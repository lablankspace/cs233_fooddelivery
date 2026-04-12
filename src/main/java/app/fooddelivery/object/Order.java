package app.fooddelivery.object;

import java.util.ArrayList;

public class Order {
    private Customer customer;
    private ArrayList<Item> items;
    private Status status;
    Order(Customer customer) {
        this.customer = customer;
        items = new ArrayList<>();
        this.status = Status.PLACED;
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public Customer getCustomer() {
        return customer;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public Status getStatus() {
        return status;
    }
    public void updateStatus(Status status) {
        this.status = status;
    }
    public enum Status {
        PLACED,
        ACCEPTED,
        INPROGRESS,
        DELIVERED
    }
}