package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
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
        
        int selection = 0;
        
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("Place Order", () -> {
                placeOrder(term, restaurant, customer);
            }),
            new Utils.Button("View Order History", () -> {
                viewOrderHistory(term, restaurant, customer);
            }),
            new Utils.Button("Rate Order", () -> {
                rateOrder();
            }),
            new Utils.Button("Exit", () -> {
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

        int selection = 0;

        Menu menu = restaurant.getMenu();
        
        List<Utils.Line> lines = new ArrayList<>();
        
        for (HashMap.Entry<Item, Double> item : menu.getHash().entrySet()) {
            String name = item.getKey().getName();
            String price = item.getValue().toString();
            lines.add(new Utils.Amount(item.getKey().getName() + " --- " + item.getKey().getDescription()+ " --- $" + item.getValue().toString()));
        }
        

        lines.add(new Utils.Button("Place Order", () -> {
                // Send Order to Queue or something
            }));
        lines.add(new Utils.Button("Exit", () -> {
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

        int selection = 0;

        List<Utils.Line> lines = new ArrayList<>();

        for (Order order : customer.getOrderHistory()) {
            lines.add(new Utils.Button(order.getId().toString(), () -> {
                //do something
            }));
        }
        lines.add(new Utils.Button("Exit", () -> {
            active.condition = false;
        }));

        Utils.loop(tilte, lines, term, active);

    }

    public static void rateOrder() {

    }
}
