package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Bean.Users;
import Controller.ActivityDAO;

public class Registration extends JFrame{
    
    Components components = new Components();

    public Registration() {
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
        JLabel nameLabel = components.createLabel(25, 230, 100, 30, "Name");
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        nameLabel.setForeground(Color.BLACK);
        add(nameLabel);

        JTextField nameField = components.createField(25, 270, 300, 30);
        nameField.setFont(new Font("Roboto", Font.BOLD, 20));
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nameField.setBackground(new Color(149,149,149));
        add(nameField);

        // ================================================================================
        JLabel emailLabel = components.createLabel(25, 310, 100, 30, "Email");
        emailLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        emailLabel.setForeground(Color.BLACK);
        add(emailLabel);

        JTextField emailField = components.createField(25, 350, 300, 30);
        emailField.setFont(new Font("Roboto", Font.BOLD, 20));
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        emailField.setBackground(new Color(149,149,149));
        add(emailField);

        // ================================================================================
        JLabel passLabel = components.createLabel(25, 390, 100, 30, "Pass");
        passLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        passLabel.setForeground(Color.BLACK);
        add(passLabel);

        JTextField passField = components.createField(25, 430, 300, 30);
        passField.setFont(new Font("Roboto", Font.BOLD, 20));
        passField.setBorder(BorderFactory.createLineBorder(Color.black));
        passField.setBackground(new Color(149,149,149));
        add(passField);

        // ================================================================================
        JButton registerButton = components.createButton(125, 480, 200, 50, "Register");
        registerButton.setFont(new Font("Roboto", Font.BOLD, 30));
        registerButton.setForeground(Color.BLACK);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Users user = new Users();
                ActivityDAO actDAO = new ActivityDAO();
                
                user.setName(nameField.getText());
                user.setEmail(emailField.getText());
                user.setPass(passField.getText());

                if(isValidEmail(user.getEmail())){
                    actDAO.registerUser(user, Registration.this);

                } else {
                    emailField.setText("");
                    emailField.requestFocus();
                    emailField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
                    emailField.setToolTipText("Invalid email");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setForeground(Color.black);
            }

            private boolean isValidEmail(String email){
                if(email.contains("@") && email.contains(".")){
                    return true;
                }
                return false;
            }
            
        });
        add(registerButton);   
    }

}
