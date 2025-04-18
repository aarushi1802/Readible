package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.BookService;
import main.java.org.kindleclone.backend.DatabaseManager;
import main.java.org.kindleclone.frontend.components.NavBar;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.List;

public class MainDashboard extends JFrame {
    private NavBar navBar;
    private JPanel mainContent;
    private JScrollPane scrollPane;
    private JPanel bookContainer;
    private BookService bookService;

    public MainDashboard() {
        setTitle("Readible");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        navBar = new NavBar(this);
        add(navBar, BorderLayout.NORTH);

        bookService = new BookService(); // Initialize BookService

        // **Main Content Panel**
        mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(240, 240, 250));

        // **Scroll Pane**
        scrollPane = new JScrollPane(mainContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // **Load Books from Database**
        loadBooks();

        setVisible(true);
    }

    private void loadBooks() {
        mainContent.removeAll(); // Clear previous data

        try {
            Connection connection = DatabaseManager.getConnection();
            String query = "SELECT title, author, description, cover_image, category FROM books";// ✅ Fetch description
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            Map<String, JPanel> categoryPanels = new LinkedHashMap<>();

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String description = rs.getString("description");
                String coverImage = rs.getString("cover_image");
                String category = rs.getString("category");

                // ✅ Ensure description is not null or empty
                if (description == null || description.trim().isEmpty()) {
                    description = "Description not available";
                }

                boolean isDark = ThemeManager.isDarkMode();

                // **Check if category exists, otherwise create a new panel**
                JPanel bookContainer = categoryPanels.get(category);
                if (bookContainer == null) {
                    bookContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
                    bookContainer.setBackground((isDark ? new Color(100, 50, 180) : new Color(180, 140, 255)));

                    JLabel categoryLabel = new JLabel(category);
                    categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
                    categoryLabel.setForeground(isDark ? Color.WHITE : Color.BLACK);
                    mainContent.add(categoryLabel);

                    JScrollPane rowScrollPane = new JScrollPane(bookContainer);
                    rowScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    rowScrollPane.setBorder(BorderFactory.createEmptyBorder());

                    mainContent.add(rowScrollPane);
                    categoryPanels.put(category, bookContainer);
                }

                // **Add book to the correct category**
                bookContainer.add(new BookPanel(this, title, author, description, coverImage));
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        mainContent.revalidate();
        mainContent.repaint();
    }


    // **Show Book Details**
    public void showBookDetails(String title, String author, String description, String coverPath) {
        getContentPane().removeAll();
        add(new BookDetailsScreen(this, title, author, description, coverPath), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // **Return to Main Dashboard**
    public void showMainDashboard() {
        getContentPane().removeAll();
        add(navBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // **Favorites & Library**
    public void addToFavorites(String title) {
        System.out.println(title + " added to Favorites (Update database here)");
    }

    public void addToLibrary(String title) {
        System.out.println(title + " added to Library (Update database here)");
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}
