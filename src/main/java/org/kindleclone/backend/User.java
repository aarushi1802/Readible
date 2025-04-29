package main.java.org.kindleclone.backend;

public class User {
    private int id;
    private String username;
    private String name;
    private int age;
    private String gender;
    private String email;

    public User(int id,String username, String name, int age, String gender, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }

    // Getters
    public String getUsername() { return username; }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
}