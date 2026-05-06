package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
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

        Utils.Input username = new Utils.Input("Username", false);
        Utils.Input password = new Utils.Input("Password", true);

        List<Utils.Line> lines = new ArrayList<>(List.of(
            username,
            password,
            new Utils.Button("Login", () -> {
                if (restaurant.containsUser(username.getInput())) {
                    if (restaurant.getUser(username.getInput()) instanceof Customer) {
                        if(restaurant.getCustomer(username.getInput()).checkPassword(password.getInput())) {
                            CustomerUI.home(term, restaurant, restaurant.getCustomer(username.getInput()));
                        }
                    }
                    if (restaurant.getUser(username.getInput()) instanceof Driver) {
                        if(restaurant.getDriver(username.getInput()).checkPassword(password.getInput())) {
                            DriverUI.home(term, restaurant, restaurant.getDriver(username.getInput()));
                        }
                    }
                    if (restaurant.getUser(username.getInput()) instanceof Admin) {
                        if(restaurant.getAdmin(username.getInput()).checkPassword(password.getInput())) {
                            AdminUI.home(term, restaurant, restaurant.getAdmin(username.getInput()));
                        }
                    }
                    
                }
                else {
                    title.add(new Utils.Text("Invalid Login: Please try again"));
                }
            }),
            new Utils.Button("Exit", () -> {
                System.exit(0);
            })
        ));
        Utils.loop(title, lines, term, none);
    }
}