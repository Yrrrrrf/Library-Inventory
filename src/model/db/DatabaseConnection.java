package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.library.Book;
import model.payment.SalesTicket;
import model.user.Author;
import model.user.Customer;

public class DatabaseConnection {

    // Set the default connection URL
    final static String defaultURL = "jdbc:mysql://localhost:3306/libraryinventory";


    /**
     * Checks the login password of a user
     * <p>
     * Returns true if the password is the same that in the table
     * <p>
     * Returns false if the password isn't the same that in the table
     * @param loginAs
     * @param mail
     * @param password
     * @return
     */
    public static boolean Login(String loginAs, String mail, String password) {
        boolean correctPassword = false;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "SELECT * FROM " + loginAs + " WHERE mail = ?";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setString(1, mail); // Replace parameter
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            if (resultSet.next()) 
                if (password.equals(resultSet.getString("password")))
                    correctPassword = true;
            else return correctPassword; // Return false if main & password isn't the same
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}
        return correctPassword;
    }


    /**
     * Adds a new customer to the customer table with the gived information
     * @param mail
     * @param password
     * @param name
     * @param surname
     * @param address
     * @param phoneNumber
     * @param birthday
     */
    public static void SignInUser(String mail, String password, String name, String surname, String address, String phoneNumber, String birthday) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "INSERT INTO customer (mail, password, name, surname, address, phoneNumber, birthday) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setString(1, mail);          // Replace mail
            pStatement.setString(2, password);      // Replace password
            pStatement.setString(3, name);          // Replace name
            pStatement.setString(4, surname);       // Replace surname
            pStatement.setString(5, address);       // Replace address
            pStatement.setString(6, phoneNumber);   // Replace phoneNumber
            pStatement.setString(7, birthday);      // Replace birthday
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("New Customer Signed In Properly!");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Get all the authors in the Author table 
     * @return
     */
    public static ArrayList<Author> getAuthors() {
        ArrayList<Author> authors = new ArrayList<Author>();
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM author");
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) authors.add(new Author(
                resultSet.getString("name"),
                resultSet.getString("surname"),
                Integer.valueOf(resultSet.getString("author_id"))));
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}
        return authors;
    }


    /**
     * Get the Author object associated with that author_id
     * @param author_id
     * @return Author
     */
    public static Author getAuthor(int author_id) {
        Author author = null;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM author WHERE author_id = ?");
            pStatement.setInt(1, author_id); // Replace the author_id that we want 
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) author = new Author(
                resultSet.getString("name"),
                resultSet.getString("surname"),
                Integer.valueOf(resultSet.getString("author_id")));
            // System.out.println(author.toString());
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}
        return author;
    }


    /**
     * Get all the books in the book table
     * @return
     */
    public static ArrayList<Book> getBooks() {
        ArrayList<Author> authors = getAuthors();
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM book WHERE stock != 0");
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                books.add(new Book(
                    resultSet.getString("ISBN"),
                    resultSet.getString("name"), 
                    resultSet.getString("cathegory"), 
                    authors.get(Integer.valueOf(resultSet.getString("author_id")) - 1),
                    resultSet.getInt("price"),
                    resultSet.getInt("stock")));
            }
            // for (int i = 0; i < books.size(); i++) System.out.println(books.get(i).toString()); // Books list
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}    
        return books;
    }


    /**
     * Adds a new book to the book to the book table
     * @param book
     */
    public static void setNewBook(Book book) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "INSERT INTO book (ISBN, name, cathegory, price, stock, author_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setString(1, book.getISBN());       // Replace ISBN
            pStatement.setString(2, book.getName());       // Replace book name
            pStatement.setString(3, book.getCathegory());  // Replace cathegory
            pStatement.setInt(4, book.getPrice());         // Replace price
            pStatement.setInt(5, book.getStock());         // Replace stock
            pStatement.setInt(6, book.getAuthor().getAuthor_id()); // Replace author_id
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("New Book Added to Inventory!");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Return a Book object with using the ISBN to consult that book's information
     * @param ISBN
     * @return Book
     */
    public static Book getBook(String ISBN) {
        Book book = null;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM book WHERE ISBN = ?");
            pStatement.setString(1, ISBN);
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                book = new Book(
                    resultSet.getString("ISBN"),
                    resultSet.getString("name"), 
                    resultSet.getString("cathegory"), 
                    getAuthor(resultSet.getInt("author_id")),
                    Integer.valueOf(resultSet.getString("price")),
                    Integer.valueOf(resultSet.getString("stock")));
            }
            // System.out.println(book.toString());
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}    
        return book;
    }


    /**
     * Creates a new author & adds it to the Author list
     * @param author
     */
    public static void setNewAuthor(Author author) {        
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            // String query = "INSERT INTO book (ISBN, name, cathegory, price, stock) VALUES (?,?,?,?,?)";
            String query = "INSERT INTO author (name, surname) VALUES (?,?)";
            PreparedStatement pStatement = connection.prepareStatement(query);
            // pStatement.setString(1, userValues); // Replace Values
            pStatement.setString(1, author.getName()); // Replace name
            pStatement.setString(2, author.getSurname()); // Replace surname
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("New Author added to Database!");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Adds a new row with the gived book to a customer's cart
     * @param customer_id
     * @param ISBN
     * @param quantity
     */
    public static void addBookToCart(int customer_id, String ISBN, int quantity) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "INSERT INTO cart_items (customer_id, ISBN, quantity) VALUES (?,?,?);";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt   (1, customer_id);                    // Replace parameter
            pStatement.setString(2, getBook(ISBN).getISBN());   // Replace parameter
            pStatement.setInt   (3, quantity); // Replace quantity (add Only 1 per click)
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println(ISBN + " added to " + customer_id + " cart");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Get a List of the books that where in the cart of the customer
     * @param customer_id
     * @return Books in the cart
     */
    public static ArrayList<Book> getCartBooks(int customer_id) {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement
                ("SELECT book.*, cart_items.quantity FROM cart_items, book WHERE customer_id = ?" +
                " AND cart_items.ISBN = book.ISBN AND book.stock >= cart_items.quantity;");
            pStatement.setInt(1, customer_id); // Replace the customer_ID
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                books.add(new Book(
                    resultSet.getString("ISBN"),
                    resultSet.getString("name"), 
                    resultSet.getString("cathegory"), 
                    getAuthor(resultSet.getInt("author_id")),
                    Integer.valueOf(resultSet.getString("price")),
                    Integer.valueOf(resultSet.getString("quantity")))); // Set that book's quantity as the total in cart
            }
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}    
        return books;
    }


    /**
     * Get the customer with the given customer_id
     * @param customer_id
     * @return Customer
     */
    public static Customer getCustomer(int customer_id) {
        Customer customer = null;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement
                ("SELECT * FROM customer WHERE customer_id = ?");
            pStatement.setInt(1, customer_id); // Replace the customer_ID
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) { // Build the customer
                customer = new Customer(resultSet.getInt("customer_id"),resultSet.getString("name"),resultSet.getString("surname"));
                    customer.setAddress(resultSet.getString("customer_id"));
                    customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                    customer.setBirthday(resultSet.getString("birthday"));
            }
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}   
        return customer; 
    }


    /**
     * Creates a ticket associated to a customer
     * @param customer_id
     * @param price
     */
    public static void generateTicket(int customer_id, int price) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "INSERT INTO ticket (customer_id, sale_date, price) VALUES (?, CURDATE(), ?)";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, customer_id);  // Replace customer_id
            pStatement.setInt(2, price);        // Replace price
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("Ticket created properly");
            // Ask
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Saves a ticket in the ticket_items table that are in the cart of the customer in that moment
     * @param customer_id
     */
    public static void saveTicket(int customer_id) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "INSERT INTO ticket_items (ticket_id, ISBN, quantity) " +
                "(SELECT MAX(ticket_id), ISBN, quantity " +
                "FROM ticket, cart_items " +
                "WHERE ticket.customer_id = ? " +
                "GROUP BY ISBN, quantity)";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, customer_id);      // Replace customer_id
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("Ticket Saved!");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Removes all the row in the cart table associated to a customer
     * @param customer_id
     */
    public static void deleteCart(int customer_id) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "DELETE FROM cart_items WHERE customer_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, customer_id);      // Replace customer_id
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("Emptied Cart");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Returns all the tickets associated to a customer
     * @param customer_id
     * @return ticket history of customer
     */
    public static ArrayList<SalesTicket> checkTicketHistory(int customer_id) {
        ArrayList<SalesTicket> tickets = new ArrayList<SalesTicket>();
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT ticket_id, sale_date FROM ticket WHERE customer_id = ?");
            pStatement.setInt(1, customer_id);   // Replace customer_id
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                tickets.add(new SalesTicket(
                    resultSet.getInt("ticket_id"),
                    getCustomer(customer_id), 
                    checkTicketsContent(resultSet.getInt("ticket_id")),
                    resultSet.getString("sale_date")
                ));
            }
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}    
        return tickets;
    }


    /**
     * Returns the books associated to a ticket
     * @param ticket_id int
     * @return Books associated to a ticket 
     */
    public static ArrayList<Book> checkTicketsContent(int ticket_id) {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT ISBN, quantity FROM ticket_items WHERE ticket_id = ?");
            pStatement.setInt(1, ticket_id);   // Replace customer_id
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                Book book = getBook(resultSet.getString("ISBN")); // Build the book inside that ticket
                book.setStock(resultSet.getInt("quantity")); // replace stock for the "quantity" in that ticket
                books.add(book); // Build all the book
            }
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}    
        return books;
    }

    /**
     * Fet all the purchased books by the user (customer_id)
     * @param customer_id
     * @return books purchased
     */
    public static String[][] getAllPurchasedBooks(int customer_id) {
        String[][] data = new String[2048][6];
        int totalBooks = 0;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM ticket t JOIN ticket_items ti " +
            "ON t.ticket_id = ti.ticket_id WHERE customer_id = ?");
            pStatement.setInt(1, customer_id);   // Replace customer_id
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                data[totalBooks][0] = String.valueOf(resultSet.getInt("ticket_id"));
                data[totalBooks][1] = resultSet.getString("ISBN");
                data[totalBooks][2] = getBook(resultSet.getString("ISBN")).getName();
                data[totalBooks][3] = String.valueOf(getBook(resultSet.getString("ISBN")).getPrice());
                data[totalBooks][4] = String.valueOf(resultSet.getInt("quantity"));
                data[totalBooks][5] = resultSet.getString("sale_date");
                totalBooks++;
            }
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}    
        return data;
    }


    /**
     *  Get all the books sold before
     * @return All sold books list
     */
    public static String[][] getSales() {
        String[][] sales = new String[2048][6];
        int totalBooks = 0;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT DISTINCT * FROM ticket t " + 
            "JOIN ticket_items ti ON t.ticket_id = ti.ticket_id JOIN book b ON b.ISBN = ti.ISBN;");
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) {
                sales[totalBooks][0] = String.valueOf(resultSet.getInt("ticket_id"));
                sales[totalBooks][1] = resultSet.getString("customer_id");
                sales[totalBooks][2] = resultSet.getString("ISBN");
                sales[totalBooks][3] = String.valueOf(getBook(resultSet.getString("ISBN")).getPrice());
                sales[totalBooks][4] = String.valueOf(resultSet.getInt("quantity"));
                sales[totalBooks][5] = resultSet.getString("sale_date");
                totalBooks++;
            }
            // System.out.println(author.toString());
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}
        return sales;
    }


    /**
     * Used to check how many copies of a book a customer have in his cart
     * @param customer_id
     * @param ISBN
     * @return number of copies
     */
    public static int checkBookAvailability(int customer_id, String ISBN) {
        int quantity = 0;
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT quantity FROM cart_items WHERE customer_id = ? AND ISBN = ?");
            pStatement.setInt(1, customer_id);
            pStatement.setString(2, ISBN);
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) quantity = resultSet.getInt("quantity");
            connection.close(); // Close the Connection
        } catch (SQLException ex) {System.out.println(ex);}
        return quantity;
    }


    /**
     * Adds a copy of a book on an existing row in user's cart the table
     * @param customer_id
     * @param ISBN
     */
    public static void addABook(int customer_id, String ISBN) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "UPDATE cart_items SET quantity = quantity + 1  WHERE customer_id = ? AND ISBN = ?;";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, customer_id);  // Replace customer_id
            pStatement.setString(2, ISBN);      // Replace ISBN
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("+1 of " + ISBN + " added to customer " + customer_id + " cart");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    /**
     * Removes 1 copy of a book on an existing row in user's cart the table
     * @param customer_id
     * @param ISBN
     */
    public static void substractABook(int customer_id, String ISBN) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "UPDATE cart_items SET quantity = quantity - 1  WHERE customer_id = ? AND ISBN = ?;";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, customer_id);  // Replace customer_id
            pStatement.setString(2, ISBN);      // Replace ISBN
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println("-1 of " + ISBN + " substracted of customer " + customer_id + " cart");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    // Use if the custmer already have the book in his cart
    /**
     * Removes the row that contains that book in the customer's cart
     * @param customer_id
     * @param ISBN
     */
    public static void removeBook(int customer_id, String ISBN) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            String query = "DELETE FROM cart_items WHERE customer_id = ? AND ISBN = ?;";
            PreparedStatement pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, customer_id);  // Replace customer_id
            pStatement.setString(2, ISBN);      // Replace ISBN
            pStatement.executeUpdate(); // Excecute Update
            connection.close(); // Close the Connection
            System.out.println(ISBN + " removed from the customer " + customer_id + " cart");
        } catch (SQLException ex) {System.out.println(ex);}
    }


    // get all the tickets from a customer
    public static String[][] getCsutomerTicketsHistory(int customer_id) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT ticket_id, sale_date, price FROM ticket WHERE customer_id = ?");
            pStatement.setInt(1, customer_id);
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query

            String[][] customerTickets = new String[getTotalCustomerTickets(customer_id)][3]; 
            int i = 0; 
            while (resultSet.next()) {
                customerTickets[i][0] = resultSet.getString("ticket_id");
                customerTickets[i][1] = resultSet.getString("sale_date");
                customerTickets[i][2] = resultSet.getString("price");
                i++;
            }
            connection.close(); // Close the Connection
            return customerTickets;
        } catch (Exception ex) {System.out.println(ex);}
        return null;
    }
    
    
    // get the total number of tickets created by a same customer
    private static int getTotalCustomerTickets(int customer_id) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT COUNT(*) tickets FROM ticket WHERE customer_id = ?");
            pStatement.setInt(1, customer_id);
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) return resultSet.getInt("tickets"); // Return the number of tickets   
            return resultSet.getInt("tickets");
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }


    // get the Items from a specific ticket_id
    public static String[][] getItemsFromTicket(int ticket_id) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT ticket_items.ISBN, book.name, book.price, ticket_items.quantity" +
                " FROM ticket_items, book WHERE ticket_id = ? AND book.ISBN = ticket_items.ISBN");
            pStatement.setInt(1, ticket_id);
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query

            String[][] customerTickets = new String[getTotalItemsFromTicket(ticket_id)][4]; 
            int i = 0; 
            while (resultSet.next()) {
                customerTickets[i][0] = resultSet.getString("ISBN");
                customerTickets[i][1] = resultSet.getString("name");
                customerTickets[i][2] = "" + resultSet.getInt("quantity");
                customerTickets[i][3] = "" + resultSet.getInt("price");
                i++;
            }
            connection.close(); // Close the Connection
            return customerTickets;
        } catch (Exception ex) {System.out.println(ex);}
        return null;
    }


    // get the number of items from a ticket_id
    private static int getTotalItemsFromTicket(int ticket_id) {
        try {
            Connection connection = DriverManager.getConnection(defaultURL, "root", "1006");
            PreparedStatement pStatement = connection.prepareStatement("SELECT COUNT(*) items FROM ticket_items WHERE ticket_id = ?");
            pStatement.setInt(1, ticket_id);
            ResultSet resultSet = pStatement.executeQuery(); // Excecute Query
            while (resultSet.next()) return resultSet.getInt("items"); // Return the number of tickets   
            return resultSet.getInt("items");
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }


}