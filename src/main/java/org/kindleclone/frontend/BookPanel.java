package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.Book;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class BookPanel extends JPanel {
    private final Book book;
    private final JLabel titleLabel;
    private final JLabel coverLabel;

    public BookPanel(MainDashboard dashboard, Book book) {
        this.book = book;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(100, 200));
        setOpaque(true);

        // Book cover
        coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(100, 200));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Load image from path (if available)
        if (book.getCoverImagePath() != null) {
            ImageIcon icon = new ImageIcon(book.getCoverImagePath());
            Image img = icon.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
            coverLabel.setIcon(new ImageIcon(img));
        } else {
            coverLabel.setText("No Cover");
            coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
            coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        }

        // Book title
        titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setMaximumSize(new Dimension(100, 200));
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setVerticalTextPosition(SwingConstants.TOP);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        add(coverLabel);
        add(Box.createVerticalStrut(5));
        add(titleLabel);

        // Clickable to show book details
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboard.showBookDetails(book);
            }
        });

        updateTheme();
    }

    public void updateTheme() {
        boolean isDark = ThemeManager.isDarkMode();
        Color bgColor = isDark ? new Color(60, 60, 60) : new Color(255, 255, 255);
        Color textColor = isDark ? Color.WHITE : Color.BLACK;

        setBackground(bgColor);
        setForeground(textColor);

        titleLabel.setForeground(textColor);
        coverLabel.setBackground(bgColor);
    }
}
