package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.frontend.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class BookDetailsScreen extends JPanel {
    private MainDashboard dashboard;
    private String title, author, description, coverPath;

    public BookDetailsScreen(MainDashboard dashboard, String title, String author, String description, String coverPath) {
        this.dashboard = dashboard;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverPath = coverPath;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topPanel.setBackground(Color.WHITE);

        JLabel bookCover = new JLabel(new ImageIcon(new ImageIcon(coverPath).getImage().getScaledInstance(150, 220, Image.SCALE_SMOOTH)));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel bookTitle = new JLabel(title);
        bookTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        bookTitle.setForeground(Color.BLACK);

        JLabel bookAuthor = new JLabel("by " + author);
        bookAuthor.setFont(new Font("SansSerif", Font.ITALIC, 16));
        bookAuthor.setForeground(Color.BLACK);

        JTextArea bookDescription = new JTextArea(description);
        bookDescription.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bookDescription.setWrapStyleWord(true);
        bookDescription.setLineWrap(true);
        bookDescription.setEditable(false);
        bookDescription.setBackground(Color.WHITE);
        bookDescription.setForeground(Color.BLACK);

        infoPanel.add(bookTitle);
        infoPanel.add(bookAuthor);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(bookDescription);

        topPanel.add(bookCover);
        topPanel.add(infoPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton likeButton = new JButton("❤️ Like");
        JButton downloadButton = new JButton("⬇️ Download");
        JButton backButton = new JButton("🔙 Back");

        likeButton.setBackground(ThemeManager.getButtonColor());
        likeButton.setForeground(ThemeManager.getTextColor());
        downloadButton.setBackground(ThemeManager.getButtonColor());
        downloadButton.setForeground(ThemeManager.getTextColor());
        backButton.setBackground(ThemeManager.getButtonColor());
        backButton.setForeground(ThemeManager.getTextColor());

        likeButton.addActionListener(e -> {
            dashboard.addToFavorites(title);
            JOptionPane.showMessageDialog(this, title + " added to Favorites!");
        });

        downloadButton.addActionListener(e -> {
            dashboard.addToLibrary(title);
            JOptionPane.showMessageDialog(this, title + " downloaded!");
        });

        backButton.addActionListener(e -> dashboard.showMainDashboard());

        buttonPanel.add(likeButton);
        buttonPanel.add(downloadButton);
        buttonPanel.add(backButton);

        add(topPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
