package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.AuthService;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class RegisterScreen extends JFrame {
    private JTextField nameField, usernameField, emailField, ageField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> genderBox;
    private JLabel statusLabel;
    private JButton registerBtn, loginBtn;

    public RegisterScreen() {
        setTitle("Readible - Register");
        setSize(500, 550); // Increased height for additional field
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        JLabel headerLabel = new JLabel("Create New Account", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(headerLabel, gbc);

        // Name Field
        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 1;
        add(new JLabel("Full Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Username Field
        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Age Field
        gbc.gridy = 3; gbc.gridx = 0;
        add(new JLabel("Age:"), gbc);
        ageField = new JTextField(20);
        ageField.addKeyListener(new NumericKeyListener());
        gbc.gridx = 1;
        add(ageField, gbc);

        // Gender Field
        gbc.gridy = 4; gbc.gridx = 0;
        add(new JLabel("Gender:"), gbc);
        genderBox = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other", "Prefer not to say"});
        gbc.gridx = 1;
        add(genderBox, gbc);

        // Email Field
        gbc.gridy = 5; gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Password Field
        gbc.gridy = 6; gbc.gridx = 0;
        add(new JLabel("Password (min 8 chars):"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Confirm Password
        gbc.gridy = 7; gbc.gridx = 0;
        add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptRegistration();
                }
            }
        });
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // Status Label
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridy = 8; gbc.gridx = 0; gbc.gridwidth = 2;
        add(statusLabel, gbc);

        // Buttons Panel
        registerBtn = new JButton("Register");
        loginBtn = new JButton("Already have an account? Login");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.add(registerBtn);
        buttonPanel.add(loginBtn);
        gbc.gridy = 9; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Action Listeners
        registerBtn.addActionListener(e -> attemptRegistration());
        loginBtn.addActionListener(e -> {
            this.dispose();
            new LoginScreen();
        });
    }

    private void attemptRegistration() {
        // Collect field values
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gender = genderBox.getSelectedItem().toString();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate inputs
        if (name.isEmpty() || username.isEmpty() || ageStr.isEmpty() ||
                gender.equals("Select Gender") || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Please fill all fields!");
            return;
        }

        if (username.contains(" ")) {
            statusLabel.setText("Username cannot contain spaces!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match!");
            return;
        }

        if (password.length() < 8) {
            statusLabel.setText("Password must be at least 8 characters!");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 0 || age > 120) {
                statusLabel.setText("Please enter a valid age (0-120)");
                return;
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Age must be a number!");
            return;
        }

        // Attempt registration
        try {
            boolean success = AuthService.registerUser(username, name, age, gender, email, password);
            if (success) {
                statusLabel.setForeground(new Color(0, 100, 0));
                statusLabel.setText("Registration successful! Redirecting to login...");

                Timer timer = new Timer(1500, e -> {
                    dispose();
                    new LoginScreen().setVisible(true);
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                statusLabel.setText("Username or email already registered!");
            }
        } catch (Exception ex) {
            statusLabel.setText("Registration failed. Please try again.");
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

        // Style input fields
        Component[] fields = {nameField, usernameField, ageField, emailField,
                passwordField, confirmPasswordField, genderBox};
        for (Component field : fields) {
            field.setBackground(fieldBg);
            field.setForeground(textColor);
        }

        // Style status label
        statusLabel.setForeground(textColor);

        // Force UI update
        revalidate();
        repaint();
    }

    private class NumericKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                e.consume();
            }
        }
    }
}