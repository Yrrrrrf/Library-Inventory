package ui.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.UserInterface;

public class AuxiliarMethods {

    // Create 
    public static JLabel setMainImage(JPanel frame, int posX, int posY) {
        JLabel imageLabel = AuxiliarMethods.createImageLabel("", "src/img/books.png", 200, 200);
        imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        imageLabel.setIconTextGap(-16);
        imageLabel.setBounds(posX, posY, 320, 240);
        frame.add(imageLabel);
        return imageLabel;
    }


    // Create a JTextField with the given text
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(120, 40));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        return label;
    }

    // Create a JTextField with text parameter as the default text inside
    public static JTextField createTextField(String text) {
        JTextField textField = new JTextField();
        textField.setText(text);
        textField.setPreferredSize(new Dimension(240, 32));
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setFont(new Font("Consolas", Font.ITALIC, 16));
        textField.setHorizontalAlignment(0);
        textField.setAlignmentY(100);
        return textField;
    }

    // rezise an Image to the given size
    public static ImageIcon resizedImage(String path, int width, int height) {
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    // Create a JLabel with an image(with given width & height)
    public static JLabel createImageLabel(String text, String path, int imageWidth, int imageHeight) {
        JLabel imageLabel = createLabel(text);
        ImageIcon image = resizedImage(path, imageWidth, imageHeight);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
        imageLabel.setIcon(image);
        return imageLabel;
    }

    // Creates a button with image 
    public static JButton createImageButton(String text, String path, int imageWidth, int imageHeight) {
        JButton button = new JButton();
        button.add(createImageLabel(text,path, imageWidth, imageHeight));
        button.setLayout(new GridLayout(1,1));
        button.setBorder(null);
        return button;
    }

    // Creates a complete Ask Field. Can be Horizontal or Vertical
    public static JTextField createAskField(JPanel panel, String askedValue, int posX, int posY, boolean isVertical) {
        JLabel label = createLabel(askedValue);
        JTextField textField = createTextField("");
        if (isVertical) {
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(posX, posY, 100, 20);
            textField.setBounds(posX - 70, posY + 20, 240, 32);
        } else {
            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setBounds(posX - 80, posY + 4, 100, 20);
            textField.setBounds(posX + 40, posY, 240, 32);
        }
        panel.add(label);
        panel.add(textField);
        return textField; // just if is necessary to get the written data
    }


    public static JComboBox<Integer> createComboBox(JPanel frame, int posX, int posY, int minValue, int maxValue) {
        JComboBox<Integer> comboBox = new JComboBox<Integer>();
        for (int i = minValue; i <= maxValue; i++) comboBox.addItem(i);
        comboBox.setBounds(posX, posY, 60, 32);
        comboBox.setBackground(Color.DARK_GRAY);
        comboBox.setForeground(Color.WHITE);
        comboBox.addActionListener(e -> comboBox.getSelectedItem());
        frame.add(comboBox);
        return comboBox;
    }
    
    public static JComboBox<String> createComboBox(JPanel frame, int posX, int posY, String[] values) {
        JComboBox<String> comboBox = new JComboBox<String>();
        for (int i = 0; i < values.length; i++) comboBox.addItem(values[i]);
        comboBox.setBounds(posX, posY, 128, 32);
        comboBox.setBackground(Color.DARK_GRAY);
        comboBox.setForeground(Color.WHITE);
        comboBox.addActionListener(e -> comboBox.getSelectedItem());
        frame.add(comboBox);
        return comboBox;
    }


    public static JLabel createAlertLabel(String alertMessage, JPanel frame, int posX, int posY) {
        JLabel alertLabel = AuxiliarMethods.createLabel(alertMessage);
        alertLabel.setForeground(Color.RED);
        alertLabel.setFont(new Font("Cambria", Font.BOLD, 16));
        alertLabel.setBounds(posX, posY, 240, 20);
        frame.add(alertLabel);
        frame.repaint();
        return alertLabel;
    }

    // User's menu
    public static void createUserMenu() {
        // Set this labels menu bar
        JMenuBar menuBar = new JMenuBar();
        UserInterface.frame.setJMenuBar(menuBar);
        // Menu Bar
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        // JMenu Bar Item
        JMenuItem signOut  = new JMenuItem("Sign Out");
        signOut.addActionListener(e -> {
            UserInterface.cardLayout.show(UserInterface.framePanel, "Login");
            UserInterface.frame.setTitle("LoginMenu");
            UserInterface.frame.setJMenuBar(null);
        });
        signOut.setIcon(AuxiliarMethods.resizedImage("src/img/exit.png",24, 24));
        optionsMenu.add(signOut);

    }

    // Create a JComboBox to add some selectable options inside
    public static JLabel comboBoxLabel(JPanel frame, String text, int posX, int posY) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(120, 80));
        label.setBounds(posX, posY, 120, 80);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        frame.add(label);
        return label;
    }
}