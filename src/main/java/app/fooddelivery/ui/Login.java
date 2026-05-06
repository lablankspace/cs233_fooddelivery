package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.User;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.Screen;


public class Login implements Screen{
    @Override
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("PLEASE LOGIN")      
        ));

        Utils.Input username = new Utils.Input("Username: ", false);
        Utils.Input password = new Utils.Input("Password: ", true);

        List<Utils.Line> lines = new ArrayList<>();
        lines.add(username);
        lines.add(password);
        Utils.Text error = new Utils.Text("Invalid Login: Please try again");
        lines.add(new Utils.Button("Login", (Utils.Action) -> {
                String user = username.getInput();
                String pass = password.getInput();

                User u = restaurant.getUser(user);
                
                if (u != null && u.checkPassword(pass)) {

                    if (u instanceof Customer) {
                        Customer c = (Customer) u;
                        return new (c);
                    }

                    if (u instanceof Driver) {
                        Driver d = (Driver) u;
                        return new DriverHomeScreen(d);
                    }

                    if (u instanceof Admin) {
                        Admin a = (Admin) u;
                        return new AdminHomeScreen(a);
                    }
                }
                title.add(error);
                return null;
            }));
        lines.add(new Utils.Button("Exit", () -> null));
        Utils.loop(title, lines, term);
    }
}