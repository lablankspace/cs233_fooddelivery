package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.QuantizedItem;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.CustomerUI.CustomerHome;
import app.fooddelivery.ui.Utils.Amount;
import app.fooddelivery.ui.Utils.Button;
import app.fooddelivery.ui.Utils.Line;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.Text;


public class CustomerUI {
    public static class CustomerHome implements Screen {
        private final Customer customer;

        public CustomerHome(Customer customer) {
            this.customer = customer;
        }

        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();
            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("WELCOME " + customer.getName())));

            List<Line> lines = new ArrayList<>();
            lines.add(new Button("Place Order", () -> new PlaceOrder(customer)));
            lines.add(new Button("View Order History", () -> new ViewOrderHistory(customer)));
            lines.add(new Button("Exit", () -> new LoginUI.Login()));
            return Utils.loop(title, lines, term);
        }
    }

    public static class PlaceOrder implements Screen {
        private final Customer customer;

        public PlaceOrder(Customer customer) {
            this.customer = customer;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Placing New Order for " + customer.getName())));

            Menu menu = restaurant.getMenu();

            List<Line> lines = new ArrayList<>();
            HashMap<Item, Integer> amount = new HashMap<>();

            for (Item item : menu.getItems()) {
                Amount line = new Amount(
                        item.getName() + " --- " + item.getDescription() + " --- $" + item.getPrice());
                lines.add(line);
                amount.put(item, line.quantity());
            }

            lines.add(new Button("Place Order", () -> {
                Order o = new Order(customer);

                for (Map.Entry<Item, Integer> entry : amount.entrySet()) {
                    if (entry.getValue() > 0) {
                        o.addQuantizedItem(new QuantizedItem(
                                entry.getKey(), entry.getValue()));
                    }
                }
                if (o.getItems().isEmpty()) {
                    return new PlaceOrderError(customer);
                }
                restaurant.addOrder(o);
                restaurant.update();
                customer.addOrder(o);
                restaurant.writeOrder(o);
                return new CustomerHome(customer);
            }));
            lines.add(new Button("Exit", () -> {
                return new CustomerHome(customer);
            }));
            return Utils.loop(title, lines, term);
        }
    }
    public static class PlaceOrderError implements Screen {
        private final Customer customer;

        public PlaceOrderError(Customer customer) {
            this.customer = customer;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Placing New Order for " + customer.getName()),
                    new Text("ERROR: No items selected")));

            Menu menu = restaurant.getMenu();

            List<Line> lines = new ArrayList<>();
            HashMap<Item, Integer> amount = new HashMap<>();

            for (Item item : menu.getItems()) {
                Amount line = new Amount(
                        item.getName() + " --- " + item.getDescription() + " --- $" + item.getPrice());
                lines.add(line);
                amount.put(item, line.quantity());
            }

            lines.add(new Button("Place Order", () -> {
                Order o = new Order(customer);

                for (Map.Entry<Item, Integer> entry : amount.entrySet()) {
                    if (entry.getValue() > 0) {
                        o.addQuantizedItem(new QuantizedItem(
                                entry.getKey(), entry.getValue()));
                    }
                }
                if (o.getItems().isEmpty()) {
                    return new PlaceOrderError(customer);
                }
                restaurant.addOrder(o);
                restaurant.update();
                customer.addOrder(o);
                restaurant.writeOrder(o);
                return new CustomerHome(customer);
            }));
            lines.add(new Button("Exit", () -> {
                return new CustomerHome(customer);
            }));
            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewOrderHistory implements Screen {
        private final Customer customer;

        public ViewOrderHistory(Customer customer) {
            this.customer = customer;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text(customer.getName() + "'s Order History")));
            List<Line> lines = new ArrayList<>();

            for (Order order : customer.getOrderHistory()) {
                lines.add(new Button(order.getId().toString(), () -> new ViewOrder(customer, order)));
            }
            lines.add(new Button("Exit", () -> new CustomerHome(customer)));

            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewOrder implements Screen {
        private final Customer customer;
        private final Order order;

        public ViewOrder(Customer customer, Order order) {
            this.customer = customer;
            this.order = order;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Order: " + order.getId().toString()),
                    new Text("Customer: " + customer.getName()),
                    new Text("ID: " + order.getId().toString()),
                    new Text("Status: " + order.getStatus().toString())

            ));
            List<Line> lines = new ArrayList<>();
            if (order.getStatus() == Order.Status.DELIVERED) {
                lines.add(new Button("Rate Driver", () -> new RateOrder(customer, order)));
            }
            lines.add(new Button("Exit", () -> new ViewOrderHistory(customer)));
            return Utils.loop(title, lines, term, 6);
        }
    }

    public static class RateOrder implements Screen {
        private final Customer customer;
        private final Order order;

        public RateOrder(Customer customer, Order order) {
            this.customer = customer;
            this.order = order;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Rate Order " + order.getId().toString()),
                    new Text("Rate 1 to 5")
                ));
            List<Line> lines = new ArrayList<>();
            Amount rate = new Amount("", 1, 5);
            Button enter = new Button("Submit Rating", () -> {
                Rating rating = new Rating(customer, rate.quantity());
                restaurant.addRating(order, rating);
                return new ViewOrder(customer, order);
            });
            lines.add(rate);
            lines.add(enter);
            return Utils.loop(title, lines, term);
        }
    }
}
