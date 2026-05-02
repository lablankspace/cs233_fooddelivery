package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Customer;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.exitCondition;

public class CustomerUI {
    public static void home(Term term, Customer customer) {
        exitCondition active = new exitCondition();
        term.clear();
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + customer.getName())      
        ));
        
        int selection = 0;
        
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("Place Order", () -> {
                placeOrder(term);
            }),
            new Utils.Button("View Order History", () -> {
                viewOrderHistory();
            }),
            new Utils.Button("Rate Order", () -> {
                rateOrder();
            }),
            new Utils.Button("Exit", () -> {
                active.condition = false;
            })
        ));
            Utils.loop(title, lines, term, 0, 3, active);
    }
    public static void placeOrder(Term term) {
        exitCondition active = new exitCondition();
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Placing New Order for" + " Sage") //customer.getName())      
        ));

        int selection = 0;

        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Amount("Burgers  $12.00"),
            new Utils.Amount("Pizza    $10.00"),
            new Utils.Button("Place Order", () -> {
                // Send Order to Queue or something
            }),
            new Utils.Button("Exit", () -> {
                active.condition = false;
            })
        ));
        
        Utils.loop(title, lines, term, 0, 3, active);
    }
    public static void viewOrderHistory() {
        
    }
    public static void rateOrder() {

    }
}
