package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Bean.Users;
import Controller.ActivityDAO;

public class Login extends JFrame {

    private ImageIcon scaledShowPassIcon;
    private ImageIcon scaledHidePassIcon;

    Components components = new Components();

    public Login() {
        setTitle("Login");
        setSize(500, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.gray);

        // ================================================================================
        ImageIcon userIcon = components.createIcon("/View/Images/user-icon.png", 180, 180);

        JLabel userTitle = components.createLabel(150, 5, 200, 200, "");
        userTitle.setIcon(userIcon);
        add(userTitle);

        // ================================================================================
        JLabel userLabel = components.createLabel(50, 230, 100, 30, "User:");
        userLabel.setForeground(Color.BLACK);
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        userLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        add(userLabel);

        JTextField userField = components.createField(55, 270, 300, 35);
        userField.setFont(new Font("Roboto", Font.BOLD, 25));
        userField.setBackground(new Color(149, 149, 149));
        userField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(userField);

        // ================================================================================
        JLabel passLabel = components.createLabel(50, 330, 170, 30, "Password:");
        passLabel.setForeground(Color.BLACK);
        passLabel.setHorizontalAlignment(JLabel.CENTER);
        passLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        add(passLabel);

        ImageIcon showPassIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/View/Images/amostrar-senha.png")));
        Image showPassImage = showPassIcon.getImage();
        Image scaledShowPassImage = showPassImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        scaledShowPassIcon = new ImageIcon(scaledShowPassImage);

        ImageIcon hidePassIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/View/Images/esconder-senha.png")));
        Image hidePassImage = hidePassIcon.getImage();
        Image scaledHidePassImage = hidePassImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        scaledHidePassIcon = new ImageIcon(scaledHidePassImage);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(55, 370, 300, 35);
        passField.setFont(new Font("Roboto", Font.BOLD, 25));
        passField.setBackground(new Color(149, 149, 149));
        passField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(passField);

        JCheckBox showPass = new JCheckBox();
        showPass.setOpaque(false);
        showPass.setBounds(360, 365, 45, 45);
        showPass.addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            passField.setEchoChar(checkBox.isSelected() ? (char) 0 : '*');
            if (checkBox.isSelected()) {
                showPass.setIcon(scaledHidePassIcon);
            } else {
                showPass.setIcon(scaledShowPassIcon);
            }
        });
        add(showPass);

        showPass.setIcon(scaledShowPassIcon);

        // ================================================================================
        JButton loginButton = components.createButton(80, 440, 300, 35, "Login");
        loginButton.setForeground(Color.BLACK);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {

                Users user = new Users();
                ActivityDAO act = new ActivityDAO();

                user.setName(userField.getText());
                user.setPass(new String(passField.getPassword()));

                act.checkLogin(user, Login.this);
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                loginButton.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                loginButton.setForeground(Color.black);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {

            }

        });
        add(loginButton);

        // ================================================================================
        JButton cadastreButton = components.createButton(80, 480, 300, 35, "Cadastrar");
        cadastreButton.setForeground(Color.BLACK);
        cadastreButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cadastreButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                new Registration().setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                cadastreButton.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                cadastreButton.setForeground(Color.black);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {

            }

        });
        add(cadastreButton);

        // ================================================================================
        JButton changePassButton = components.createButton(80, 520, 300, 35, "Change Pass");
        changePassButton.setForeground(Color.black);
        changePassButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        changePassButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                new ChangePass().setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                changePassButton.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                changePassButton.setForeground(Color.black);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {

            }

        });
        add(changePassButton);
    }

}
