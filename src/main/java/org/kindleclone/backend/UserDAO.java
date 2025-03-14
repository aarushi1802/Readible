package main.java.org.kindleclone.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String INSERT_USER = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    private static final String LOGIN_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";

    public boolean registerUser(String username, String password, String email) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(LOGIN_QUERY)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // ✅ User found, login successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
