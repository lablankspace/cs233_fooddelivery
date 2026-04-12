package app.fooddelivery.object;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Order> orderHistory;
    public Customer(String name, int id) {
        super(name, id);
        orderHistory = new ArrayList<>();
    }
    public Customer(User u) {
        super(u.getName(), u.getId());
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
        UserRating rate = new UserRating(this,driver,rating);
        driver.addRate(rate);
    }
    public String viewOrderStatus(Order order) {
        return "TODO";
    }
    public String viewOrderHistory() {
        return "TODO";
    }
}