package app.fooddelivery.object;

import java.util.ArrayList;
import java.util.UUID;

public class Customer extends User {
    private final ArrayList<Order> orderHistory;
    public Customer(String name, UUID id, String password) {
        super(name, id, password);
        this.orderHistory = new ArrayList<>();
    }

    protected Customer(String name, UUID id, byte[] passwordHash) {
        super(name, id, passwordHash);
        this.orderHistory = new ArrayList<>();
    }

    public Customer(String name, String password) {
        this(name, UUID.randomUUID(), password);
    }
    public Customer(User u) {
        super(u.getName(), u.getId(), u.getPasswordHash());
        orderHistory = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    public Order createOrder(){
        return new Order(this);
    }
    public void rateDriver(Driver driver, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        UserRating rate = new UserRating(this,rating);
        driver.addRate(rate);
    }
    public String viewOrderStatus(Order order) {
        return "TODO";
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    public void addOrder(Order order) {
        orderHistory.add(order);
    }
}