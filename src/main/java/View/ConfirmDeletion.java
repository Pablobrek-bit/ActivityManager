package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.ActivityDAO;

public class ConfirmDeletion extends JFrame {

    Components components = new Components();

    public ConfirmDeletion() {
        setSize(500, 160);
        setTitle("Confirm Deletion");
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ================================================================================
        JLabel label = components.createLabel(10, 10, 460, 25, "Are you sure you want to delete this activity?");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.black);
        label.setFont(new Font("Roboto", Font.BOLD, 20));
        add(label);

        // ================================================================================
        JButton yesButton = components.createButton(10, 55, 70,30, "Yes");
        yesButton.setFont(new Font("Roboto", Font.BOLD, 20));
        yesButton.setForeground(Color.green);
        yesButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ActivityDAO actDAO = new ActivityDAO();
                yesButton.setForeground(Color.black);
                int row = ActivitiesFrame.table.getSelectedRow();
                int id = (int) ActivitiesFrame.table.getValueAt(row, 0);
                actDAO.deleteActivity(id);
                ActivitiesFrame.model.removeRow(row);
                dispose();
            }
        });
        add(yesButton);

        JButton noButton = components.createButton(130, 55, 70,30, "No");
        noButton.setFont(new Font("Roboto", Font.BOLD, 20));
        noButton.setForeground(Color.red);

        noButton.addActionListener(event -> {
            dispose();
        });
        add(noButton);
    }
}
