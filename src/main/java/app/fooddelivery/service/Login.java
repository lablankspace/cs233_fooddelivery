package app.fooddelivery.service;

public class Login {
    public static String checkLogin(String user, String pass) {
        if (user.equals(pass)) return "true";
        else return "false";
    }
}