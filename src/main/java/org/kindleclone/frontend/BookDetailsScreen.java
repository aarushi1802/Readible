package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.Book;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BookDetailsScreen extends JPanel {
    private final MainDashboard dashboard;
    private final Book book;
    private JLabel title;
    private JButton favButton, libButton, backButton;
    private JPanel buttonPanel;

    public BookDetailsScreen(MainDashboard dashboard, Book book) {
        this.dashboard = dashboard;
        this.book = book;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title panel
        title = new JLabel(book.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        favButton = createButton("Add to Favorites", this::handleFavButton);
        libButton = createButton("Add to Library", this::handleLibButton);
        backButton = createButton("Back", this::handleBackButton);

        buttonPanel.add(favButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(libButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
        updateTheme();
    }

    private JButton createButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.addActionListener(listener);
        return button;
    }

    private void handleFavButton(ActionEvent e) {
        dashboard.addToFavorites(book.getId());
    }

    private void handleLibButton(ActionEvent e) {
        dashboard.addToLibrary(book.getId());
    }

    private void handleBackButton(ActionEvent e) {
        dashboard.returnToMainDashboard();
    }

    public void updateTheme() {
        // Apply background to main panel and button panel
        Color bgColor = ThemeManager.getBackgroundColor();
        setBackground(bgColor);
        buttonPanel.setBackground(bgColor);

        // Apply text color
        title.setForeground(ThemeManager.getTextColor());

        // Apply button styles
        Color buttonColor = ThemeManager.getButtonColor();
        Color buttonTextColor = ThemeManager.getButtonTextColor();

        for (JButton button : new JButton[]{favButton, libButton, backButton}) {
            button.setBackground(buttonColor);
            button.setForeground(buttonTextColor);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
        }

        revalidate();
        repaint();
    }
}