package View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Components extends JFrame {

    public JLabel createLabel(int x, int y, int width, int height, String text) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        return label;
    }

    public JTextField createField(int x, int y, int width, int height) {
        JTextField field = new JTextField();
        field.setBounds(x, y, width, height);
        return field;
    }

    public JButton createButton(int x, int y, int width, int height, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 30));
        button.setBounds(x, y, width, height);
        button.setHorizontalAlignment(JLabel.CENTER);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    public ImageIcon createIcon(String path, int width, int height){
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        return scaledIcon;
    } 

}
    


