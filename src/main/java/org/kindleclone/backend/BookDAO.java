package main.java.org.kindleclone.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String INSERT_BOOK = "INSERT INTO kindle_db.books (title, author, description, file_path, cover_image, category) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_BOOKS = "SELECT * FROM kindle_db.books";
    private static final String GET_BOOKS_BY_CATEGORY = "SELECT title, author FROM kindle_db.books WHERE category=?";
    private static final String DELETE_BOOK = "DELETE FROM kindle_db.books WHERE id=?";

    public void addBook(String title, String author, String description, String filePath, String coverImage, String category) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BOOK)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, description);
            stmt.setString(4, filePath);
            stmt.setString(5, coverImage);
            stmt.setString(6, category);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllBooks() {
        List<String> books = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_BOOKS)) {
            while (rs.next()) {
                books.add(rs.getString("title") + " - " + rs.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<String> getBooksByCategory(String category) {
        List<String> books = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BOOKS_BY_CATEGORY)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(rs.getString("title") + " - " + rs.getString("author"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void deleteBook(int bookId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BOOK)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
