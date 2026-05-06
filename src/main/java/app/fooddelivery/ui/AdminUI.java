package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
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
        Utils.exitCondition active = new Utils.exitCondition();
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Menu")      
        ));
        
        List<Utils.Line> lines = new ArrayList<>();
        Menu menu = restaurant.getMenu();
        for (HashMap.Entry<Item, Double> item : menu.getHash().entrySet()) {
            Utils.Button line = new Utils.Button(item.getKey().getName() + " --- " + item.getKey().getDescription()+ " --- $" + item.getValue().toString(), () -> {
                removeItem(term, restaurant, admin, item.getKey());
            });
            lines.add(line);
        }
        lines.add(new Utils.Button("New Item", () -> {
                addItem(term, restaurant, admin);
            })
        );
        lines.add(new Utils.Button("Exit", () -> {
                term.clear();
                active.condition = false;
            })
        );
            Utils.loop(title, lines, term, active);
    }
    public static void viewOrder(Term term, Restaurant restaurant, Admin admin) {
        
    }
    public static void addItem(Term term, Restaurant restaurant, Admin admin) {
        Utils.exitCondition active = new Utils.exitCondition();
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Create new Menu Item")
        ));

        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Input("Item Name: ", false),
            new Utils.Input("Item Description: ", false),
            new Utils.Input("Price", false),
            new Utils.Button("Add Item", () -> {
                // pull info and add item
            }),
            new Utils.Button("Exit", () -> {
                term.clear();
                active.condition = false;
            })
        ));
        Utils.loop(title, lines, term, active);
    }
    public static void removeItem(Term term, Restaurant restaurant, Admin admin, Item item) {
        Utils.exitCondition active = new Utils.exitCondition();
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Are you sure you wish to remove " + item.getName())
        ));
        
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("Yes", () -> {
                restaurant.getMenu().removeItem(item);
                active.condition = false;
            }),
            new Utils.Button("No", () -> {
                term.clear();
                active.condition = false;
            })
        ));
        Utils.loop(title, lines, term, active);
    }
}