package test;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;

public class CardLayoutTest extends JFrame {


    static JFrame frame = new JFrame("Card Layout Test");

    static JPanel originalPanel = new JPanel();
    static JPanel panel1 = new JPanel();
    static JPanel panel2 = new JPanel();

    static JButton b1 = (new JButton("b1"));;
    static JButton b2 = new JButton("b2");

    static CardLayout cardLayout = new CardLayout();


    static void testCL() {
        // Set the original Layout as the owner of the cardLayout
        originalPanel.setLayout(cardLayout);

        // Set Panel & Panel Change Button
        panel1.setBackground(new ColorUIResource(ColorUIResource.BLACK));
        panel1.add(b1);
        b1.addActionListener(e -> {
            cardLayout.show(originalPanel, "2");
            frame.setTitle("Panel2");
        });
        // Set Panel & Panel Change Button
        panel2.setBackground(new ColorUIResource(ColorUIResource.GRAY));
        panel2.add(b2);
        b2.addActionListener(e -> {
            cardLayout.show(originalPanel, "1");
            frame.setTitle("Panel1");
        });

        originalPanel.add(panel1, "1");
        originalPanel.add(panel2, "2");
        // Show the initial panel of the application
        cardLayout.show(originalPanel, "1");

        // Show the card Layout in the frame
        frame.add(originalPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new DimensionUIResource(720, 480));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        testCL();
    }
}