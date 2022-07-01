package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.db.DatabaseConnection;
import model.user.Customer;

import java.awt.Font;
import java.util.ArrayList;
import java.awt.Dimension;

// import model.db.UserLogin;
import ui.util.AuxiliarMethods;

public class SignInMenu {


    public SignInMenu(JPanel frame) {
        // Main Image
        AuxiliarMethods.setMainImage(frame, 380, 40);
        // Go Back To Login Menu
        JButton loginButton = AuxiliarMethods.createImageButton("Log In", "src/img/signIn.png", 40, 40);
        loginButton.setBounds(8, 8, 60, 60);
        loginButton.setBorder(null);
        frame.add(loginButton);
        loginButton.addActionListener(e -> {
            UserInterface.cardLayout.show(UserInterface.framePanel, "Login");
            UserInterface.frame.setTitle("Login Menu");
        });
        // Ask Data Fields
        JTextField[] customerData = newCustomer(frame);
        ArrayList<JComboBox<Integer>> date = birthdayField(frame);
        //* To know if the number have 10 digits length

        // REGISTER BUTTON
        JButton signInButton = new JButton("Sign In");
        signInButton.setFont(new Font("Consolas", Font.BOLD, 16));
        signInButton.setBounds(200, 396, 100, 32);
        signInButton.addActionListener(e -> {
            Customer user = new Customer(customerData, getDateString(date));
            // if (user.aFieldIsEmpty())  AuxiliarMethods.createAlertLabel("Fields can't be empty", frame, 132, 372);
            // else AuxiliarMethods.createAlertLabel("All fields filled", frame, 400, 300);
            //? Proved method
            DatabaseConnection.SignInUser(
                user.getMail(), 
                user.getPassword(), 
                user.getName(), 
                user.getSurname(), 
                user.getAddress(), 
                user.getPhoneNumber(), 
                user.getBirthday()
            );
            frame.repaint();
        });
        frame.add(signInButton);

        UserInterface.addVersion(frame);
    }

    // Set the Ask Field for all the Customer Data
    public static JTextField[] newCustomer(JPanel frame) {
        JTextField[] customerData = new JTextField[7];
        customerData[0] = AuxiliarMethods.createAskField(frame, "Name", 100, 100, false);
        customerData[1] = AuxiliarMethods.createAskField(frame, "Surname", 100, 140, false);
        customerData[2] = AuxiliarMethods.createAskField(frame, "Address", 100, 180, false);
        // birthdayField(frame);
        //TODO Add the condition & Label (can be only 10 digits lenght) :(
        customerData[3] = AuxiliarMethods.createAskField(frame, "Phone Number", 100, 260, false);
        customerData[4] = AuxiliarMethods.createAskField(frame, "Mail", 100, 300, false);
        customerData[5] = AuxiliarMethods.createAskField(frame, "Password", 100, 340, false);
        return customerData;
    }

    // Set the AskBirthday Field
    public static ArrayList<JComboBox<Integer>> birthdayField(JPanel frame) {
        birthdayLabel(frame, "Birthday", 0, 224);
        birthdayLabel(frame, "D", 32, 224);
        birthdayLabel(frame, "M", 114, 224);
        birthdayLabel(frame, "Y", 196, 224);
        ArrayList<JComboBox<Integer>> date = new ArrayList<JComboBox<Integer>>();
        date.add(AuxiliarMethods.createComboBox(frame, 320, 220, 1940,2022));   // YEAR
        date.add(AuxiliarMethods.createComboBox(frame, 240, 220, 1, 12));       // MONTH
        date.add(AuxiliarMethods.createComboBox(frame, 160, 220, 1, 31));       // DAY
        return date; // return the date as a JComboBox Array
    }

    // Set birthday label, only used on birthdayField() method
    private static JLabel birthdayLabel(JPanel frame, String text, int posX, int posY) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(120, 40));
        label.setBounds(posX, posY, 120, 20);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setVerticalAlignment(JLabel.CENTER);
        frame.add(label);
        return label;
    }

    // Get a Date String, using the date JComboBox ArrayList 
    private static String getDateString(ArrayList<JComboBox<Integer>> date) {
        return date.get(0).getSelectedItem() + "-"
             + ((int)date.get(1).getSelectedItem() < 10 ? ("0" + date.get(1).getSelectedItem()) : date.get(1).getSelectedItem()) + "-"
             + ((int)date.get(2).getSelectedItem() < 10 ? ("0" + date.get(2).getSelectedItem()) : date.get(2).getSelectedItem());
    }

}