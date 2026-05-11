package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.User;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.AdminUI.AdminHome;
import app.fooddelivery.ui.CustomerUI.CustomerHome;
import app.fooddelivery.ui.DriverUI.DriverHome;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;

public class LoginUI {
    public static class Login implements Screen {
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("PLEASE LOGIN")));

            Utils.Input username = new Utils.Input("Username: ", false);
            Utils.Input password = new Utils.Input("Password: ", true);

            List<Utils.Line> lines = new ArrayList<>();
            lines.add(username);
            lines.add(password);
            lines.add(new Utils.Button("Yes", () -> {
                String user = username.getInput();
                String pass = password.getInput();

                User u = restaurant.getUser(user);
                if (u != null && u.checkPassword(pass)) {
                    if (u instanceof Customer) {
                        return new CustomerHome((Customer) u);
                    }
                    if (u instanceof Driver) {
                        return new DriverHome((Driver) u);
                    }
                    if (u instanceof Admin) {
                        return new AdminHome((Admin) u);
                    }
                }
                return new LoginError();
            }));

            lines.add(new Utils.Button("Exit", () -> new LoginExit()));
            return Utils.loop(title, lines, term);
        }
    }

    public static class LoginError implements Screen {
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("PLEASE LOGIN")));

            Utils.Input username = new Utils.Input("Username: ", false);
            Utils.Input password = new Utils.Input("Password: ", true);

            List<Utils.Line> lines = new ArrayList<>();
            lines.add(username);
            lines.add(password);
            lines.add(new Utils.Button("Yes", () -> {
                String user = username.getInput();
                String pass = password.getInput();

                User u = restaurant.getUser(user);
                if (u != null && u.checkPassword(pass)) {
                    if (u instanceof Customer) {
                        return new CustomerHome((Customer) u);
                    }
                    if (u instanceof Driver) {
                        return new DriverHome((Driver) u);
                    }
                    if (u instanceof Admin) {
                        return new AdminHome((Admin) u);
                    }
                }
                return new LoginError();
            }));

            lines.add(new Utils.Button("Exit", () -> new LoginExit()));
            return Utils.loop(title, lines, term);
        }
    }

    public static class LoginExit implements Screen {
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();
            System.exit(0);
            return null;
        }
    }
}