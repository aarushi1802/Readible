package main.java.org.kindleclone.backend;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    // Add Book (Uses DAO Instead of Direct SQL)
    public boolean addBook(String title, String author, String description, String filePath, String coverImage) {
        try {
            bookDAO.addBook(title, author, description, filePath, coverImage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
