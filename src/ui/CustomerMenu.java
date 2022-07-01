package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import model.db.DatabaseConnection;
import model.library.Inventory;
import model.user.Customer;
import test.ButtonColumn;
import ui.util.AuxiliarMethods;

public class CustomerMenu {

    public static Customer customer = new Customer(1, "", "");
    JPanel workSpace = new JPanel();

    public CustomerMenu(JPanel frame) {
        AuxiliarMethods.createUserMenu();

        workSpace.setBounds(16, 48, 520, 360);
        workSpace.setLayout(null);
        frame.add(workSpace);

        // Show Inventory Button
        JButton showInventory = new JButton("Show Inventory");
        showInventory.setBounds(572, 54, 120, 40);
        showInventory.addActionListener(e -> {
            workSpace.removeAll();
            workSpace.add(showInventoryToCustomer());
            workSpace.repaint();
        });
        frame.add(showInventory);

        // Show Users Cart
        JButton showCartButton = AuxiliarMethods.createImageButton("", "src/img/cart.jpg", 40, 40);
        showCartButton.setBounds(572, 114, 120, 40);
        showCartButton.addActionListener(e -> {
            updateCart();
        });
        frame.add(showCartButton);

        // Show Users Ticket History
        JButton showTicketsButton = AuxiliarMethods.createImageButton("", "src/img/salesHistory.jpg", 40, 40);
        showTicketsButton.setBounds(572, 174, 120, 40);
        showTicketsButton.addActionListener(e -> {
            workSpace.removeAll();
            // TODO
            workSpace.add(showCustomersTicketHistory());
            workSpace.repaint();
        });
        frame.add(showTicketsButton);


        // Actual Version
        UserInterface.addVersion(frame);
    }


