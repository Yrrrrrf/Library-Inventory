package model.payment;

import java.util.ArrayList;

import javax.swing.JTable;

import model.library.Book;
import model.user.Customer;

public class SalesTicket {

    int ticketID;
    Customer customer;
    ArrayList<Book> booksSold = new ArrayList<Book>();
    String soldDate;


    public SalesTicket(int ticket_id, Customer customer, ArrayList<Book> booksSold, String date) {
        this.ticketID = ticket_id;
        this.customer = customer;
        this.booksSold = booksSold;
        this.soldDate = date;
    }


    public JTable getTicketListTable() {
        String[] columns = {"ISNB", "Name", "$", "Quantity"};
        String[][] data = new String[this.booksSold.size()][4];

        for (int i = 0; i < this.booksSold.size(); i++) {
            data[i][0] = this.booksSold.get(i).getISBN();
            data[i][1] = this.booksSold.get(i).getName();
            data[i][2] = String.valueOf(this.booksSold.get(i).getPrice());
            data[i][3] = String.valueOf(this.booksSold.get(i).getStock());
        }
        JTable inventoryTable = new JTable(data, columns);
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(96); // ISNB
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(200); // NAME
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(24); // PRICE
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(32); // QUANTITY
        return inventoryTable;
    }


    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < booksSold.size(); i++) totalPrice += booksSold.get(i).getPrice() * booksSold.get(i).getStock();
        return totalPrice;
    }


    public void listBooks() {
        for (int i = 0; i < booksSold.size(); i++) 
                System.out.println(this.booksSold.get(i).toString());
    }

    //* Setters
    public void setCustomer(Customer customer) {this.customer = customer;}

    public void setBooksSold(ArrayList<Book> booksSold) {this.booksSold = booksSold;}

    public void setTicketID(int ticketID) {this.ticketID = ticketID;}

    public void setSoldDate(String soldDate) {this.soldDate = soldDate;}

    
    //* Getters
    public Customer getCustomer() {return this.customer;}

    public ArrayList<Book> getBooksSold() {return this.booksSold;}

    public int getTicketID() {return this.ticketID;}

    public String getSoldDate() {return this.soldDate;}

}