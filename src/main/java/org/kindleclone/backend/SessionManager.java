package main.java.org.kindleclone.backend;

public class SessionManager {
    private static boolean isLoggedIn = false;

    public static boolean isUserLoggedIn() {
        return isLoggedIn;
    }

    public static void login() {
        isLoggedIn = true;
    }

    public static void logout() {
        isLoggedIn = false;
    }
}
