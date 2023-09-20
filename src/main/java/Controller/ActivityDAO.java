package Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Bean.Users;
import Bean.Activities;
import Connection.ConnectionFactory;
import View.ActivitiesFrame;
import View.Login;

public class ActivityDAO {

    // Variable to store the id of the logged-in user
    private static int user_id = 0;

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int id) {
        user_id = id;
    }

    public ActivityDAO() {
        super();
    }

    // method for inserting activities
    public void insertActivity(Activities activ, JFrame addActivities) {
        // instantiating the connection with the database and the statement to execute
        // the query
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            // query to insert the activity in the database
            stmt = con.prepareStatement(
                    "INSERT INTO activity (title, descript, date_create, stats, user_id, final_data) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, activ.getTitle());
            stmt.setString(2, activ.getDescription());
            stmt.setDate(3, activ.getData_create());
            stmt.setString(4, activ.getStatus());
            stmt.setInt(5, getUser_id());
            stmt.setDate(6, activ.getFinal_date());

            // executing the query
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Activity saved with success!");
            addActivities.dispose();
            new ActivitiesFrame().setVisible(true);

        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    // method to register user in database during registration
    public void registerUser(Users user, JFrame registrationFrame) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        // creating the hash and salt of the given password to store in the database
        String hashSaltPass = hashSalt(user.getPass());

        try {
            // query to insert the user in the database
            stmt = con.prepareStatement("INSERT INTO users (name_user, password_user, email) VALUES (?,?,?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, hashSaltPass);
            stmt.setString(3, user.getEmail());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "User registered with success!");
            registrationFrame.dispose();
            new Login().setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    // method to check if the user exists in the database
    public void checkLogin(Users user, JFrame loginFrame) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // query to check if the user exists in the database
            stmt = con.prepareStatement("SELECT * FROM users WHERE name_user = ?");
            stmt.setString(1, user.getName());

            rs = stmt.executeQuery();

            // if the user exists, the password is checked
            if (rs.next()) {
                // getting the hash and salt of the password stored in the database
                String hash = rs.getString("password_user");

                // if the password is correct, the user is logged in
                if (checkHash(user.getPass(), hash)) {
                    setUser_id(rs.getInt("id"));

                    JOptionPane.showMessageDialog(null, "Welcome!");
                    loginFrame.dispose();
                    new ActivitiesFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password!", "Error",
                            JOptionPane.ERROR_MESSAGE, null);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect username or password!", "Error",
                        JOptionPane.ERROR_MESSAGE, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    // method to check if the email exists in the database and send the password
    protected boolean checkEmail(Users user) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM users WHERE email = ? AND name_user = ?");
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());

            rs = stmt.executeQuery();

            if (rs.next()) {
                return check = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    // method to change the password
    public void changePass(Users user, JFrame changePassFrame) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        String hashSaltSenha = hashSalt(user.getPass());

        // checking if the email exists in the database to change the password
        if (checkEmail(user)) {
            try {
                // query to update the password in the database
                stmt = con.prepareStatement("UPDATE users SET password_user = ? WHERE email = ?");
                stmt.setString(1, hashSaltSenha);
                stmt.setString(2, user.getEmail());

                // executing the query
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password changed with success!");

                new Login().setVisible(true);
                changePassFrame.dispose();

            } catch (SQLException e) {
                throw new RuntimeException("Error: " + e);
            } finally {
                ConnectionFactory.closeConnection(con, stmt);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect username or email!", "Error", JOptionPane.ERROR_MESSAGE,
                    null);
        }
    }

    // method to read the activities of the logged-in user and return a list of
    // activities
    public List<Activities> readActivities() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Activities> activities = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM activity WHERE user_id = ?");
            stmt.setInt(1, getUser_id());

            rs = stmt.executeQuery();

            // reading the activities of the logged-in user
            while (rs.next()) {
                Activities act = new Activities();
                act.setId(rs.getInt("id"));
                act.setTitle(rs.getString("title"));
                act.setDescription(rs.getString("descript"));
                act.setData_create(rs.getDate("date_create"));
                act.setFinal_date(rs.getDate("final_data"));
                act.setStatus(rs.getString("stats"));

                activities.add(act);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return activities;
    }

    // method to update the activities
    public void deleteActivity(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM activity WHERE id = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Activity deleted with success!");

        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void completedActivity(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE activity SET stats = ? WHERE id = ?");
            stmt.setString(1, "completed");
            stmt.setInt(2, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Activity completed with success!");
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    // method to query a user activity by id
    public Activities consultActivity(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Activities act = new Activities();

        try {
            stmt = con.prepareStatement("SELECT * FROM activity WHERE id = ? AND user_id = ?");
            stmt.setInt(1, id);
            stmt.setInt(2, getUser_id());

            rs = stmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Activity not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return act;
            }

            act.setId(rs.getInt("id"));
            act.setTitle(rs.getString("title"));
            act.setDescription(rs.getString("descript"));
            act.setData_create(rs.getDate("date_create"));
            act.setFinal_date(rs.getDate("final_data"));
            act.setStatus(rs.getString("stats"));

            return act;
        } catch (SQLException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Activity not found.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    // method to update the activities
    public void updateActivityStatus() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM activity WHERE user_id = ?");
            stmt.setInt(1, getUser_id());

            rs = stmt.executeQuery();

            // instantiation of the Date class to get the current date
            Date date = new Date(System.currentTimeMillis());

            // checking if the final date of the activity is before the current date
            while (rs.next()) {
                int id = rs.getInt("id");
                Date finalDate = rs.getDate("final_data");
                String status = rs.getString("stats");

                // if the final date is before the current date and the activity is not
                // completed, the status is changed to pending
                if (finalDate.before(date) && !"completed".equals(status)) {
                    PreparedStatement updateStmt = con.prepareStatement("UPDATE activity SET stats = ? WHERE id = ?");
                    updateStmt.setString(1, "pending");
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    // method for creating the hash and salt of the user's password to store in the
    // database
    public String hashSalt(String senha) {
        String hashSaltSenha = "";

        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        MessageDigest md;
        try {
            // Hashing algorithm
            md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hash = md.digest(senha.getBytes());

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            // Concatenação do salt e hash da senha para serem salvos no banco de dados
            hashSaltSenha = saltBase64 + ":" + hashBase64;
            return hashSaltSenha;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot find SHA-256 algorithm." + e);
        }
    }

    // method to check if the password is correct by comparing the hash and salt of
    // the password stored in the database
    public boolean checkHash(String pass, String hash) {

        String[] pieces = hash.split(":");
        String saltBase64 = pieces[0];
        String hashBase64 = pieces[1];

        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] storedHash = Base64.getDecoder().decode(hashBase64);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);

            byte[] hashNew = md.digest(pass.getBytes());

            boolean equal = MessageDigest.isEqual(storedHash, hashNew);

            return equal;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot find SHA-256 algorithm." + e);
        }
    }
}
