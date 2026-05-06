package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.Rating;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Term;


public class DriverUI {
    public static void home(Term term, Restaurant restaurant, Driver driver) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();
                        
        List<Utils.Line> title = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text("WELCOME " + driver.getName())      
        ));
        
        List<Utils.Line> lines = new ArrayList<>(List.of(
            new Utils.Button("View Orders", () -> {
                viewActiveOrders(term, restaurant, driver);
                term.clear();
            }),
            new Utils.Button("View Rates", () -> {
                viewRates(term, restaurant, driver);
                term.clear();
            }),
            new Utils.Button("Exit", () -> {
                active.stop();
            })
        ));
            Utils.loop(title, lines, term, active);
    }

    public static void viewActiveOrders(Term term, Restaurant restaurant, Driver driver) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();

        List<Utils.Line> tilte = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(driver.getName() + "'s Active Orders")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Order order : driver.getOrders()) {
            lines.add(new Utils.Button(order.getId().toString() + " " + order.getStatus(), () -> {
                viewOrder(term, restaurant, driver, order);
                term.clear();
            }));
        }
        lines.add(new Utils.Button("Exit", () -> {
            term.clear();
            active.stop();
        }));

        Utils.loop(tilte, lines, term, active);
    }

    public static void viewOrder(Term term, Restaurant restaurant, Driver driver, Order order) {
        Utils.ExitCondition active = new Utils.ExitCondition();
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
                    active.stop();
            }));
        }
        if (order.getStatus() == Order.Status.INPROGRESS) {
            lines.add(new Utils.Button("Update to Delivered", () -> {
                    order.updateStatus(Order.Status.DELIVERED);
                    restaurant.update();
                    active.stop();
            }));
        }
        lines.add(new Utils.Button("Exit", () -> {
            active.stop();
        }));
        Utils.loop(title, lines, term, active, 6);
    }
    public static void viewRates(Term term, Restaurant restaurant, Driver driver) {
        Utils.ExitCondition active = new Utils.ExitCondition();
        term.clear();

        List<Utils.Line> tilte = new ArrayList<>(List.of(
            new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
            new Utils.Text(driver.getName() + "'s Rates")
        ));
        List<Utils.Line> lines = new ArrayList<>();

        for (Rating rate : driver.getRates()) {
            lines.add(new Utils.Text(rate.toString()));
        }
        lines.add(new Utils.Button("Exit", () -> {
            active.stop();
        }));

        Utils.loop(tilte, lines, term, active);
    }

}
