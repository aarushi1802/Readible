package main.java.org.kindleclone.frontend.components;

import javax.swing.*;
import java.awt.*;

public class SearchBar extends JPanel {
    private JTextField searchField;
    private JButton searchButton;

    public SearchBar() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 35));
        setBackground(Color.WHITE);

        // 🔹 Rounded Border for Unified Look
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));

        // 🔹 Search Field
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);

        // 🔹 Search Button (🔍 Inside Box)
        searchButton = new JButton("🔍");
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        searchButton.setContentAreaFilled(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 🔹 Custom UI for Seamless Integration
        searchButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw Background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);

                super.paint(g, c);
            }
        });

        // 🔹 Add Action Listener for Search
        searchButton.addActionListener(e -> performSearch());

        // 🔹 Adding Components in One Unified Box
        add(searchField, BorderLayout.CENTER);
        add(searchButton, BorderLayout.EAST);
    }

    // 🔍 Search Function (Prints to Console)
    private void performSearch() {
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            System.out.println("Searching for: " + query);
            // Implement actual search logic here
        } else {
            System.out.println("Please enter something to search.");
        }
    }

    // ✅ Getter for search text (Optional)
    public String getSearchQuery() {
        return searchField.getText().trim();
    }
}
