package main.java.org.kindleclone.frontend.components;

import main.java.org.kindleclone.frontend.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchBar extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private SearchListener searchListener;

    public interface SearchListener {
        void onSearch(String query);
    }

    public SearchBar() {
        initComponents();
        updateTheme();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        setMaximumSize(new Dimension(300, 40));

        searchField = new JTextField(20);
        searchButton = new JButton("🔍");

        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add search action on button click
        searchButton.addActionListener(e -> performSearch());

        // Add search action on Enter key press
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        add(searchField, BorderLayout.CENTER);
        add(searchButton, BorderLayout.EAST);
    }

    public void setSearchListener(SearchListener listener) {
        this.searchListener = listener;
    }

    public void updateTheme() {
        boolean isDark = ThemeManager.isDarkMode();
        Color bgColor = ThemeManager.LIGHT_FIELD;
        Color textColor = Color.black;
        Color btnColor = ThemeManager.LIGHT_FIELD;
        Color btnTextColor = Color.BLACK;

        setBackground(ThemeManager.getBackgroundColor());
        searchField.setBackground(bgColor);
        searchField.setForeground(textColor);
        searchField.setCaretColor(textColor);
        searchButton.setBackground(btnColor);
        searchButton.setForeground(btnTextColor);
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (searchListener != null && !query.isEmpty()) {
            searchListener.onSearch(query);
        }
    }
}