package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Bean.Activities;
import Controller.ActivityDAO;

public class ActivitiesFrame extends JFrame {

    Components components = new Components();
    static DefaultTableModel model = new DefaultTableModel();
    static JTable table = new JTable(model);

    public ActivitiesFrame() {
        setSize(1100, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.gray);

        // ================================================================================

        JLabel title = components.createLabel(0, 0, 1100, 64, "Activities");
        title.setFont(new Font("Roboto", Font.BOLD, 44));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(Color.BLACK);
        title.setForeground(Color.white);
        add(title);

        // ================================================================================
        ImageIcon plusIcon = components.createIcon("/View/Images/plus-icon.png", 53, 53);

        JButton createButton = components.createButton(650, 62, 165, 50, "Create");
        createButton.setFont(new Font("Roboto", Font.BOLD, 20));
        createButton.setIcon(plusIcon);
        createButton.setForeground(Color.BLACK);
        createButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                createButton.setForeground(Color.LIGHT_GRAY);

                Timer timer = new Timer(140, event -> {
                    createButton.setForeground(Color.black);
                    ((Timer) event.getSource()).stop();
                });

                timer.start();

                new AddActivities(ActivitiesFrame.this).setVisible(true);

            }

        });
        add(createButton);

        // ================================================================================
        ImageIcon deleteIcon = components.createIcon("/View/Images/delete-icon.png", 42, 42);

        JButton deleteButton = components.createButton(770, 62, 165, 50, " Delete");
        deleteButton.setFont(new Font("Roboto", Font.BOLD, 20));
        deleteButton.setIcon(deleteIcon);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                deleteButton.setForeground(Color.LIGHT_GRAY);

                Timer timer = new Timer(140, event -> {
                    deleteButton.setForeground(Color.black);
                    ((Timer) event.getSource()).stop();
                });

                timer.start();

                int row = table.getSelectedRow();
                if (row != -1) {
                    // JOptionPane.showMessageDialog(null, table.getValueAt(row, 0));
                    new ConfirmDeletion().setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Select a row!");
                }

            }

        });
        add(deleteButton);

        // =================================================================================
        ImageIcon consultIcon = components.createIcon("/View/Images/lupa-icon.png", 43, 43);

        JButton consultButton = components.createButton(888, 62, 165, 50, "Consult");
        consultButton.setFont(new Font("Roboto", Font.BOLD, 20));
        consultButton.setIcon(consultIcon);
        consultButton.setForeground(Color.BLACK);
        consultButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                consultButton.setForeground(Color.LIGHT_GRAY);

                Timer timer = new Timer(140, event -> {
                    consultButton.setForeground(Color.black);
                    ((Timer) event.getSource()).stop();
                });

                timer.start();

                new ConsultActivity().setVisible(true);
            }

        });
        add(consultButton);

        // ================================================================================
        ImageIcon completedIcon = components.createIcon("/View/Images/completed-icon.png", 43, 43);

        JButton completedButton = components.createButton(495, 62, 200, 50, "Completed");
        completedButton.setFont(new Font("Roboto", Font.BOLD, 20));
        completedButton.setIcon(completedIcon);
        completedButton.setForeground(Color.BLACK);
        completedButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                completedButton.setForeground(Color.LIGHT_GRAY);

                Timer timer = new Timer(140, event -> {
                    completedButton.setForeground(Color.black);
                    ((Timer) event.getSource()).stop();
                });

                timer.start();

                int row = table.getSelectedRow();
                if ((row != -1)) {
                    if(table.getValueAt(row, 5).equals("completed")){
                        JOptionPane.showMessageDialog(null, "Activity already completed!");
                    }else{
                        ActivityDAO activityDAO = new ActivityDAO();
                        activityDAO.completedActivity((int) table.getValueAt(row, 0));
                        table.setValueAt("completed", row, 5);

                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Select a row!");
                }
            }
        });
        add(completedButton);

        // ================================================================================
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Description");
        model.addColumn("Date Created");
        model.addColumn("Final Date");
        model.addColumn("Status");

        ActivityDAO activityDAO = new ActivityDAO();
        activityDAO.updateActivityStatus();
        List<Activities> activitiesList = activityDAO.readActivities();

        for (Activities activities : activitiesList) {
            model.addRow(new Object[] {
                    activities.getId(),
                    activities.getTitle(),
                    activities.getDescription(),
                    activities.getData_create(),
                    activities.getFinal_date(),
                    activities.getStatus()
            });
        }

        table.setFont(new Font("Roboto", Font.BOLD, 12));
        table.setRowHeight(30);
        // table.setIntercellSpacing(new Dimension(10, 10));
        table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {

            public void setValue(Object value) {

                setHorizontalAlignment(JLabel.CENTER);
                if ("pending".equals(value)) {
                    setForeground(Color.red);
                    setText((value == null) ? "" : value.toString());
                } else if ("in_progress".equals(value)) {
                    setForeground(Color.orange);
                    setText((value == null) ? "" : value.toString());
                } else if ("completed".equals(value)) {
                    setForeground(Color.green);
                    setText((value == null) ? "" : value.toString());
                } else {
                    setBackground(Color.white);
                    setForeground(Color.black);
                    setText((value == null) ? "" : value.toString());
                }
            }
        };
        table.getColumnModel().getColumn(5).setCellRenderer(statusRenderer);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Roboto", Font.BOLD, 22));
        header.setBackground(Color.black);
        header.setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 110, 880, 500);
        add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // ================================================================================
        ImageIcon logoutIcon = components.createIcon("/View/Images/logout-icon.png", 30, 30);

        JButton logoutButton = components.createButton(0, 560, 140, 50, "Logout");
        logoutButton.setFont(new Font("Roboto", Font.BOLD, 20));
        logoutButton.setIcon(logoutIcon);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                ActivityDAO.setUser_id(0);
                new Login().setVisible(true);
            }
        });
        add(logoutButton);
    }
}
