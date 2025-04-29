package main.java.org.kindleclone.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final DatabaseManager dbManager;

    public BookService() {
        this.dbManager = new DatabaseManager();
    }

    public List<Book> getAllBooks() {
        return queryBooks("SELECT * FROM books");
    }

    public List<Book> getLibraryBooks(User currentUser) {
        int userId = SessionManager.getCurrentUser().getId();
        String sql = "SELECT b.* FROM books b " +
                "JOIN user_library ul ON b.id = ul.book_id " +
                "WHERE ul.user_id = ?";
        return queryBooks(sql, userId);
    }

    public List<Book> getFavoriteBooks(User currentUser) {
        int userId = SessionManager.getCurrentUser().getId();
        String sql = "SELECT b.* FROM books b " +
                "JOIN user_favorites uf ON b.id = uf.book_id " +
                "WHERE uf.user_id = ?";
        return queryBooks(sql, userId);
    }

    public boolean addToLibrary(int bookId) {
        int userId = SessionManager.getCurrentUser().getId();
        String sql = "INSERT INTO user_library (user_id, book_id) VALUES (?, ?)";
        return executeUpdate(sql, userId, bookId);
    }

    public boolean addToFavorites(int bookId) {
        int userId = SessionManager.getCurrentUser().getId();
        String sql = "INSERT INTO user_favorites (user_id, book_id) VALUES (?, ?)";
        return executeUpdate(sql, userId, bookId);
    }

    private List<Book> queryBooks(String sql, Object... params) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("description"),
                            rs.getString("cover_image"),
                            rs.getString("category")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Add this method to BookService.java
    public List<Book> searchBooks(String query) {
        String sql = "SELECT * FROM books WHERE " +
                "LOWER(title) LIKE ? OR " +
                "LOWER(author) LIKE ? OR " +
                "LOWER(category) LIKE ?";

        String searchTerm = "%" + query.toLowerCase() + "%";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);

            return getBooksFromResultSet(stmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Book> getBooksFromResultSet(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("description"),
                    rs.getString("cover_image"),
                    rs.getString("category")
            ));
        }
        return books;
    }

    private boolean executeUpdate(String sql, Object... params) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}