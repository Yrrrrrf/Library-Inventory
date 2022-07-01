package ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;

import model.db.DatabaseConnection;

import ui.util.AuxiliarMethods;

public class LoginMenu {


    String loginAs = "";


    public LoginMenu(JPanel frame) {
        // MainImage
        JLabel imageLabel = AuxiliarMethods.setMainImage(frame, 200, -8);
        // Customer
        JButton customerButton = AuxiliarMethods.createImageButton("As Customer", "src/img/customer.png", 120, 120);
        customerButton.setBounds(40, 200, 180, 180);
        frame.add(customerButton);
        // Epmloyee
        JButton employeeButton = AuxiliarMethods.createImageButton("As Employee", "src/img/employee.png", 120, 120);
        employeeButton.setBounds(500, 200, 180, 180);
        frame.add(employeeButton);
        // Buttons Switch Behavrior
        employeeButton.addActionListener(e -> {
            employeeButton.setEnabled(false);
            customerButton.setEnabled(true);
            imageLabel.setText("Login As Employee");
            this.setLoginAs("employee");
        });
        customerButton.addActionListener(e -> {
            customerButton.setEnabled(false);
            employeeButton.setEnabled(true);
            imageLabel.setText("Login As Customer");
            this.setLoginAs("customer");
        });
        // Initial Login Settings (Customer as default)
        customerButton.setEnabled(false);
        this.setLoginAs("customer");
        imageLabel.setText("Login As Customer");
        // SignIn
        JButton signInButton = AuxiliarMethods.createImageButton("Sign In", "src/img/signIn.png", 40, 40);
        signInButton.setBounds(8, 8, 60, 60);
        signInButton.setBorder(null);
        frame.add(signInButton);
        signInButton.addActionListener(e -> {
            UserInterface.cardLayout.show(UserInterface.framePanel, "SignIn");
            UserInterface.frame.setTitle("Sign In Menu");            
            }
        );
        // Asking data fields
        JTextField mailTF = AuxiliarMethods.createAskField(frame, "Mail", 310, 240, true);
        JTextField passwordTF = AuxiliarMethods.createAskField(frame, "Password", 310, 300, true);
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Consolas", Font.BOLD, 16));
        loginButton.setBounds(320, 380, 80, 32);
        loginButton.addActionListener(e -> {
            if (DatabaseConnection.Login(this.getLoginAs(), mailTF.getText(), passwordTF.getText())) {
                UserInterface.cardLayout.show(UserInterface.framePanel, this.getLoginAs()); // Change to the selected Window
                UserInterface.frame.setTitle(this.getLoginAs() + " Menu");
                // UserInterface.frame.setJMenuBar(null); // Set the selected window MenuBar
                AuxiliarMethods.createUserMenu();    
            }
            else AuxiliarMethods.createAlertLabel("Incorrect Mail Or Password", frame, 240, 356);
        });
        frame.add(loginButton);

        UserInterface.addVersion(frame);
    }

    // Get Query String
    public String getLoginAs() {return this.loginAs;}
    // Set Query String
    public void setLoginAs(String string) {this.loginAs = string;}
}