    // Show the inventory as normaly, but adds an "Add to cart Button" to each row of the table
    public static JScrollPane showInventoryToCustomer() {
        JTable inventoryTable = Inventory.getBooksListTable();
        inventoryTable.setRowHeight(32);
        inventoryTable.addColumn(new TableColumn());
        inventoryTable.getColumnModel().getColumn(6).setPreferredWidth(32); // Table Button
        // Table action Performed method (used below in the ButtonColumn)
        Action tableButtonAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int quantity = DatabaseConnection.checkBookAvailability(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                if (quantity == 0) DatabaseConnection.addBookToCart(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0), 1);
                else DatabaseConnection.addABook(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
            }
        };
        // Add the Button Column to the table
        new ButtonColumn(inventoryTable, tableButtonAction, 6);
        inventoryTable.getColumnModel().getColumn(6).setHeaderValue("Add To Cart");
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBounds(0, 0, 520, 360);
        return scrollPane;
    }


    public JScrollPane showCustomerCart(JPanel workSpace) {
        JTable inventoryTable = customer.getCart().getCartListTable(customer.getId());
        inventoryTable.setRowHeight(32);
        // SUBSTARC ONE BOOK
        Action deleteBookAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int quantity = DatabaseConnection.checkBookAvailability(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                if (quantity > 1) DatabaseConnection.substractABook(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                else DatabaseConnection.removeBook(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                updateCart();
            }
        };
        new ButtonColumn(inventoryTable, deleteBookAction, 4); // Add button to (-) column

        // ADD ONE BOOK
        Action addBookAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int quantity = DatabaseConnection.checkBookAvailability(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                if (quantity != 0) DatabaseConnection.addABook(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                else DatabaseConnection.addABook(customer.getId(), (String) table.getValueAt(table.getSelectedRow(), 0));
                updateCart();
            }
        };
        new ButtonColumn(inventoryTable, addBookAction, 5); // Add button to (+) column

        JScrollPane scrollPane = new JScrollPane(inventoryTable); 
        scrollPane.setBounds(0, 0, 520, 300);
        workSpace.add(scrollPane);
        return scrollPane;
    }


    public void updateCart() {
        workSpace.removeAll();
        showCustomerCart(workSpace);
        showPriceLabel(workSpace);
        showPurchaseButton(workSpace);
        showTrashButton(workSpace);
        workSpace.repaint();
    }


    // returns the label that have the total price (calculated) inside
    public static JLabel showPriceLabel(JPanel workSpace) {
        JLabel priceTextLabel = AuxiliarMethods.createLabel("Total Price: ");
        priceTextLabel.setBackground(Color.BLACK);
        priceTextLabel.setBounds(240, 320, 120, 40);
        workSpace.add(priceTextLabel);

        JLabel priceLabel = AuxiliarMethods.createLabel("$ " + customer.getCart().getTotalPrice());
        priceLabel.setFont(new Font("Consolas", Font.ITALIC, 20));
        priceLabel.setBackground(Color.BLACK);
        priceLabel.setBounds(320, 324, 120, 40);
        workSpace.add(priceLabel);
        return priceLabel;
    }


    public JButton showPurchaseButton(JPanel workSpace) {
        JButton purchaseButton = new JButton("Buy!");
        purchaseButton.setBounds(420, 320, 80, 40);
        purchaseButton.addActionListener(e -> {
            // First of all create the ticket. Then add the items of that ticket to the ticket_items table
            DatabaseConnection.generateTicket(customer.getId(), customer.getCart().getTotalPrice());
            DatabaseConnection.saveTicket(customer.getId());
            DatabaseConnection.deleteCart(customer.getId());
            workSpace.removeAll();
            showCustomerCart(workSpace);
            showPriceLabel(workSpace);
            showPurchaseButton(workSpace);
            showTrashButton(workSpace);
            workSpace.repaint();
        });
        workSpace.add(purchaseButton);
        return purchaseButton;
    }


    private JButton showTrashButton(JPanel workSpace) {
        // JButton trashButton = AuxiliarMethods.createImageButton("", "src/img/trash.jpg", 32, 32);
        JButton trashButton = new JButton("Emptied Cart");
        trashButton.setBackground(Color.LIGHT_GRAY);
        // trashButton.setForeground(Color.);
        trashButton.setBounds(40, 320, 120, 40);
        trashButton.addActionListener(e -> {
            DatabaseConnection.deleteCart(customer.getId());
            updateCart();
        });
        workSpace.add(trashButton);
        return trashButton;
    }



    // Show the inventory as normaly, but adds an "Add to cart Button" to each row of the table
    public static JScrollPane showCustomersBuyHistory(int ticket_id) {
        // ArrayList<SalesTicket> tickets = DatabaseConnection.checkTicketHistory(customer.getId());
        String[] columns = {"ISBN", "Name", "Quantity", "Price"};
        JTable purchaseHistoryTable = new JTable(DatabaseConnection.getItemsFromTicket(ticket_id), columns);
        purchaseHistoryTable.setRowHeight(32);
        purchaseHistoryTable.getColumnModel().getColumn(0).setPreferredWidth(92);   // ISBN
        purchaseHistoryTable.getColumnModel().getColumn(1).setPreferredWidth(200);  // NAME
        purchaseHistoryTable.getColumnModel().getColumn(2).setPreferredWidth(16);   // QUANTITY
        purchaseHistoryTable.getColumnModel().getColumn(3).setPreferredWidth(16);   // PRICE

        JScrollPane scrollPane = new JScrollPane(purchaseHistoryTable);
        // JScrollPane scrollPane = new JScrollPane(purchaseHistoryTable);
        scrollPane.setBounds(0, 0, 520, 356);
        // Add the Button Column to the table
        return scrollPane;
    }


    // Show the inventory as normaly, but adds an "Add to cart Button" to each row of the table
    public JScrollPane showCustomersTicketHistory() {
        String[] columns = {"Ticket ID", "Sale Date", "Price"};
        JTable purchaseHistoryTable = new JTable(DatabaseConnection.getCsutomerTicketsHistory(customer.getId()), columns);
        purchaseHistoryTable.setRowHeight(32);
        purchaseHistoryTable.getColumnModel().getColumn(0).setPreferredWidth(32);  // TICKET ID
        purchaseHistoryTable.getColumnModel().getColumn(1).setPreferredWidth(128); // SALE DATE
        purchaseHistoryTable.getColumnModel().getColumn(2).setPreferredWidth(64);  // PRICE

        Action showItemsButtonAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                workSpace.removeAll();
                workSpace.add(showCustomersBuyHistory(Integer.valueOf((String) table.getValueAt(table.getSelectedRow(), 0))));
                workSpace.repaint();
            }
        };

        new ButtonColumn(purchaseHistoryTable, showItemsButtonAction, 0);
        JScrollPane scrollPane = new JScrollPane(purchaseHistoryTable);
        scrollPane.setBounds(0, 0, 520, 356);
        return scrollPane;
    }

}