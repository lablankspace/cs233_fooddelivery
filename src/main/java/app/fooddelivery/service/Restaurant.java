package app.fooddelivery.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Deserialization;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.QuantizedItem;
import app.fooddelivery.object.Rating;
import app.fooddelivery.object.Serialization;
import app.fooddelivery.object.State;
import app.fooddelivery.object.User;

public class Restaurant {
    private final State state;

    public Restaurant(State state) {
        this.state = state;
    }

    public void addUser(User u) {
        if (!state.users.containsKey(u.getId()) && !isNameTaken(u.getName(), state.users.values())
                && !isIdTaken(u.getId(), state.users.values())) {
            state.users.put(u.getId(), u);
        }
    }

    public boolean containsUser(User u) {
        return state.users.containsValue(u);
    }

    public boolean containsUser(String name) {
        for (User u : state.users.values()) {
            if (u.getName().equals(name))
                return true;
        }
        return false;
    }

    public void removeUser(User u) {
        state.users.remove(u.getId());
    }

    public User getUser(String name) {
        for (User u : state.users.values()) {
            if (u.getName().equals(name))
                return u;
        }
        return null;
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(state.users.values());
    }

    public Admin getAdmin(String name) {
        for (User u : state.users.values()) {
            if (u instanceof Admin && u.getName().equals(name))
                return (Admin) u;
        }
        return null;
    }

    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        for (User u : state.users.values()) {
            if (u instanceof Driver)
                drivers.add((Driver) u);
        }
        return drivers;
    }

    public Driver getDriver(String name) {
        for (User u : state.users.values()) {
            if (u instanceof Driver && u.getName().equals(name))
                return (Driver) u;
        }
        return null;
    }

    public Customer getCustomer(String name) {
        for (User u : state.users.values()) {
            if (u instanceof Customer && u.getName().equals(name))
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

    public Order nextOrder() {
        return state.orderQueue.poll();
    }

    public void update() {
        assignDriver();
    }
    
    public void writeOrder(Order o) {
        File file = new File("order.txt");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Order.class, new Serialization.OrderSerializer())
                .registerTypeAdapter(Order.class, new Deserialization.OrderDeserializer())
                .registerTypeAdapter(QuantizedItem.class, new Serialization.QuantizedItemSerializer())
                .registerTypeAdapter(QuantizedItem.class, new Deserialization.QuantizedItemDeserializer())
                .create();
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(o, writer);
        } catch (IOException e) {
            System.err.println("Failed to save!");
        }
    }

    public Collection<Order> getOrderQueue() {
        return Collections.unmodifiableCollection(state.orderQueue);
    }

    public void addRating(Order order, Rating rate) {
        for (Driver d : getDrivers()) {
            for (Order o : d.getOrders()) {
                if (order.equals(o)) {
                    d.addRate(rate);
                }
            }
        }
    }

    public void assignDriver() {
        Order order = nextOrder();
        if (order == null)
            return;
        Driver driver = findDriver();
        order.updateStatus(Order.Status.ACCEPTED);
        driver.addOrder(order);
    }

    public Driver findDriver() {
        Driver best = null;
        for (Driver driver : getDrivers()) {
            if (driver.getStatus() == Driver.Status.FREE) {
                if (best == null || driver.averageRate() > best.averageRate()) {
                    best = driver;
                }
            }
        }
        return best;
    }

    public int checkLogin(String user, String pass) {
        if (containsUser(user)) {
            User u = getUser(user);
            if (u.checkPassword(pass)) {
                if (u.getRole().equals("Admin"))
                    return 0;
                if (u.getRole().equals("Driver"))
                    return 1;
                else
                    return 2;
            } else
                return 3;
        } else
            return 3;
    }
}