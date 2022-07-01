package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.util.AuxiliarMethods;

public class UserInterface extends JFrame {
    final static String ACTUAL_VERSION = "0.00";
    // Init JFrame
    public static JFrame frame = new JFrame();
    // Login Panel & declaration
    static JPanel loginPanel = new JPanel();
    static LoginMenu loginMenu = new LoginMenu(loginPanel);
    // USERS LOGIN MENU & declaration
    static JPanel userPanel = new JPanel();
    static CustomerMenu userMenu = new CustomerMenu(userPanel);
    // EMPLOYEE LOGIN MENU & declaration
    static JPanel employeePanel = new JPanel();
    static EmployeeMenu employeeMenu = new EmployeeMenu(employeePanel);

    // Sign In Panel & declaration
    static JPanel signInPanel = new JPanel();
    static SignInMenu signInMenu = new SignInMenu(signInPanel);

    // The principal panel that will altern between the others
    public static JPanel framePanel = new JPanel();
    public static CardLayout cardLayout = new CardLayout();


    private static void SetUserInterface(String windowName, int sizeX, int sizeY) {
        frame.setTitle(windowName);
        frame.setSize(new Dimension(sizeX, sizeY));
        // frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set Icon
        ImageIcon icon = new ImageIcon("src/img/books.png");
        frame.setIconImage(icon.getImage());
        // This is necessary to make the Menu's funciton in a correct way
        signInPanel.setLayout(null);
        loginPanel.setLayout(null);
        userPanel.setLayout(null);
        employeePanel.setLayout(null);

        // Set framePanel as the Original Card        
        framePanel.setLayout(cardLayout);
        framePanel.setPreferredSize(new Dimension(sizeX, sizeY));
        // Add new Panels to the Original Card
        framePanel.add(signInPanel, "SignIn");
        framePanel.add(loginPanel, "Login");
        framePanel.add(userPanel, "customer");
        framePanel.add(employeePanel, "employee");
        
        // Set the initial panel that appears
        cardLayout.show(framePanel, "customer");
        // cardLayout.show(framePanel, "employee");
        // TODO Uncomment next line
        // cardLayout.show(framePanel, "Login");
        frame.setJMenuBar(null);
        // Add the Original Panel (that will altern) into the frame
        frame.add(framePanel);
    }

    // To altern between the UserInterface different Cards form the other Scripts
    public static void alternCard(String cardName) {
        cardLayout.show(framePanel, "cardName");
    }

    public static void addVersion(JPanel frame) {
        JLabel info = AuxiliarMethods.createLabel(ACTUAL_VERSION + " (testing)");
        info.setFont(new Font("Consolas", Font.ITALIC, 12));
        info.setHorizontalAlignment(JLabel.LEFT);
        info.setBounds(8, 420, 180, 20);
        frame.add(info);
    }


    public static void main(String[] args) {
        SetUserInterface("Login Menu", 720, 480);

        frame.setVisible(true);
    }
}