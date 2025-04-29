package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.AuthService;
import main.java.org.kindleclone.backend.SessionManager;
import main.java.org.kindleclone.backend.User;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JLabel statusLabel;

    public LoginScreen() {
        setTitle("Readible - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        initComponents();
        updateTheme();
        setVisible(true);
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        JLabel headerLabel = new JLabel("Login to Readible", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(headerLabel, gbc);

        // Email Field
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Password Field
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        });
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Status Label
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(statusLabel, gbc);

        // Buttons Panel
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        gbc.gridy = 4;
        add(buttonPanel, gbc);

        // Button Actions
        loginButton.addActionListener(e -> attemptLogin());
        registerButton.addActionListener(e -> {
            new RegisterScreen().setVisible(true);
            this.dispose();
        });
    }

    private void attemptLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both email and password!");
            return;
        }

        try {
            User user = AuthService.loginUser(email, password);
            if (user != null) {
                SessionManager.login(user);
                this.dispose();
                // Pass the authenticated user to MainDashboard
                new Welcome(user).setVisible(true);
            } else {
                statusLabel.setText("Invalid email or password!");
                passwordField.setText("");
            }
        } catch (Exception ex) {
            statusLabel.setText("Login failed. Please try again.");
            ex.printStackTrace();
        }
    }

    private void updateTheme() {
        // Set main background
        getContentPane().setBackground(ThemeManager.getBackgroundColor());

        // Get all colors from ThemeManager
        Color bgColor = ThemeManager.getBackgroundColor();
        Color btnColor = ThemeManager.getButtonColor();
        Color fieldBg = ThemeManager.getFieldColor();
        Color textColor = ThemeManager.getTextColor();
        Color btnTextColor = ThemeManager.getButtonTextColor();

        // Apply to all components
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(bgColor);
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    if (subComp instanceof JButton) {
                        subComp.setBackground(btnColor);
                        subComp.setForeground(btnTextColor);
                        ((JButton) subComp).setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                    } else {
                        subComp.setForeground(textColor);
                    }
                }
            } else if (comp instanceof JLabel) {
                comp.setForeground(textColor);
            }
        }

        // Style text fields
        emailField.setBackground(fieldBg);
        emailField.setForeground(textColor);
        passwordField.setBackground(fieldBg);
        passwordField.setForeground(textColor);

        // Style status label
        statusLabel.setForeground(textColor);

        // Force UI update
        revalidate();
        repaint();
    }}