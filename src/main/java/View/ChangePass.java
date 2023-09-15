package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Bean.Users;
import Controller.ActivityDAO;

public class ChangePass extends JFrame {
    
    Components components = new Components();

    public ChangePass(){
        setTitle("Change Password");
        setSize(500, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.gray);
        
        // ================================================================================
        JLabel title = components.createLabel(100, 30, 300, 60, "Change Pass");
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto", Font.BOLD, 44));
        add(title);

        // ================================================================================
        JLabel userLabel = components.createLabel(58, 120, 100, 30, "User:");
        userLabel.setForeground(Color.BLACK);
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        userLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        add(userLabel);

        JTextField userField = components.createField(61, 160, 350, 35);
        userField.setFont(new Font("Roboto", Font.BOLD, 25));
        userField.setBackground(new Color(149,149,149));
        userField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(userField);

        // ================================================================================
        JLabel emaiLabel = components.createLabel(50, 200, 120, 30, "Email");
        emaiLabel.setForeground(Color.BLACK);
        emaiLabel.setHorizontalAlignment(JLabel.CENTER);
        emaiLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        add(emaiLabel);

        JTextField emailField = components.createField(61, 240, 350, 35);
        emailField.setFont(new Font("Roboto", Font.BOLD, 25));
        emailField.setBackground(new Color(149,149,149));
        emailField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(emailField); 

        // ================================================================================
        JLabel passLabel = components.createLabel(50, 300, 190, 30, "New Pass");
        passLabel.setForeground(Color.BLACK);
        passLabel.setHorizontalAlignment(JLabel.CENTER);
        passLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        add(passLabel);

        JTextField passField = components.createField(61, 340, 350, 35);
        passField.setFont(new Font("Roboto", Font.BOLD, 25));
        passField.setBackground(new Color(149,149,149));
        passField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(passField);

        // ================================================================================
        JLabel confirmPassLabel = components.createLabel(42, 400, 250, 30, "Confirm Pass");
        confirmPassLabel.setForeground(Color.BLACK);
        confirmPassLabel.setHorizontalAlignment(JLabel.CENTER);
        confirmPassLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        add(confirmPassLabel);

        JTextField confirmPassField = components.createField(61, 440, 350, 35);
        confirmPassField.setFont(new Font("Roboto", Font.BOLD, 25));
        confirmPassField.setBackground(new Color(149,149,149));
        confirmPassField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(confirmPassField);

        // ================================================================================
        JButton changeButton = components.createButton(80, 500, 300, 35, "Change");
        changeButton.setForeground(Color.BLACK);
        changeButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                Users user = new Users();
                ActivityDAO act = new ActivityDAO();

                if(passField.getText().equals(confirmPassField.getText())){
                    user.setName(userField.getText());
                    user.setEmail(emailField.getText());
                    user.setPass(passField.getText());

                    act.changePass(user, ChangePass.this);
                } else {
                    confirmPassField.setText("");
                    confirmPassField.requestFocus();
                    confirmPassField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
                    confirmPassField.setToolTipText("Different passwords");
                }
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                changeButton.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                changeButton.setForeground(Color.black);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                
            }
            
        });
        add(changeButton);

    }
}
