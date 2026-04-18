package app.fooddelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.User;



public class Restaurant {
    private final List<User> users;

    public Restaurant() {
        this.users = new ArrayList<>();
    }

    public void addUser(User u, String password) {
        if (!users.contains(u)) {
            if (!isNameTaken(u.getName(), users) && (!isIdTaken(u.getId(), users))) {
                switch (u.getRole()) {
                    case "Admin":
                      //  users.add(new Admin(u));
                        break;
                    case "Driver":
                        //users.add(new Driver(u));
                        break;
                    default:
                        //users.add(new Customer(u));
                        break;
                }
            }
        }
    }
    public boolean containsUser(User u) {
        return users.contains(u);
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
    public List<User> getUsers() {
        return this.users;
    }
    public List<Admin> getAdmins() {
        List<Admin> admins = new ArrayList<>();
        for ( User u : users) {
            admins.add(new Admin(u));
        }
        return admins;
    }
    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        for ( User u : users) {
         //   drivers.add(new Driver(u));
        }
        return drivers;
    }
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        for ( User u : users) {
            customers.add(new Customer(u));
        }
        return customers;
    }
    public static boolean isNameTaken(String name, List<? extends User> users) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isIdTaken(UUID id, List<? extends User> users) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}