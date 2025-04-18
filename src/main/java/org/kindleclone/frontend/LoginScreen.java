package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.AuthService;
import main.java.org.kindleclone.frontend.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginScreen(JFrame parentFrame) {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // **Ensure Parent Frame Closes Properly**
        if (parentFrame != null) {
            parentFrame.dispose();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // **Username Field**
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);  // Changed label from Username to Email
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // **Password Field**
        gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // **Login & Register Buttons**
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, gbc);

        // **Button Listeners**
        loginButton.addActionListener(e -> loginUser());
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterScreen(this);
        });

        // **Apply Theme**
        updateTheme();
        setVisible(true);
    }

    private void loginUser() {
        if (usernameField == null || passwordField == null) {
            JOptionPane.showMessageDialog(this, "UI components not initialized!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isAuthenticated = AuthService.loginUser(email, password);
        if (isAuthenticated) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            MainDashboard dashboard = new MainDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials, try again!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTheme() {
        boolean isDark = ThemeManager.isDarkMode();
        Color bgColor = Color.WHITE;
        Color textColor = isDark ? Color.WHITE : Color.BLACK;
        Color btnColor = isDark ? new Color(100, 50, 180) : new Color(180, 140, 255);

        getContentPane().setBackground(bgColor);

        if (usernameField != null) usernameField.setBackground(bgColor);
        if (passwordField != null) passwordField.setBackground(bgColor);

        if (usernameField != null) usernameField.setForeground(textColor);
        if (passwordField != null) passwordField.setForeground(textColor);

        loginButton.setBackground(btnColor);
        loginButton.setForeground(textColor);
        registerButton.setBackground(btnColor);
        registerButton.setForeground(textColor);
    }
}
