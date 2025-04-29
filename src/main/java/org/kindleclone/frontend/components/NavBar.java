package main.java.org.kindleclone.frontend.components;

import main.java.org.kindleclone.backend.SessionManager;
import main.java.org.kindleclone.frontend.*;
import main.java.org.kindleclone.backend.User;
import main.java.org.kindleclone.frontend.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NavBar extends JPanel {
    private JButton themeButton;
    private JButton homeBtn, libraryBtn, favoritesBtn, profileBtn;
    private JPopupMenu profileMenu;
    private MainDashboard parentFrame;
    private SearchBar searchBar;

        public NavBar(MainDashboard parentFrame) {
            this.parentFrame = parentFrame;
            initComponents();
            updateTheme();
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(1000, 40));
            setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

            // Left side buttons
            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
            leftPanel.setOpaque(false);

            homeBtn = createBorderlessButton("Home");
            libraryBtn = createBorderlessButton("Library");
            favoritesBtn = createBorderlessButton("Favorites");

            homeBtn.addActionListener(e -> parentFrame.showMainView());
            libraryBtn.addActionListener(e -> parentFrame.showLibraryView());
            favoritesBtn.addActionListener(e -> parentFrame.showFavoritesView());

            leftPanel.add(homeBtn);
            leftPanel.add(libraryBtn);
            leftPanel.add(favoritesBtn);

            // Search bar in center
            searchBar = new SearchBar();
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(searchBar);
            searchBar.setSearchListener(parentFrame::searchBooks);

            // Right side buttons
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            rightPanel.setOpaque(false);

            themeButton = createBorderlessButton(ThemeManager.isDarkMode() ? "🌙" : "☀"); // Now using JButton
            themeButton.addActionListener(e -> {
                ThemeManager.toggleTheme();
                parentFrame.updateAllThemes();
                updateTheme();
                // Update the theme button icon
                themeButton.setText(ThemeManager.isDarkMode() ? "🌙" : "☀");
            });

            profileBtn = createBorderlessButton(SessionManager.isUserLoggedIn() ? "👤" : "🔒");
            profileBtn.addActionListener(e -> showProfileMenu());

            rightPanel.add(themeButton);
            rightPanel.add(profileBtn);

            add(leftPanel, BorderLayout.WEST);
            add(centerPanel, BorderLayout.CENTER);
            add(rightPanel, BorderLayout.EAST);
        }

    private String getProfileIconText() {
        User user = SessionManager.getCurrentUser();
        if (user != null) {
            // Use first character of username if available
            return user.getUsername().isEmpty() ? "👤" :
                    user.getUsername().substring(0, 1).toUpperCase();
        }
        return "🔒";
    }

    private JButton createBorderlessButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            return button;
        }

    private void showProfileMenu() {
        profileMenu = new JPopupMenu();
        updateProfileMenu();
        profileMenu.show(profileBtn, 0, profileBtn.getHeight());
    }

    private void updateProfileMenu() {
        profileMenu.removeAll();

        if (SessionManager.isUserLoggedIn()) {
            JMenuItem profileItem = new JMenuItem("Profile");
            JMenuItem logoutItem = new JMenuItem("Logout");

            logoutItem.addActionListener(e -> {
                SessionManager.logout();
                profileBtn.setText(getProfileIconText());
                parentFrame.returnToLogin();
                updateTheme();
            });

            profileMenu.add(profileItem);
            profileMenu.add(logoutItem);
        } else {
            JMenuItem loginItem = new JMenuItem("Login");
            JMenuItem registerItem = new JMenuItem("Register");

            loginItem.addActionListener(e -> {
                parentFrame.dispose();
                new LoginScreen().setVisible(true);
            });
            registerItem.addActionListener(e -> {
                parentFrame.dispose();
                new RegisterScreen().setVisible(true);
            });
            profileMenu.add(loginItem);
            profileMenu.add(registerItem);

            profileMenu.setBackground(ThemeManager.getBackgroundColor());
            profileMenu.setForeground(ThemeManager.getTextColor());
            for (Component item : profileMenu.getComponents()) {
                if (item instanceof JMenuItem) {
                    ((JMenuItem) item).setBackground(ThemeManager.getBackgroundColor());
                    ((JMenuItem) item).setForeground(ThemeManager.getTextColor());
                }
            }
        }
    }

    public void updateTheme() {
        Color bgColor = ThemeManager.getBackgroundColor();
        Color textColor = ThemeManager.getTextColor();

        setBackground(bgColor);

        // Update all buttons
        Component[] buttons = {homeBtn, libraryBtn, favoritesBtn, themeButton, profileBtn};
        for (Component btn : buttons) {
            if (btn != null) {
                btn.setForeground(textColor);
                btn.setBackground(bgColor);
            }
        }

        // Update search bar if it exists
        if (searchBar != null) {
            searchBar.updateTheme();
        }

        // Update profile menu if it exists
        if (profileBtn != null) {
            profileBtn.setText(getProfileIconText());
                }
        // Update theme button icon
        if (themeButton != null) {
            themeButton.setText(ThemeManager.isDarkMode() ? "🌙" : "☀");
        }
    }
}