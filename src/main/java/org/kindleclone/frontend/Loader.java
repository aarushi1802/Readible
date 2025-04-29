package main.java.org.kindleclone.frontend;

import javax.swing.*;
import java.awt.*;

public class Loader extends JFrame {
    public Loader() {
        setTitle("Readible");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(180, 140, 255));

        JLabel loaderLabel = new JLabel("Loading Readible...", SwingConstants.CENTER);
        loaderLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        loaderLabel.setForeground(Color.WHITE);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(loaderLabel, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(panel);
        setVisible(true);

        // Simulate loading process
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                dispose();
                new RegisterScreen().setVisible(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}