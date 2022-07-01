package model.user;

import java.util.ArrayList;
import model.library.Book;

public class Author extends Person{

    ArrayList<Book> writtenBooks;
    int author_id;

    public Author(String name, String surname, int author_id) {
        super(name, surname);
        this.author_id = author_id;
    }
    
    public Author(String name, String surname) {
        super(name, surname);
    }

    @Override
    public String getUserValues() {
        return this.getId() + "Author\n" 
             + this.getName() + " " + this.getSurname()+ "\n"
             + this.showWrittenBooks();
    }

    public void addNewWrittenBook(Book book) {this.writtenBooks.add(book);}

    public String showWrittenBooks() {
        String wbooks = "Written Books: \n";
        for (int i = 0; i < writtenBooks.size(); i++)
            wbooks += "  " + writtenBooks.get(i).toString() + "\n";
        return wbooks;
        
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getSurname();
    }


    //* Setters
    public void setWrittenBooks(ArrayList<Book> writtenBooks) {this.writtenBooks = writtenBooks;}

    public void setAuthor_id(int author_id) {this.author_id = author_id;}

    //* Getters
    public ArrayList<Book> getWrittenBooks() {return this.writtenBooks;}

    public int getAuthor_id() {return this.author_id;}

}