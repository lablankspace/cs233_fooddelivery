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


            Utils.Input username = new Utils.Input("Username", false);
            Utils.Input password = new Utils.Input("Password", true);

            List<Utils.Line> lines = new ArrayList<>(List.of(
                username,
                password,
                new Utils.Button("Login", () -> {
                    if (restaurant.getCustomer(username.getInput()).checkPassword(password.getInput())) {
                    CustomerUI.home(term, restaurant.getCustomer(username.getInput()));
                    }
                    else {
                        title.add(new Utils.Text("Invalid Login: Please try again"));
                    }
                }),
                new Utils.Button("Exit", () -> {
                    System.exit(0);
                })
                ));

            Utils.loop(title, lines, term, 0, 3, none);
    }
}