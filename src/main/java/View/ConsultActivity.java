package View;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Bean.Activities;
import Controller.ActivityDAO;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class ConsultActivity extends JFrame {

    Components components = new Components();

    public ConsultActivity() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.gray);
        setLayout(null);

        // ================================================================================
        JLabel title = components.createLabel(0, 0, 500, 60, "Consult Activity");
        title.setOpaque(true);
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Roboto", Font.BOLD, 38));
        add(title);

        // ================================================================================
        JLabel idLabel = components.createLabel(0, 80, 50, 20, "ID:");
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        idLabel.setForeground(Color.black);
        idLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(idLabel);

        JTextField idField = components.createField(50, 75, 50, 32);
        idField.setFont(new Font("Roboto", Font.BOLD, 24));
        idField.setBackground(new Color(149,149,149));
        idField.setBorder(BorderFactory.createLineBorder(Color.black));
        add(idField);

        // ================================================================================
        JLabel titleLabel = components.createLabel(0, 150, 60, 20, "Title:");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(titleLabel);

        JLabel titleResultLabel = components.createLabel(60, 148, 400, 25, "");
        titleResultLabel.setHorizontalAlignment(JLabel.CENTER);
        titleResultLabel.setForeground(Color.black);
        titleResultLabel.setOpaque(true);
        titleResultLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleResultLabel.setBackground(new Color(149,149,149));
        titleResultLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(titleResultLabel);

        // ================================================================================
        JLabel descriptionLabel = components.createLabel(0, 200, 150, 27, "Description:");
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel.setForeground(Color.black);
        descriptionLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(descriptionLabel);

        JLabel descriptionResultLabel = components.createLabel(150, 200, 300, 27, "");
        descriptionResultLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionResultLabel.setForeground(Color.black);
        descriptionResultLabel.setOpaque(true);
        descriptionResultLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        descriptionResultLabel.setBackground(new Color(149,149,149));
        descriptionResultLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(descriptionResultLabel);

        // ================================================================================
        JLabel dateCreateLabel = components.createLabel(0, 250, 150, 27, "Date Create:");
        dateCreateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateCreateLabel.setForeground(Color.black);
        dateCreateLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(dateCreateLabel);

        JLabel dateCreateResultLabel = components.createLabel(150, 250, 150, 27, "");
        dateCreateResultLabel.setHorizontalAlignment(JLabel.CENTER);
        dateCreateResultLabel.setForeground(Color.black);
        dateCreateResultLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        dateCreateResultLabel.setBackground(new Color(149,149,149));
        dateCreateResultLabel.setOpaque(true);
        dateCreateResultLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(dateCreateResultLabel);

        // ================================================================================
        JLabel finalDateLabel = components.createLabel(0, 300, 150, 27, "Final Date:");
        finalDateLabel.setHorizontalAlignment(JLabel.CENTER);
        finalDateLabel.setForeground(Color.black);
        finalDateLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(finalDateLabel);

        JLabel finalDateResultLabel = components.createLabel(150, 300, 150, 27, "");
        finalDateResultLabel.setHorizontalAlignment(JLabel.CENTER);
        finalDateResultLabel.setForeground(Color.black);
        finalDateResultLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        finalDateResultLabel.setBackground(new Color(149,149,149));
        finalDateResultLabel.setOpaque(true);
        finalDateResultLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(finalDateResultLabel);

        // ================================================================================
        JLabel statusLabel = components.createLabel(0, 350, 150, 27, "Status:");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(Color.black);
        statusLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        add(statusLabel);

        JLabel statusResultLabel = components.createLabel(150, 350, 150, 27, "");
        statusResultLabel.setHorizontalAlignment(JLabel.CENTER);
        statusResultLabel.setOpaque(true);
        statusResultLabel.setForeground(Color.black);
        statusResultLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        statusResultLabel.setBackground(new Color(149,149,149));
        statusResultLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(statusResultLabel);

        // ================================================================================
        JButton consultButton = components.createButton(150, 400, 170, 50, "Consult");
        consultButton.setFont(new Font("Roboto", Font.BOLD, 35));
        consultButton.setForeground(Color.BLACK);
        consultButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ActivityDAO activityDAO = new ActivityDAO();
                if(idField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please, fill in the ID field.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    Activities act = activityDAO.consultActivity(Integer.parseInt(idField.getText()));
                    
                    if(act == null){
                        JOptionPane.showMessageDialog(null, "Activity not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }else{
                        titleResultLabel.setText(act.getTitle());
                        descriptionResultLabel.setText(act.getDescription());
                        dateCreateResultLabel.setText(String.valueOf(act.getData_create()));
                        finalDateResultLabel.setText(String.valueOf(act.getFinal_date()));
                        statusResultLabel.setText(act.getStatus());
                    }
                }
                            
            }
        
        });
        add(consultButton);



    }
    
}
