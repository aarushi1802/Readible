package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.User;
import javax.swing.*;
import java.awt.*;

public class Welcome extends JFrame {
    private JButton startButton;

    public Welcome(User user) {
        setTitle("Welcome to Readible");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(100, 50, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel(
                "<html><center>Welcome " + user.getUsername() + "!<br>Let's start your reading journey</center></html>",
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLACK);

        startButton = createBorderlessButton("Start Reading");
        startButton.addActionListener(e -> {
            dispose();
            new MainDashboard(user).setVisible(true);
        });

        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(startButton, BorderLayout.SOUTH);
        add(panel);
    }

    private JButton createBorderlessButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            button.setForeground(Color.BLACK);
            return button;
        }
    }
