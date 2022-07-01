package model.library;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;

import model.db.DatabaseConnection;
import model.user.Customer;

public abstract class Inventory {
    private HashMap<Customer, ArrayList<Book>> borrowedBooks;

    public static JTable getBooksListTable() {
        String[] columns = {"ISNB", "Name", "$", "Author", "Cathegory", "Stock"};
        ArrayList<Book> books = DatabaseConnection.getBooks();

        String[][] data = new String[books.size()][6];
        for (int i = 0; i < books.size(); i++) {
            data[i][0] = books.get(i).getISBN();
            data[i][1] = books.get(i).getName();
            data[i][2] = String.valueOf(books.get(i).getPrice());
            data[i][3] = books.get(i).getAuthor().toString();
            data[i][4] = books.get(i).getCathegory();
            data[i][5] = String.valueOf(books.get(i).getStock());
        }
        JTable inventoryTable = new JTable(data, columns);
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(96); // ISNB
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(200); // NAME
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(24); // PRICE
        inventoryTable.getColumnModel().getColumn(5).setPreferredWidth(32); // Stock
        return inventoryTable;
    }

    //* Getters
    // public HashMap<Book, Integer> getStock() {return this.stock;}

    public HashMap<Customer,ArrayList<Book>> getBorrowedBooks() {return this.borrowedBooks;}


    //* Setters
    // public void setStock(HashMap<Book, Integer> stock) {this.stock = stock;}

    public void setBorrowedBooks(HashMap<Customer,ArrayList<Book>> borrowedBooks) {this.borrowedBooks = borrowedBooks;}

}