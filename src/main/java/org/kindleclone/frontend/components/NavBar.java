package main.java.org.kindleclone.frontend.components;

import main.java.org.kindleclone.frontend.utils.ThemeManager;
import main.java.org.kindleclone.frontend.RegisterScreen;
import main.java.org.kindleclone.frontend.LoginScreen;
import main.java.org.kindleclone.backend.AuthService;
import main.java.org.kindleclone.backend.SessionManager;

import javax.swing.*;
import java.awt.*;

public class NavBar extends JPanel {
    private JToggleButton themeToggle;
    private JButton homeBtn, favBtn, profileBtn;
    private JPopupMenu profileMenu;
    private JFrame parentFrame; // ✅ Store parent frame reference

    public NavBar(JFrame parentFrame) {
        this.parentFrame = parentFrame; // ✅ Assign parent frame
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 50));

        // **Left Panel (Library & Favorites)**
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
        leftPanel.setOpaque(false);

        homeBtn = createModernButton("Library");
        favBtn = createModernButton("Favorites");

        leftPanel.add(homeBtn);
        leftPanel.add(favBtn);

        // **Center Panel (Search Bar)**
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        SearchBar searchBar = new SearchBar();
        centerPanel.add(searchBar);

        // **Right Panel (Dark Mode Toggle + Profile)**
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setOpaque(false);

        themeToggle = new JToggleButton();
        themeToggle.setFocusPainted(false);
        themeToggle.setPreferredSize(new Dimension(35, 35));
        themeToggle.setBorder(BorderFactory.createEmptyBorder());
        themeToggle.setContentAreaFilled(false);
        themeToggle.setOpaque(false);

        // **Profile Button with Emoji**
        profileBtn = new JButton("👤");
        profileBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        profileBtn.setFocusPainted(false);
        profileBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // **Initialize Profile Menu**
        profileMenu = new JPopupMenu();
        updateProfileMenu(); // ✅ Dynamically create menu based on login state

        profileBtn.addActionListener(e -> profileMenu.show(profileBtn, 0, profileBtn.getHeight()));

        rightPanel.add(themeToggle);
        rightPanel.add(profileBtn); // ✅ Add profile button to navbar

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // **Apply Theme at Start**
        SwingUtilities.invokeLater(this::updateTheme);

        // **Toggle Theme Event**
        themeToggle.addActionListener(e -> {
            ThemeManager.toggleTheme(parentFrame);
            updateTheme();
        });
    }

    // **Update Profile Menu Based on Login State**
    public void updateProfileMenu()
    {
        profileMenu.removeAll(); // ✅ Clear old menu

        if (SessionManager.isUserLoggedIn()) {
            // **If Logged In: Show Profile & Logout**
            JMenuItem profileOption = new JMenuItem("Profile");
            JMenuItem logoutOption = new JMenuItem("Logout");

            logoutOption.addActionListener(e -> {
                SessionManager.logout();
                JOptionPane.showMessageDialog(null, "Logged out successfully.");
                updateProfileMenu(); // ✅ Refresh UI
                updateTheme();
            });

            profileMenu.add(profileOption);
            profileMenu.add(logoutOption);
        } else {
            // **If Not Logged In: Show Sign In & Login**
            JMenuItem signInOption = new JMenuItem("Sign In");
            JMenuItem loginOption = new JMenuItem("Log In");

            signInOption.addActionListener(e -> new RegisterScreen(parentFrame));
            loginOption.addActionListener(e -> {
                new LoginScreen(parentFrame);
                SessionManager.login(); // ✅ Set login state
                updateProfileMenu(); // ✅ Refresh UI immediately
                updateTheme();
            });

            profileMenu.add(signInOption);
            profileMenu.add(loginOption);
        }
        profileMenu.revalidate();
        profileMenu.repaint();
    }

    // **Update Theme Properly**
    public void updateTheme() {
        Color lightNavbar = new Color(180, 140, 255);
        Color darkNavbar = new Color(100, 50, 180);
        Color lightButton = new Color(180, 140, 255);
        Color darkButton = new Color(100, 50, 180);

        boolean isDark = ThemeManager.isDarkMode();

        setBackground(isDark ? darkNavbar : lightNavbar);

        Color menuBg = isDark ? new Color(80, 40, 150) : new Color(200, 160, 255);
        Color menuText = isDark ? Color.WHITE : Color.BLACK;

        profileMenu.setBackground(menuBg);
        profileMenu.setBorder(BorderFactory.createLineBorder(menuBg.darker(), 1));

        for (Component comp : profileMenu.getComponents()) {
            if (comp instanceof JMenuItem) {
                JMenuItem menuItem = (JMenuItem) comp;
                menuItem.setOpaque(true);
                menuItem.setBackground(menuBg);
                menuItem.setForeground(menuText);
            }
        }

        homeBtn.setBackground(isDark ? darkButton : lightButton);
        homeBtn.setForeground(isDark ? Color.WHITE : Color.BLACK);

        favBtn.setBackground(isDark ? darkButton : lightButton);
        favBtn.setForeground(isDark ? Color.WHITE : Color.BLACK);

        themeToggle.setBackground(isDark ? darkButton : lightButton);
        themeToggle.setForeground(isDark ? Color.WHITE : Color.BLACK);
        themeToggle.setText(isDark ? "🌙" : "☀");

        profileBtn.setBackground(isDark ? darkButton : lightButton);
        profileBtn.setForeground(isDark ? Color.WHITE : Color.BLACK);

        repaint();
        revalidate();
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));

        boolean isDark = ThemeManager.isDarkMode();
        button.setBackground(isDark ? new Color(100, 50, 180) : new Color(180, 140, 255));
        button.setForeground(isDark ? Color.WHITE : Color.BLACK);

        return button;
    }
}
