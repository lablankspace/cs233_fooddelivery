package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Term;

public class AdminUI {
    public static void home(Term term, Restaurant restaurant, Admin admin) {
        Utils.exitCondition active = new Utils.exitCondition();
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + admin.getName())      
        ));
        
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("View Menu", () -> {
                viewMenu(term, restaurant, admin);
            }),
            new Utils.Button("View Orders", () -> {
                viewOrder(term, restaurant, admin);
            }),
            new Utils.Button("Exit", () -> {
                term.clear();
                active.condition = false;
            })
        ));
            Utils.loop(title, lines, term, active);
    }
    public static void viewMenu(Term term, Restaurant restaurant, Admin admin) {

    }
    public static void viewOrder(Term term, Restaurant restaurant, Admin admin) {
        
    }
}