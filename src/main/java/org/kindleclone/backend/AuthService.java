package main.java.org.kindleclone.backend;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AuthService {
    public static boolean registerUser(String username, String name, int age,
                                       String gender, String email, String password) {
        try (Connection conn = DatabaseManager.getConnection()) {
            // Check if username or email already exists
            String checkSql = "SELECT id FROM users WHERE username = ? OR email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                checkStmt.setString(2, email);
                if (checkStmt.executeQuery().next()) {
                    return false;
                }
            }

            // Insert new user
            String insertSql = "INSERT INTO users (username, name, age, gender, email, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, username);
                stmt.setString(2, name);
                stmt.setInt(3, age);
                stmt.setString(4, gender);
                stmt.setString(5, email);
                stmt.setString(6, BCrypt.hashpw(password, BCrypt.gensalt()));
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("email")
                    );
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}