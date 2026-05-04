package app.fooddelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.fooddelivery.object.*;




public class Restaurant {
    private final State state;

    public Restaurant(State state) {
        this.state = state;
    }

    public void addUser(User u) {
        if (!state.users.contains(u)) {
            if (!isNameTaken(u.getName(), state.users) && (!isIdTaken(u.getId(), state.users))) {
                switch (u.getRole()) {
                    case "Admin":
                        state.users.add((Admin) u);
                        break;
                    case "Driver":
                        state.users.add((Driver) u);
                        break;
                    default:
                        state.users.add((Customer) u);
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
    public Customer getCustomer(String name) {
        List<Customer> customers = getCustomers();
        for ( Customer c : customers) {
            if (c.getName().equals(name)) return c;
        }
        return null;
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
    public Menu getMenu() {
        return state.menu;
    }
    public void addOrder(Order o) {
        state.orderQueue.add(o);
    }
    public Order nextOrder() { return state.orderQueue.poll();}
    public void addRating(Rating rate) {
        // TODO send to driver assingment or something to store rates
    }
}