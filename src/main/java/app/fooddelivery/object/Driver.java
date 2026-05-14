package app.fooddelivery.object;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Driver extends User {
    private Status status;
    private final ArrayList<Order> orders;
    private final Queue<Rating> ratings;
    
    protected Driver(String name, UUID id, String password){
        super(name, id, password);
        this.status = Status.FREE;
        orders = new ArrayList<>();
        ratings = new LinkedList<>();
    }
    protected Driver(User u) { 
        super(u.getName(), u.getId(), u.getPasswordHash()); 
        orders = new ArrayList<>();
        ratings = new LinkedList<>();
        }

    protected Driver(String name, UUID id, byte[] phashes) {
        super(name,id,phashes);
        this.status = Status.FREE;
        orders = new ArrayList<>();
        ratings = new LinkedList<>();
    }
    public Driver(String name, String password) {
        this(name, UUID.randomUUID(), password);
    }
    public void addOrder(Order order) {
        orders.add(order);
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public Status getStatus() {
        return status;
    }
    public void goOffline() {
        status = Status.OFFLINE;
    }
    public void goOnline() {
        status = Status.FREE;
    }
    public double averageRate() {
        double avg = 0;
        for (Rating rate : ratings) {
            avg += rate.getScore();
        }
        if (ratings.isEmpty()) {
            return 0;
        }
        return avg / ratings.size();
    }
    public void addRate(Rating rate) {
        if (ratings.size() > 5) {
            ratings.poll();
        }
        ratings.add(rate);
    }
    public Queue<Rating> getRates() {
        return ratings;
    }
    @Override
    public String getRole() {
        return "Driver";
    }
    public static enum Status {
        FREE,
        BUSY,
        OFFLINE
    }
}