package app.fooddelivery.object;

import java.util.ArrayList;
import java.util.UUID;

public class Driver extends User {
    private Status status;
    private final ArrayList<Order> orders;
    final protected ArrayList<UserRating> ratings;
    protected Driver(String name, UUID id) {
        super(name,id);
        this.status = Status.FREE;
        orders = new ArrayList<>();
        ratings = new ArrayList<>();
    }
    public Driver(String name) {
        this(name,UUID.randomUUID());
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
        for (UserRating rate : ratings) {
            avg += rate.getScore();
        }
        return avg / ratings.size();

    }
    public void addRate(UserRating rate) {
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