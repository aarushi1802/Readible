package main.java.org.kindleclone;

import main.java.org.kindleclone.backend.DatabaseManager;
import main.java.org.kindleclone.backend.BookDAO;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connection successful!");
            } else {
                System.out.println("❌ Database connection failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BookDAO bookDAO = new BookDAO();

        // 📌 1. Add a Book
        bookDAO.addBook("The Alchemist", "Paulo Coelho", "A journey of self-discovery", "/books/alchemist.pdf", "/images/alchemist.jpg");

        // 📌 2. Fetch Books
        System.out.println("Books in Database: " + bookDAO.getAllBooks());

        // 📌 3. Delete a Book (Manually id pass karni padegi)
        bookDAO.deleteBook(1);
    }
}
