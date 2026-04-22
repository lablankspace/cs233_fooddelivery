package app.fooddelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Set;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.User;
import app.fooddelivery.object.State;



public class Restaurant {
    private final State state;

    public Restaurant(State state) {
        this.state = state;
    }

    public void addUser(User u, String password) {
        if (!state.users.contains(u)) {
            if (!isNameTaken(u.getName(), state.users) && (!isIdTaken(u.getId(), state.users))) {
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
        return state.users.contains(u);
    }
    public boolean containsUser(String name) {
        for ( User u : state.users) {
            if (u.getName().equals(name)) return true;
        }
        return false;
    }
    public void removeUser(User u) {
        if (state.users.contains(u)) state.users.remove(u);
    }
    public User getUser(String name) {
        for ( User u : state.users) {
            if (u.getName().equals(name)) return u;
        }
        return null;
    }
    public Set<User> getUsers() {
        return state.users;
    }
    public List<Admin> getAdmins() {
        List<Admin> admins = new ArrayList<>();
        for ( User u : state.users) {
            admins.add(new Admin(u));
        }
        return admins;
    }
    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        for ( User u : state.users) {
         //   drivers.add(new Driver(u));
        }
        return drivers;
    }
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        for ( User u : state.users) {
            customers.add(new Customer(u));
        }
        return customers;
    }
    public static boolean isNameTaken(String name, Set<? extends User> users) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isIdTaken(UUID id, Set<? extends User> users) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}