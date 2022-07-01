package model.payment;

import java.util.ArrayList;
import java.util.Date;
import model.library.Book;
import model.user.Customer;

public class LoanTicket {

    private Date requestDate, limitDate;
    private Customer customer;
    private ArrayList<Book> borrowedBooks;


    LoanTicket(Customer customer, ArrayList<Book> borrowedBooks) {
        this.customer = customer;
        this.borrowedBooks = borrowedBooks;
        // this.requestDate = date.
    }

    
    //* Getters
    public Date getRequestDate() {return this.requestDate;}

    public Date getLimitDate() {return this.limitDate;}

    public Customer getCustomer() {return this.customer;}

    public ArrayList<Book> getBorrowedBooks() {return this.borrowedBooks;}


    //* Setters
    public void setRequestDate(Date requestDate) {this.requestDate = requestDate;}

    public void setLimitDate(Date limitDate) {this.limitDate = limitDate;}

    public void setCustomer(Customer customer) {this.customer = customer;}

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {this.borrowedBooks = borrowedBooks;}

}