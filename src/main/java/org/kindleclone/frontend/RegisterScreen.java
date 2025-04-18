package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.AuthService;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, loginButton;
    private JCheckBox showPasswordCheck;
    private final AuthService authService;

    public RegisterScreen(JFrame parentFrame) {
        authService = new AuthService();
        setTitle("Register");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Close Parent Window If Open
        if (parentFrame != null) {
            parentFrame.dispose();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // **Title Label**
        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // **Name Field**
        gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Name:"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // **Email Field**
        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        add(emailField, gbc);

        // **Password Field**
        gbc.gridy = 3; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // **Confirm Password Field**
        gbc.gridy = 4; gbc.gridx = 0;
        add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // **Show Password Checkbox**
        showPasswordCheck = new JCheckBox("Show Password");
        showPasswordCheck.setOpaque(true);
        gbc.gridy = 5; gbc.gridx = 1;
        add(showPasswordCheck, gbc);
        showPasswordCheck.addActionListener(e -> {
            boolean show = showPasswordCheck.isSelected();
            passwordField.setEchoChar(show ? (char) 0 : '•');
            confirmPasswordField.setEchoChar(show ? (char) 0 : '•');
        });

        // **Register Button**
        registerButton = new JButton("Sign Up");
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        add(registerButton, gbc);

        // **Login Button (Switch to Login Page)**
        loginButton = new JButton("Already have an account? Login");
        gbc.gridy = 7;
        add(loginButton, gbc);

        // **Register Button Click**
        registerButton.addActionListener(e -> registerUser());

        // **Switch to Login Page**
        loginButton.addActionListener(e -> {
            dispose();
            new LoginScreen(this);
        });

        // **Apply Theme**
        updateTheme();
        setVisible(true);
    }

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isRegistered = AuthService.registerUser(name, email, password);

        if (isRegistered) {
            JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginScreen(this);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email may be already in use.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateTheme() {
        boolean isDark = ThemeManager.isDarkMode();
        Color bgColor = Color.WHITE;
        Color textColor = isDark ? Color.WHITE : Color.BLACK;
        Color btnColor = isDark ? new Color(100, 50, 180) : new Color(180, 140, 255);

        getContentPane().setBackground(bgColor);

        loginButton.setBackground(btnColor);
        loginButton.setForeground(textColor);
        registerButton.setBackground(btnColor);
        registerButton.setForeground(textColor);
    }
}
