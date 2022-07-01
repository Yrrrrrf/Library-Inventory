package model.library;

import model.user.Author;

public class Book {
    
    private String name, ISBN, cathegory;
    private Author author;
    private int price, stock;
    public static final String[] CATHEGORIES = {"Science Fiction", "Fantasy", 
    "Soocial Critic", "Futurology", "Philosophy"}; // Need some new cathegories

    public Book(String ISBN, String name, String cathegory, Author author, int price, int stock) {
        this.ISBN = ISBN;
        this.name = name;
        this.cathegory = cathegory;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return this.ISBN + " "+ this.name + ", " + this.author.toString();
    }


    //* Getters
    public String getName() {return this.name;}

    public String getISBN() {return this.ISBN;}

    public Author getAuthor() {return this.author;}

    public String getCathegory() {return this.cathegory;}    
    
    public int getPrice() {return this.price;}

    public int getStock() {return this.stock;}


    //* Setters
    public void setName(String name) {this.name = name;}

    public void setAuthor(Author author) {this.author = author;}

    public void setISBN(String ISBN) {this.ISBN = ISBN;}

    public void setCathegory(String cathegory) {this.cathegory = cathegory;}

    public void setPrice(int price) {this.price = price;}

    public void setStock(int stock) {this.stock = stock;}

}