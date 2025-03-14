package main.java.org.kindleclone.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginScreen() {
        setTitle("Amazon Kindle Clone - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Username Field
        usernameField = new JTextField();
        add(new JLabel("Username:"));
        add(usernameField);

        // Password Field
        passwordField = new JPasswordField();
        add(new JLabel("Password:"));
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel);

        // Button Listeners
        loginButton.addActionListener(new LoginHandler());
        registerButton.addActionListener(e -> {
            new RegisterScreen().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    // Login Action
    private class LoginHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean isAuthenticated = main.java.org.kindleclone.backend.AuthService.loginUser(username, password);
            if (isAuthenticated) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                new MainDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials, try again!");
            }
        }
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
