package main.java.org.kindleclone.frontend;

import main.java.org.kindleclone.backend.Book;
import main.java.org.kindleclone.backend.BookService;
import main.java.org.kindleclone.backend.User;
import main.java.org.kindleclone.frontend.components.NavBar;
import main.java.org.kindleclone.frontend.utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainDashboard extends JFrame {
    private final NavBar navBar;
    private final JPanel mainContent;
    private final JScrollPane scrollPane;
    private final BookService bookService;
    private final Map<String, JPanel> categoryPanels = new LinkedHashMap<>();
    private final User currentUser;

    public MainDashboard(User user) {
        this.currentUser = user;
        setTitle("Readible - " + user.getUsername());
        setSize(1200, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        bookService = new BookService();
        navBar = new NavBar(this);

        mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(mainContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(navBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadBooks();
        updateTheme();
        setVisible(true);
    }

    private void loadBooks() {
        mainContent.removeAll();
        categoryPanels.clear();
        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            mainContent.add(new JLabel("No books available", SwingConstants.CENTER));
        } else {
            Map<String, List<Book>> categorizedBooks = new LinkedHashMap<>();

            books.stream()
                    .map(book -> book.getCategory() != null ? book.getCategory() : "General")
                    .distinct()
                    .forEach(category -> categorizedBooks.put(category, new ArrayList<>()));

            for (Book book : books) {
                String category = book.getCategory() != null ? book.getCategory() : "General";
                categorizedBooks.get(category).add(book);
            }

            categorizedBooks.forEach((category, bookList) -> {
                if (!bookList.isEmpty()) {
                    createCategoryPanel(category, bookList);
                }
            });
        }

        mainContent.revalidate();
        mainContent.repaint();
    }

    public void searchBooks(String query) {
        mainContent.removeAll();
        categoryPanels.clear();

        if (query == null || query.trim().isEmpty()) {
            loadBooks();
            return;
        }

        List<Book> searchResults = bookService.searchBooks(query.trim());
        if (searchResults.isEmpty()) {
            mainContent.add(new JLabel("No books found matching: " + query, SwingConstants.CENTER));
        } else {
            Map<String, List<Book>> resultsByCategory = new LinkedHashMap<>();
            resultsByCategory.put("Search Results", searchResults);

            resultsByCategory.forEach(this::createCategoryPanel);
        }

        mainContent.revalidate();
        mainContent.repaint();
    }

    private void createCategoryPanel(String category, List<Book> books) {
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 0));
        mainContent.add(categoryLabel);

        JPanel bookRowPanel = new JPanel();
        bookRowPanel.setLayout(new BoxLayout(bookRowPanel, BoxLayout.X_AXIS));
        bookRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (Book book : books) {
            JPanel bookCard = new JPanel();
            bookCard.setLayout(new BoxLayout(bookCard, BoxLayout.Y_AXIS));

            BookPanel bookPanel = new BookPanel(this, book);

            bookCard.add(bookPanel);
            bookCard.add(Box.createVerticalStrut(5));

            bookRowPanel.add(Box.createHorizontalStrut(10));
            bookRowPanel.add(bookCard);
        }

        JScrollPane scrollPane = new JScrollPane(bookRowPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(1200, 250));

        mainContent.add(scrollPane);
        categoryPanels.put(category, bookRowPanel);
    }

    public void showBookDetails(Book book) {
        BookDetailsScreen detailsScreen = new BookDetailsScreen(this, book);
        getContentPane().removeAll();
        add(navBar, BorderLayout.NORTH);
        add(detailsScreen, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void returnToMainDashboard() {
        getContentPane().removeAll();
        add(navBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void addToFavorites(String title) {
        System.out.println("Added to favorites: " + title);
    }

    public void addToLibrary(String title) {
        System.out.println("Added to library: " + title);
    }

    public void showMainView() {
        loadBooks();
    }

    public void returnToLogin() {
        this.dispose();
        new LoginScreen().setVisible(true);
    }

    public void updateAllThemes() {
        updateTheme();
    }

    public void showLibraryView() {
        mainContent.removeAll();
        List<Book> libraryBooks = bookService.getLibraryBooks(currentUser);
        displayBooks(libraryBooks, "Your Library");
        mainContent.revalidate();
        mainContent.repaint();
    }

    public void showFavoritesView() {
        mainContent.removeAll();
        List<Book> favoriteBooks = bookService.getFavoriteBooks(currentUser);
        displayBooks(favoriteBooks, "Your Favorites");
        mainContent.revalidate();
        mainContent.repaint();
    }

    private void displayBooks(List<Book> books, String categoryName) {
        if (books.isEmpty()) {
            JLabel emptyLabel = new JLabel("No books found matching your search", SwingConstants.CENTER);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainContent.add(emptyLabel);
        } else {
            JLabel categoryLabel = new JLabel(categoryName);
            categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            categoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 0));
            mainContent.add(categoryLabel);

            JPanel bookRowPanel = new JPanel();
            bookRowPanel.setLayout(new BoxLayout(bookRowPanel, BoxLayout.X_AXIS));
            bookRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            bookRowPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            for (Book book : books) {
                bookRowPanel.add(Box.createHorizontalStrut(10));
                bookRowPanel.add(new BookPanel(this, book));
            }

            JScrollPane scrollPane = new JScrollPane(bookRowPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setPreferredSize(new Dimension(1200, 250));

            mainContent.add(scrollPane);
        }

        mainContent.revalidate();
        mainContent.repaint();
    }

    public void addToLibrary(int bookId) {
        if (bookService.addToLibrary(bookId)) {
            JOptionPane.showMessageDialog(this, "Added to your library!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add to library",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addToFavorites(int bookId) {
        if (bookService.addToFavorites(bookId)) {
            JOptionPane.showMessageDialog(this, "Added to your favorites!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add to favorites",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTheme() {
        boolean isDark = ThemeManager.isDarkMode();
        Color bgColor = isDark ? new Color(30, 30, 30) : new Color(240, 240, 240);
        Color textColor = isDark ? Color.WHITE : Color.BLACK;
        Color contentBg = isDark ? new Color(45, 45, 45) : new Color(230, 230, 230);

        mainContent.setBackground(bgColor);
        mainContent.setOpaque(true);

        scrollPane.getViewport().getView().setBackground(bgColor);
        scrollPane.setBackground(bgColor);

        for (Component comp : mainContent.getComponents()) {
            updateComponentTheme(comp, isDark, textColor, contentBg);
        }

        navBar.updateTheme();

        revalidate();
        repaint();
    }

    private void updateComponentTheme(Component comp, boolean isDark, Color textColor, Color contentBg) {
        if (comp instanceof JLabel) {
            comp.setForeground(textColor);
        } else if (comp instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) comp;
            scrollPane.getViewport().getView().setBackground(contentBg);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
        } else if (comp instanceof BookPanel) {
            ((BookPanel) comp).updateTheme();
        } else if (comp instanceof BookDetailsScreen) {
            ((BookDetailsScreen) comp).updateTheme();
        } else if (comp instanceof JPanel) {
            JPanel panel = (JPanel) comp;
            panel.setBackground(contentBg);
            panel.setOpaque(true);
            for (Component child : panel.getComponents()) {
                updateComponentTheme(child, isDark, textColor, contentBg);
            }
        } else if (comp instanceof JButton) {
            JButton button = (JButton) comp;
            button.setBackground(ThemeManager.getButtonColor());
            button.setForeground(ThemeManager.getButtonTextColor());
            button.setOpaque(true);
            button.setBorderPainted(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Loader());
    }
}
