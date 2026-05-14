package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Button;
import app.fooddelivery.ui.Utils.Line;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.Text;



public class DriverUI {
    public static class DriverHome implements Screen {
        private final Driver driver;

        public DriverHome(Driver driver) {
            this.driver = driver;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("WELCOME " + driver.getName())));

            List<Line> lines = new ArrayList<>(List.of(
                    new Button("View Orders", () -> new ViewOrders(driver)),
                    new Button("View Rates", () -> new ViewRates(driver)),
                    new Button("Exit", () -> new LoginUI.Login())));
            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewOrders implements Screen {
        private final Driver driver;

        public ViewOrders(Driver driver) {
            this.driver = driver;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text(driver.getName() + "'s Orders")));
            List<Line> lines = new ArrayList<>();

            for (Order order : driver.getOrders()) {
                lines.add(new Button(order.getId().toString() + " " + order.getStatus(),
                        () -> new ViewOrder(driver, order)));
            }
            lines.add(new Button("Exit", () -> new DriverHome(driver)));

            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewOrder implements Screen {
        private final Driver driver;
        private final Order order;

        public ViewOrder(Driver driver, Order order) {
            this.driver = driver;
            this.order = order;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Order: " + order.getId().toString()),
                    new Text("Customer: " + order.getCustomer().getName()),
                    new Text("Status: " + order.getStatus().toString())));
            List<Line> lines = new ArrayList<>();
            if (order.getStatus() == Order.Status.ACCEPTED) {
                lines.add(new Button("Update to In-Progress", () -> {
                    order.updateStatus(Order.Status.INPROGRESS);
                    return new ViewOrders(driver);
                }));
            }
            if (order.getStatus() == Order.Status.INPROGRESS) {
                lines.add(new Button("Update to Delivered", () -> {
                    order.updateStatus(Order.Status.DELIVERED);
                    restaurant.update();
                    return new ViewOrders(driver);
                }));
            }
            lines.add(new Button("Exit", () -> {
                return new ViewOrders(driver);
            }));
            return Utils.loop(title, lines, term, 6);
        }
    }

    public static class ViewRates implements Screen {
        private final Driver driver;

        public ViewRates(Driver driver) {
            this.driver = driver;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text(driver.getName() + "'s Rates")));
            List<Line> lines = new ArrayList<>();

            for (Rating rate : driver.getRates()) {
                lines.add(new Text(rate.toString()));
            }
            lines.add(new Button("Exit", () -> new DriverHome(driver)));

            return Utils.loop(title, lines, term);
        }
    }
}