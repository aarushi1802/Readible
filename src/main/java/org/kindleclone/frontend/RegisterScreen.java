package main.java.org.kindleclone.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public RegisterScreen() {
        setTitle("Amazon Kindle Clone - Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Username Field
        usernameField = new JTextField();
        add(new JLabel("Username:"));
        add(usernameField);

        // Email Field
        emailField = new JTextField();
        add(new JLabel("Email:"));
        add(emailField);

        // Password Field
        passwordField = new JPasswordField();
        add(new JLabel("Password:"));
        add(passwordField);

        // Buttons
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        add(buttonPanel);

        // Button Listeners
        registerButton.addActionListener(new RegisterHandler());
        backButton.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    // Register Action
    private class RegisterHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            boolean isRegistered = main.java.org.kindleclone.backend.AuthService.registerUser(username, password, email);
            if (isRegistered) {
                JOptionPane.showMessageDialog(null, "Registration Successful! Please Login.");
                new LoginScreen().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Registration Failed! Try Again.");
            }
        }
    }

    public static void main(String[] args) {
        new RegisterScreen();
    }
}
