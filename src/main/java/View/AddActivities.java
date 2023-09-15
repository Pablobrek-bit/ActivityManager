package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import Bean.Activities;
import Controller.ActivityDAO;

public class AddActivities extends JFrame {

    Components components = new Components();

    public AddActivities(JFrame ActivitiesFrame) {
        setSize(400, 400);
        setTitle("add new activities");
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ================================================================================
        JLabel titleLabel = components.createLabel(10, 50, 70, 25, "Title");
        // titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        add(titleLabel);

        JTextField titleTextField = components.createField(13, 90, 250, 30);
        titleTextField.setFont(new Font("Roboto", Font.BOLD, 20));
        add(titleTextField);

        // ================================================================================
        JLabel descriptionLabel = components.createLabel(10, 130, 200, 33, "Description");
        // descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel.setForeground(Color.black);
        descriptionLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        add(descriptionLabel);

        JTextField descriptionTextField = components.createField(13, 170, 250, 30);
        descriptionTextField.setFont(new Font("Roboto", Font.BOLD, 20));
        add(descriptionTextField);

        // ================================================================================
        JLabel finalDateLabel = components.createLabel(10, 210, 200, 33, "Final Date");
        // finalDateLabel.setHorizontalAlignment(JLabel.CENTER);
        finalDateLabel.setForeground(Color.black);
        finalDateLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        add(finalDateLabel);

        JTextField finalDateTextField = components.createField(13, 250, 250, 30);
        finalDateTextField.setFont(new Font("Roboto", Font.BOLD, 20));
        add(finalDateTextField);

        // ================================================================================
        ImageIcon confirmIcon = components.createIcon("/View/Images/confirm-icon.png", 48, 48);

        JButton confirmButton = components.createButton(0, 310, 200, 50, "Confirm");
        confirmButton.setFont(new Font("Roboto", Font.BOLD, 20));
        confirmButton.setIcon(confirmIcon);
        confirmButton.setForeground(Color.BLACK);

        confirmButton.addActionListener(event -> {
            confirmButton.setForeground(Color.LIGHT_GRAY);

            Timer timer = new Timer(140, event2 -> {
                confirmButton.setForeground(Color.black);
                ((Timer) event2.getSource()).stop();
            });

            timer.start();

            Activities act = new Activities();
            ActivityDAO actDAO = new ActivityDAO();

            if (!titleTextField.getText().equals("") && !descriptionTextField.getText().equals("")
                    && !finalDateTextField.getText().equals("")) {
                act.setTitle(titleTextField.getText());
                act.setDescription(descriptionTextField.getText());
                act.setFinal_date(java.sql.Date.valueOf(finalDateTextField.getText()));

                actDAO.insertActivity(act, AddActivities.this);

                ActivitiesFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Fill in all fields");
            } 

        });
        add(confirmButton);

        // ================================================================================

    }
}
