package main.java.org.kindleclone.backend;

public class AuthService {
    private static final UserDAO userDAO = new UserDAO();

    public static boolean registerUser(String username, String password, String email) {
        return userDAO.registerUser(username, password, email);
    }

    public static boolean loginUser(String username, String password) {
        return userDAO.loginUser(username, password);
    }
}
