package main.java.org.kindleclone.backend;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final String description;
    private final String coverImage;
    private final String category;

    public Book(int id, String title, String author,
                String description, String coverImage, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverImage = coverImage;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public String getCoverImage() { return coverImage; }
    public String getCategory() { return category; }

    // Alias for cover image file path
    public String getCoverImagePath() {
        return coverImage;
    }
}
