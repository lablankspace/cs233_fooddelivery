package app.fooddelivery.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.Rating;
import app.fooddelivery.object.State;
import app.fooddelivery.object.User;




public class Restaurant {
    private final State state;

    public Restaurant(State state) {
        this.state = state;
    }
    public void addUser(User u) {
    if (!state.users.containsKey(u.getId()) && !isNameTaken(u.getName(), state.users.values()) && !isIdTaken(u.getId(), state.users.values())) {
        state.users.put(u.getId(), u);
    }
    }
    public boolean containsUser(User u) {
        return state.users.containsValue(u);
    }
    public boolean containsUser(String name) {
        for ( User u : state.users.values()) {
            if (u.getName().equals(name)) return true;
        }
        return false;
    }
    public void removeUser(User u) {
        state.users.remove(u.getId());
    }
    public User getUser(String name) {
        for ( User u : state.users.values()) {
            if (u.getName().equals(name)) return u;
        }
        return null;
    }
    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(state.users.values());
    }
    
    public Admin getAdmin(String name) {
        for ( User u : state.users.values()) {
            if ( u instanceof Admin && u.getName().equals(name)) 
                return (Admin) u;
        }
        return null;
    }
    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        for ( User u : state.users.values()) {
            if ( u instanceof Driver) drivers.add((Driver) u);
        }
        return drivers;
    }
    public Driver getDriver(String name) {
        for ( User u : state.users.values()) {
            if ( u instanceof Driver && u.getName().equals(name)) 
                return (Driver) u;
        }
        return null;
    }
    public Customer getCustomer(String name) {
        for ( User u : state.users.values()) {
            if ( u instanceof Customer && u.getName().equals(name)) 
                return (Customer) u;
        }
        return null;
    }

    public static boolean isNameTaken(String name, Collection<? extends User> users) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isIdTaken(UUID id, Collection<? extends User> users) {
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
        update();
    }
    public Order nextOrder() { return state.orderQueue.poll();}
    public void update() {
        DriverAssignment.assignDriver(this);
    }
    public void addRating(Rating rate) {
        // TODO send to driver assingment or something to store rates
    }
}