package main.java.org.kindleclone.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookPanel extends JPanel {
    private String title;
    private String author;
    private String description;
    private String coverPath;
    private MainDashboard dashboard;

    public BookPanel(MainDashboard dashboard, String title, String author, String description, String coverPath) {
        this.dashboard = dashboard;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverPath = coverPath;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 220));
        setBackground(new Color(55, 55, 55));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load Book Cover
        ImageIcon bookIcon;
        try {
            bookIcon = new ImageIcon(new ImageIcon(coverPath).getImage().getScaledInstance(140, 180, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            bookIcon = new ImageIcon(new ImageIcon("assets/default_cover.jpg").getImage().getScaledInstance(140, 180, Image.SCALE_SMOOTH));
        }

        JLabel bookCover = new JLabel(bookIcon);
        bookCover.setHorizontalAlignment(SwingConstants.CENTER);

        // Book Title
        JLabel bookTitle = new JLabel(title, SwingConstants.CENTER);
        bookTitle.setForeground(Color.WHITE);
        bookTitle.setFont(new Font("Arial", Font.BOLD, 14));

        add(bookCover, BorderLayout.CENTER);
        add(bookTitle, BorderLayout.SOUTH);

        // **Hover Effect**
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(100, 100, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(55, 55, 55));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                dashboard.showBookDetails(title, author, description, coverPath);
            }
        });
    }
}
