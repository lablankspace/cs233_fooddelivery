package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.QuantizedItem;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Term;


public class CustomerUI {
    public Screen home(Term term, Restaurant restaurant, Customer customer) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + customer.getName())      
        ));
                
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("Place Order", () -> {
                placeOrder(term, restaurant, customer);
                term.clear();
            }),
            new Utils.Button("View Order History", () -> {
                viewOrderHistory(term, restaurant, customer);
                term.clear();
            }),
            new Utils.Button("Exit", () -> {
                active.stop();
            })
        ));
            Utils.loop(title, lines, term, active);
    }

    public static void placeOrder(Term term, Restaurant restaurant, Customer customer) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Placing New Order for " + customer.getName())      
        ));

        Menu menu = restaurant.getMenu();
        
        List<Utils.Line> lines = new ArrayList<>();
        List<QuantizedItem> items = new ArrayList<>();
        
        for (Item item : menu.getItems()) {
            Utils.Amount line = new Utils.Amount(item.getName() + " --- " + item.getDescription()+ " --- $" + item.getPrice());
            lines.add(line);
            QuantizedItem qItem = new QuantizedItem(item, line.quantity());
            items.add(qItem);
        }
        // TODO Move qItem out of for loop somehow

        lines.add(new Utils.Button("Place Order", () -> {
                Order o = new Order(customer);
                for ( QuantizedItem item : items) o.addQuantizedItem(item);
                restaurant.addOrder(o);
                restaurant.update();
                customer.addOrder(o);
                // Write all orders to file for transaction history
                active.stop();
            }));
        lines.add(new Utils.Button("Exit", () -> {
            active.stop();
            })
        );
        
        Utils.loop(title, lines, term, active);
    }

    public static void viewOrderHistory(Term term, Restaurant restaurant, Customer customer) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();

        List<Utils.Line> tilte = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(customer.getName() + "'s Order History")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Order order : customer.getOrderHistory()) {
            lines.add(new Utils.Button(order.getId().toString(), () -> {
                viewOrder(term, restaurant, customer, order);
                term.clear();
            }));
        }
        lines.add(new Utils.Button("Exit", () -> {
            active.stop();
        }));

        Utils.loop(tilte, lines, term, active);

    }
    
    public static void viewOrder(Term term, Restaurant restaurant, Customer customer, Order order) {
        Utils.ExitCondition active = new Utils.ExitCondition();
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
                    term.clear();
                }));
            }
        lines.add(new Utils.Button("Exit", () -> {
            active.stop();
        }));
        Utils.loop(title, lines, term, active, 6);
    }

    public static void rateOrder(Term term, Restaurant restaurant, Customer customer, Order order) {
        Utils.ExitCondition active = new Utils.ExitCondition();
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
            active.stop();
        });
        lines.add(rate);
        lines.add(enter);
        Utils.loop(title, lines, term, active);
    }

    
}
