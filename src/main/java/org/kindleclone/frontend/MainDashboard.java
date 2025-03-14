package main.java.org.kindleclone.frontend;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import main.java.org.kindleclone.backend.BookDAO;

public class MainDashboard extends JFrame {
    private JPanel bookPanel;

    public MainDashboard() {
        setTitle("Amazon Kindle Clone - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to Kindle Clone", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Book List Panel
        bookPanel = new JPanel();
        bookPanel.setLayout(new GridLayout(0, 1));
        loadBooks();
        add(new JScrollPane(bookPanel), BorderLayout.CENTER);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });
        add(logoutButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadBooks() {
        BookDAO bookDAO = new BookDAO();
        List<String> books = bookDAO.getAllBooks();

        for (String book : books) {
            JButton bookButton = new JButton(book);
            bookButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Reading: " + book));
            bookPanel.add(bookButton);
        }
    }

    public static void main(String[] args) {
        new MainDashboard();
}
}

