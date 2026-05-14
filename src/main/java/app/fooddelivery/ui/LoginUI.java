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
import app.fooddelivery.ui.Utils.Button;
import app.fooddelivery.ui.Utils.Input;
import app.fooddelivery.ui.Utils.Line;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.Text;


public class LoginUI {
    public static class Login implements Screen {
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("PLEASE LOGIN")));

            Input username = new Input("Username: ", false);
            Input password = new Input("Password: ", true);

            List<Line> lines = new ArrayList<>();
            lines.add(username);
            lines.add(password);
            lines.add(new Button("Login", () -> {
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

            lines.add(new Button("Exit", () -> new LoginExit()));
            return Utils.loop(title, lines, term);
        }
    }

    public static class LoginError implements Screen {
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("PLEASE LOGIN"),
                    new Text("INVALID USERNAME OR PASSWORD")
                ));

            Input username = new Input("Username: ", false);
            Input password = new Input("Password: ", true);

            List<Line> lines = new ArrayList<>();
            lines.add(username);
            lines.add(password);
            lines.add(new Button("Login", () -> {
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

            lines.add(new Button("Exit", () -> new LoginExit()));
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