package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;


public class DriverUI {
public static class DriverHome implements Screen{
    private Driver driver;

    public DriverHome(Driver driver) {
        this.driver = driver;
    }
    public Screen run(Term term, Restaurant restaurant) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + driver.getName())      
        ));
        
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("View Orders", () -> new ViewActiveOrders(driver)),
            new Utils.Button("View Rates", () -> new ViewRates(driver)),
            new Utils.Button("Exit", () -> new Login())));
            return Utils.loop(title, lines, term);
    }
}
public static class ViewActiveOrders implements Screen {
    private Driver driver;

    public ViewActiveOrders(Driver driver) {
        this.driver = driver;
    }
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> tilte = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(driver.getName() + "'s Active Orders")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Order order : driver.getOrders()) {
            lines.add(new Utils.Button(order.getId().toString() + " " + order.getStatus(), () -> new ViewOrder(driver, order)));
        }
        lines.add(new Utils.Button("Exit", () -> new DriverHome(driver)));

        return Utils.loop(tilte, lines, term);
    }
}
public static class ViewOrder implements Screen{
    private Driver driver;
    private Order order;

    public ViewOrder(Driver driver, Order order) {
        this.driver = driver;
        this.order = order;
    }
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("Order: " + order.getId().toString()),
            new Utils.Text("Customer: " + order.getCustomer().getName()),
            new Utils.Text("ID: " + order.getId().toString()),
            new Utils.Text("Status: " + order.getStatus().toString())
        ));
        List<Utils.Line> lines = new ArrayList<>();
        if (order.getStatus() == Order.Status.ACCEPTED) {
            lines.add(new Utils.Button("Update to In-Progress", () -> {
                    order.updateStatus(Order.Status.INPROGRESS);
                    return new ViewActiveOrders(driver);
            }));
        }
        if (order.getStatus() == Order.Status.INPROGRESS) {
            lines.add(new Utils.Button("Update to Delivered", () -> {
                    order.updateStatus(Order.Status.DELIVERED);
                    restaurant.update();
                    return new ViewActiveOrders(driver);
            }));
        }
        lines.add(new Utils.Button("Exit", () -> {
            return new ViewActiveOrders(driver);
        }));
        return Utils.loop(title, lines, term, 6);
    }
}
public static class ViewRates implements Screen{
    private Driver driver;

    public ViewRates(Driver driver) {
        this.driver = driver;
    }
    public Screen run(Term term, Restaurant restaurant) {
        term.clear();

        List<Utils.Line> tilte = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(driver.getName() + "'s Rates")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Rating rate : driver.getRates()) {
            lines.add(new Utils.Text(rate.toString()));
        }
        lines.add(new Utils.Button("Exit", () -> new DriverHome(driver)));

        return Utils.loop(tilte, lines, term);
    }
}
}