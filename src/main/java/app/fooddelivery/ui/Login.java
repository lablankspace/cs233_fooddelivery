package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Term;



public class Login {
        public static void run(Term term, Restaurant restaurant) {
            Utils.exitCondition none = new Utils.exitCondition();
            term.clear();
                        
            List<Utils.Line> title = new ArrayList<>(List.of(
                new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                new Utils.Text("PLEASE LOGIN")      
            ));
                        
            int selection = 0;
                        
            List<Utils.Line> lines = new ArrayList<>(List.of(
                new Utils.Input("Username", false),
                new Utils.Input("Password", true),
                new Utils.Button("Login", () -> {
                    CustomerUI.home(term, null);
                }),
                new Utils.Button("Exit", () -> {
                    System.exit(0);
                })
            ));
            Utils.loop(title, lines, term, 0, 3, none);
    }
}