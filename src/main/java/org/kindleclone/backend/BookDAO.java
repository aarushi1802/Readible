package main.java.org.kindleclone.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String INSERT_BOOK = "INSERT INTO books (title, author, description, file_path, cover_image) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_BOOKS = "SELECT * FROM books";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id=?";

    public void addBook(String title, String author, String description, String filePath, String coverImage) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BOOK)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, description);
            stmt.setString(4, filePath);
            stmt.setString(5, coverImage);
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
