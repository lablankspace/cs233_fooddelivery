package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.QuantizedItem;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;


public class CustomerUI {
public static class CustomerHome implements Screen { 
    private final Customer customer;
        
    public CustomerHome(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + customer.getName())      
        ));
                
        List<Utils.Line> lines = new ArrayList<>();
            lines.add(new Utils.Button("Place Order", () -> new PlaceOrder(customer)));
            lines.add(new Utils.Button("View Order History", () -> new ViewOrderHistory(customer)));
            lines.add(new Utils.Button("Exit", () -> new Login()));
        return Utils.loop(title, lines, term);
    }
}

public static class PlaceOrder implements Screen {
    private final Customer customer;

    public PlaceOrder(Customer customer) {
        this.customer = customer;
    }

    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Placing New Order for " + customer.getName())      
        ));

        Menu menu = restaurant.getMenu();
        
        List<Utils.Line> lines = new ArrayList<>();
        HashMap<Item, Integer> amount = new HashMap<>();
        
        for (Item item : menu.getItems()) {
            Utils.Amount line = new Utils.Amount(item.getName() + " --- " + item.getDescription()+ " --- $" + item.getPrice());
            lines.add(line);
            amount.put(item, line.quantity());
        }

        lines.add(new Utils.Button("Place Order", () -> {
                Order o = new Order(customer);
                
                for (Map.Entry<Item, Integer> entry : amount.entrySet()){
                    if (entry.getValue() > 0) {
                        o.addQuantizedItem(new QuantizedItem(
                            entry.getKey(), entry.getValue()
                        ));
                    }
                }
                restaurant.addOrder(o);
                restaurant.update();
                customer.addOrder(o);
                // Write all orders to file for transaction history
                return new CustomerHome(customer);
            }));
        lines.add(new Utils.Button("Exit", () -> {
                return new CustomerHome(customer);
            }));
        return Utils.loop(title, lines, term);
    }
}
public static class ViewOrderHistory implements Screen {
    private final Customer customer;
    
    public ViewOrderHistory(Customer customer) {
        this.customer = customer;
    }
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(customer.getName() + "'s Order History")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Order order : customer.getOrderHistory()) {
            lines.add(new Utils.Button(order.getId().toString(), () -> new ViewOrder(customer, order)));
        }
        lines.add(new Utils.Button("Exit", () -> new CustomerHome(customer)));

        return Utils.loop(title, lines, term);
    }
}
public static class ViewOrder implements Screen {
    private final Customer customer;
    private final Order order;

    public ViewOrder(Customer customer, Order order) {
        this.customer = customer;
        this.order = order;
    }
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Order: " + order.getId().toString()),
            new Utils.Text("Customer: " + customer.getName()),
            new Utils.Text("ID: " + order.getId().toString()),
            new Utils.Text("Status: " + order.getStatus().toString())

        ));
        List<Utils.Line> lines = new ArrayList<>();
        if (order.getStatus() == Order.Status.DELIVERED) {
            lines.add(new Utils.Button("Rate Driver", () -> new RateOrder(customer, order)));
            }
        lines.add(new Utils.Button("Exit", () -> new ViewOrderHistory(customer)));
        return Utils.loop(title, lines, term, 6);
    }
}
public static class RateOrder implements Screen{
    private final Customer customer;
    private final Order order;
    
    public RateOrder(Customer customer, Order order) {
        this.customer = customer;
        this.order = order;
    }

    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Rate Order " + order.getId().toString())
        ));
        List<Utils.Line> lines = new ArrayList<>();
        Utils.Amount rate = new Utils.Amount("1 to 5", 1, 5);
        Utils.Button enter = new Utils.Button("Submit Rating", () -> {
            Rating rating = new Rating(customer, rate.quantity());
            restaurant.addRating(order, rating);
            return new ViewOrder(customer, order);
        });
        lines.add(rate);
        lines.add(enter);
        return Utils.loop(title, lines, term);
    }
}    
}
