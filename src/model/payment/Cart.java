package model.payment;

import java.util.ArrayList;

import javax.swing.JTable;

import model.db.DatabaseConnection;
import model.library.Book;

public class Cart {
    
    ArrayList<Book> books = new ArrayList<Book>();
    int customer_id;

    public Cart(int customer_id) {
        this.customer_id = customer_id;
        updateCart();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    
    public void removeBook(Book book) {
        this.books.remove(book);
    }


    public JTable getCartListTable(int user_id) {
        updateCart();
        String[] columns = {"ISNB", "Book's Name", "Price", "Quantity", "Delete", "Add"};

        String[][] data = new String[this.books.size()][6];
        for (int i = 0; i < this.books.size(); i++) {
            data[i][0] = this.books.get(i).getISBN();
            data[i][1] = this.books.get(i).getName();
            data[i][2] = String.valueOf(this.books.get(i).getPrice());
            data[i][3] = String.valueOf(this.books.get(i).getStock());
            data[i][4] = "-"; // To maje visible the delete one copy option on the table
            data[i][5] = "+"; // To make visible the add another copy option on the table
        }
        JTable inventoryTable = new JTable(data, columns);
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(96); // ISNB
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(200); // NAME
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(32); // PRICE
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(32); // Stock
        inventoryTable.getColumnModel().getColumn(4).setPreferredWidth(32); // -
        inventoryTable.getColumnModel().getColumn(5).setPreferredWidth(32); // +
        return inventoryTable;
    }


    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < books.size(); i++) totalPrice+= books.get(i).getStock() * books.get(i).getPrice();
        return totalPrice;
    }

    public void updateCart() {
        this.books = DatabaseConnection.getCartBooks(customer_id);
    }

    //* Getters
    public ArrayList<Book> getBooks() {return this.books;}
    
    //* Setters
    public void setBooks(ArrayList<Book> books) {this.books = books;}

}