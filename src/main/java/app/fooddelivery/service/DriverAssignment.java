package app.fooddelivery.service;

import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Order;
import java.util.HashSet;

public class DriverAssignment {
    public static Driver findDriver(Restaurant restaurant) {
        HashSet<Driver> available = new HashSet<>();
        for ( Driver driver : restaurant.getDrivers()) {
            if (driver.getStatus() == Driver.Status.FREE) {
                available.add(driver);
            }
        }
        Driver best = null;
        for ( Driver driver : available) {
            if (best == null || driver.averageRate() > best.averageRate()) {
                best = driver;
            }
        }
        return best;
    }
    public static void assignDriver(Restaurant restaurant, Order order) {
        findDriver(restaurant).addOrder(order);
        order.updateStatus(Order.Status.ACCEPTED);
    }
}