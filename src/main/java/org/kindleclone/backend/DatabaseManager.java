package main.java.org.kindleclone.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/kindle_db";
    private static final String USER = "root"; // ✅ Tumhara MySQL username
    private static final String PASSWORD = "arya080824#"; // ✅ MySQL ka password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed!");
        }
    }
}
