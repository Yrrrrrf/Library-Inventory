package model.user;

import javax.swing.JTextField;

import model.library.Book;
import model.payment.Cart;

public class Customer extends Person {

    private Cart cart;
    // private ArrayList<Book> borroweBooks;

    public Customer(int customer_id, String name, String surname) {
        super(name, surname);
        this.setId(customer_id);
        this.cart = new Cart(customer_id);
    }


    public Customer(JTextField[] customerData, String date) {
        super(customerData[0].getText(), customerData[1].getText()); // Name & Surname
        setAddress(customerData[2].getText()); // Address
        setBirthday(date); // Date
        setPhoneNumber(customerData[3].getText()); // Phone Number
        setMail(customerData[4].getText()); // Mail
        setPassword(customerData[5].getText()); // Password
    }


    @Override
    public String getUserValues() {
        return this.getMail() + ", "
             + this.getPassword() + ", "
             + this.getName() + ", "
             + this.getSurname() + ", "
             + this.getAddress() + ", "
             + this.getPhoneNumber() + ", "
             + this.getBirthday();
    }


    public boolean aFieldIsEmpty() {
        if (this.getMail().equals("") ||
            this.getPassword().equals("") ||
            this.getName().equals("") ||
            this.getSurname().equals("") ||
            this.getAddress().equals("") ||
            this.getPhoneNumber().equals("") ||
            this.getBirthday().equals("")) {
                return true; // If even a Field is empty
        } else return false; // All the fields have a value
    }

    //! Still need the limit return date implementation
    // public String showBorrowedBooks() {
    //     if (this.borroweBooks.isEmpty()) return "No borrowed books";
    //     else {
    //         String wbooks = "Borrowed Books: \n";
    //         for (int i = 0; i < borroweBooks.size(); i++) wbooks += "  " + borroweBooks.get(i).toString() + "\n";
    //         return wbooks;
    //     }
    // }


    public void addBookToCart(Book book) {cart.addBook(book);}

    public void deleteBookFromCart(Book book) {cart.removeBook(book);}


    //* Getters
    public Cart getCart() {return this.cart;}

    // public ArrayList<Book> getBorroweBooks() {return this.borroweBooks;}


    //* Setters
    public void setCart(Cart cart) {this.cart = cart;}

    // public void setBorroweBooks(ArrayList<Book> borroweBooks) {this.borroweBooks = borroweBooks;}

}