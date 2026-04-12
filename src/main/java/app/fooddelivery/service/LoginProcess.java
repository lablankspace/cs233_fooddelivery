package app.fooddelivery.service;

import app.fooddelivery.object.User;

public class LoginProcess {
    public static int checkLogin(Resturant resturant, String user, String pass) {
        if (resturant.containsUser(user)) {
            User u = resturant.getUser(user);
            if (pass.equals(user)) {
                if (u.getRole().equals("Admin")) return 0;
                if (u.getRole().equals("Driver")) return 1;
                else return 2;
            }
            else return 3;
        }
        else return 3;
    }
    public static User getUser(Resturant resturant, String name) {
        return (resturant.getUser(name));
    }
}