package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.QuantizedItem;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.exitCondition;


public class CustomerUI {
    public static void home(Term term, Restaurant restaurant, Customer customer) {
        exitCondition active = new exitCondition();
        term.clear();
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + customer.getName())      
        ));
                
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("Place Order", () -> {
                placeOrder(term, restaurant, customer);
            }),
            new Utils.Button("View Order History", () -> {
                viewOrderHistory(term, restaurant, customer);
            }),
            new Utils.Button("Exit", () -> {
                term.clear();
                active.condition = false;
            })
        ));
            Utils.loop(title, lines, term, active);
    }

    public static void placeOrder(Term term, Restaurant restaurant, Customer customer) {
        exitCondition active = new exitCondition();
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Placing New Order for " + customer.getName())      
        ));

        Menu menu = restaurant.getMenu();
        
        List<Utils.Line> lines = new ArrayList<>();
        List<QuantizedItem> items = new ArrayList<>();
        
        for (HashMap.Entry<Item, Double> item : menu.getHash().entrySet()) {
            Utils.Amount line = new Utils.Amount(item.getKey().getName() + " --- " + item.getKey().getDescription()+ " --- $" + item.getValue().toString());
            lines.add(line);
            QuantizedItem qItem = new QuantizedItem(item.getKey(), line.quantity());
            items.add(qItem);
        }
        

        lines.add(new Utils.Button("Place Order", () -> {
                Order o = new Order(customer);
                for ( QuantizedItem item : items) o.addQuantizedItem(item);
                restaurant.addOrder(o);
                customer.addOrder(o);
                active.condition = false;
            }));
        lines.add(new Utils.Button("Exit", () -> {
            term.clear();
            active.condition = false;
            })
        );
        
        Utils.loop(title, lines, term, active);
    }

    public static void viewOrderHistory(Term term, Restaurant restaurant, Customer customer) {
        exitCondition active = new exitCondition();
        term.clear();

        List<Utils.Line> tilte = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(customer.getName() + "'s Order History")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Order order : customer.getOrderHistory()) {
            lines.add(new Utils.Button(order.getId().toString(), () -> {
                viewOrder(term, restaurant, customer, order);
            }));
        }
        lines.add(new Utils.Button("Exit", () -> {
            term.clear();
            active.condition = false;
        }));

        Utils.loop(tilte, lines, term, active);

    }
    
    public static void viewOrder(Term term, Restaurant restaurant, Customer customer, Order order) {
        exitCondition active = new exitCondition();
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
            lines.add(new Utils.Button("Rate Driver", () -> {
                    rateOrder(term, restaurant, customer, order);
                }));
            }
        lines.add(new Utils.Button("Exit", () -> {
            term.clear();
            active.condition = false;
        }));
        Utils.loop(title, lines, term, active, 6);
    }

    public static void rateOrder(Term term, Restaurant restaurant, Customer customer, Order order) {
        exitCondition active = new exitCondition();
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Rate Order " + order.getId().toString())
        ));
        List<Utils.Line> lines = new ArrayList<>();
        Utils.Amount rate = new Utils.Amount("1 to 5", 1, 5);
        Utils.Button enter = new Utils.Button("Submit Rating", () -> {
            Rating rating = new Rating(customer, rate.quantity());
            restaurant.addRating(rating);
            term.clear();
            active.condition = false;
        });
        lines.add(rate);
        lines.add(enter);
        Utils.loop(title, lines, term, active);
    }

    
}
