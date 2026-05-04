package app.fooddelivery.object;

import java.util.ArrayList;
import java.util.UUID;

public class Driver extends User {
    private Status status;
    private final ArrayList<Order> orders;
    final protected ArrayList<Rating> ratings;
    protected Driver(String name, UUID id) {
        super(name,id.toString());
        this.status = Status.FREE;
        orders = new ArrayList<>();
        ratings = new ArrayList<>();
    }
    public Driver(String name) {
        this(name,UUID.randomUUID());
    }
    public Driver(User u) { 
        super(u.getName(), u.getId(), u.getPasswordHash()); 
        orders = new ArrayList<>();
        ratings = new ArrayList<>();
        }

    protected Driver(String name, UUID id, byte[] phashes) {
        super(name,id,phashes);
        this.status = Status.FREE;
        orders = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
    public String getOrders() {
        return orders.toString();
    }
    public void startOrder(Order order) {
        order.updateStatus(Order.Status.INPROGRESS);
        status = Status.BUSY;
    }
    public void deliverOrder(Order order) {
        order.updateStatus(Order.Status.DELIVERED);
        status = Status.FREE;
    }
    public Status getStatus() {
        return status;
    }
    public void goOffline() {
        status = Status.OFFLINE;
    }
    public double averageRate() {
        double avg = 0;
        for (Rating rate : ratings) {
            avg += rate.getScore();
        }
        return avg / ratings.size();

    }
    public void addRate(Rating rate) {
        ratings.add(rate);
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