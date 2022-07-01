package ui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.db.DBAuxiliarMethods;
import model.db.DatabaseConnection;
import model.library.Book;
import model.user.Author;
import ui.util.AuxiliarMethods;

public class EmployeeMenu {

    public EmployeeMenu(JPanel frame) {
        AuxiliarMethods.createUserMenu();

        JPanel workSpace = new JPanel();
        workSpace.setBounds(16, 48, 520, 360);
        workSpace.setLayout(null);
        frame.add(workSpace);

        // Show Inventory Button
        JButton showInventory = new JButton("Show Inventory");
        showInventory.setBounds(572, 54, 120, 40);
        showInventory.addActionListener(e -> {
            workSpace.removeAll();
            workSpace.add(DBAuxiliarMethods.showInventory());
            workSpace.repaint();
        });
        frame.add(showInventory);

        // Show New Book Menu
        JButton newBookButton = new JButton("Add New Book");
        newBookButton.setBounds(572, 114, 120, 40);
        newBookButton.addActionListener(e -> {
            workSpace.removeAll();
            workSpace.add(showNewBookMenu(workSpace));
            workSpace.repaint();
        });
        frame.add(newBookButton);
        
        // Show Register New Author Menu
        JButton newAuthorButton = new JButton("Add New Author");
        newAuthorButton.setBounds(572, 174, 120, 40);
        newAuthorButton.addActionListener(e -> {
            workSpace.removeAll();
            workSpace.add(showNewAuthorMenu(workSpace));
            workSpace.repaint();
        });
        frame.add(newAuthorButton);

        // Show All Sales
        JButton salesButton = AuxiliarMethods.createImageButton("", "src/img/salesHistory.jpg", 40, 40);
        salesButton.setBounds(600, 320, 40, 40);
        salesButton.addActionListener(e -> {
            workSpace.removeAll();
            workSpace.add(showSalesTable());
            workSpace.repaint();
        });
        frame.add(salesButton);


        // Actual Version
        UserInterface.addVersion(frame);
    }



    // New Book Menu
    public static JPanel showNewBookMenu(JPanel frame) {
        JPanel newBookPanel = new JPanel();
        newBookPanel.setBounds(16, 48, 520, 360);
        JTextField isnbTF = AuxiliarMethods.createAskField(frame, "ISNB", 160, 32, true);
        JTextField nameTF = AuxiliarMethods.createAskField(frame, "Name", 160, 82, true);
        AuxiliarMethods.comboBoxLabel(frame, "Price", 78, 110);
        JComboBox<Integer> stock = AuxiliarMethods.createComboBox(frame, 240, 160, 0, 1000);
        AuxiliarMethods.comboBoxLabel(frame, "Stock", 208, 110);
        JComboBox<Integer> price = AuxiliarMethods.createComboBox(frame, 110, 160, 0, 120);
        AuxiliarMethods.comboBoxLabel(frame, "Cathegory", 148, 158);
        JComboBox<String> cathegories = AuxiliarMethods.createComboBox(frame, 140, 204, Book.CATHEGORIES);
        AuxiliarMethods.comboBoxLabel(frame, "Author", 148, 208);
        ArrayList<Author> authors = DatabaseConnection.getAuthors();
        String[] authorsList = new String[authors.size()];
        for (int i = 0; i < authors.size(); i++) authorsList[i] = authors.get(i).getName() + " " + authors.get(i).getSurname();
        JComboBox<String> author = AuxiliarMethods.createComboBox(frame, 140, 256, authorsList);

        JButton registerBookButton = new JButton("Register Book");
        registerBookButton.setFont(new Font("Consolas", Font.BOLD, 16));
        registerBookButton.setBounds(112, 308, 172, 32);
        registerBookButton.addActionListener(e -> {
            Book newBook = new Book(
                isnbTF.getText(),
                nameTF.getText(),
                cathegories.getSelectedItem().toString(),
                authors.get(author.getSelectedIndex()),
                (int)price.getSelectedItem(),
                (int)stock.getSelectedItem());
            DatabaseConnection.setNewBook(newBook);
            System.out.println(newBook.toString());
            frame.repaint();
        });
        frame.add(registerBookButton);

        return newBookPanel;
    }

    // New Author Panel
    public static JPanel showNewAuthorMenu(JPanel frame) {
        JPanel newBookPanel = new JPanel();
        newBookPanel.setBounds(16, 48, 520, 360);
        JTextField nameTF = AuxiliarMethods.createAskField(frame, "Name", 160, 32, true);
        JTextField surnameTF = AuxiliarMethods.createAskField(frame, "Surname", 160, 82, true);

        JButton registerAuthorButton = new JButton("Register Author");
        registerAuthorButton.setFont(new Font("Consolas", Font.BOLD, 16));
        registerAuthorButton.setBounds(112, 308, 172, 32);
        registerAuthorButton.addActionListener(e -> {
            Author author = new Author(nameTF.getText(), surnameTF.getText());
            DatabaseConnection.setNewAuthor(author);
        });
        frame.add(registerAuthorButton);
        return newBookPanel;
    }


    // Show the inventory as normaly, but adds an "Add to cart Button" to each row of the table
    public static JScrollPane showSalesTable() {
        // ArrayList<SalesTicket> tickets = DatabaseConnection.checkTicketHistory(customer.getId());
        String[] columns = {"Ticket", "customerID", "ISBN", "$", "Quantity", "date"};
        JTable salesTable = new JTable(DatabaseConnection.getSales(), columns);
        salesTable.setRowHeight(32);
        salesTable.getColumnModel().getColumn(0).setPreferredWidth(24);   // TICKET
        salesTable.getColumnModel().getColumn(1).setPreferredWidth(92);   // CUSTOMER_ID
        salesTable.getColumnModel().getColumn(2).setPreferredWidth(92);   // ISBN
        salesTable.getColumnModel().getColumn(3).setPreferredWidth(16);   // PRICE
        salesTable.getColumnModel().getColumn(4).setPreferredWidth(16);   // QUANTITY
        salesTable.getColumnModel().getColumn(5).setPreferredWidth(32);   // SALE DATE

        JScrollPane scrollPane = new JScrollPane(salesTable);
        // JScrollPane scrollPane = new JScrollPane(purchaseHistoryTable);
        scrollPane.setBounds(0, 0, 520, 356);
        // Add the Button Column to the table
        return scrollPane;
    }    
}