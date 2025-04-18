package main.java.org.kindleclone;
import main.java.org.kindleclone.backend.DatabaseManager;

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
    }
}
