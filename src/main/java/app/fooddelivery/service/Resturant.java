package app.fooddelivery.service;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.User;



public class Resturant {
    private final List<User> users;

    public Resturant() {
        this.users = new ArrayList<>();
    }

    public void addUser(User u, String password) {
        if (!users.contains(u)) {
            if (!isNameTaken(u.getName(), users)) {
                if (u.getRole().equals("Admin")) {
                    users.add(new Admin(u));
                }
                else if (u.getRole().equals("Driver")) {
                    users.add(new Driver(u));
                }
                else {
                    users.add(new Customer(u));
                }
            }
        }
    }
    public boolean containsUser(User u) {
        if (users.contains(u)) return true;
        else return false;
    }
    public boolean containsUser(String name) {
        for ( User u : users) {
            if (u.getName().equals(name)) return true;
        }
        return false;
    }
    public void removeUser(User u) {
        if (users.contains(u)) users.remove(u);
    }
    public User getUser(String name) {
        for ( User u : users) {
            if (u.getName().equals(name)) return u;
        }
        return null;
    }
    public static boolean isNameTaken(String name, List<? extends User> users) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}