package main.java.org.kindleclone.backend;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    // Add Book
    public boolean addBook(String title, String author, String description, String filePath, String coverImage, String category) {
        try {
            bookDAO.addBook(title, author, description, filePath, coverImage, category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Books by Category
    public List<String> getBooksByCategory(String category) {
        return bookDAO.getBooksByCategory(category);
    }

    // Get All Books
    public List<String> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    // Delete Book
    public boolean deleteBook(int bookId) {
        try {
            bookDAO.deleteBook(bookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
