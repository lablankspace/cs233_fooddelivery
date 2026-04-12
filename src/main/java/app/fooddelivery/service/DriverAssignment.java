package app.fooddelivery.service;

import java.util.HashSet;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Order;

public class DriverAssignment {
    private static HashSet<Driver> drivers;
    DriverAssignment() {
        drivers = new HashSet<>();
    }
    public static void newDriver(Driver driver) {
        drivers.add(driver);
    }
    public static void removeDriver(Driver driver) {
        drivers.remove(driver);
    }
    public Driver findDriver() {
        HashSet<Driver> available = new HashSet<>();
        for ( Driver driver : drivers) {
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
    public void assignDriver(Order order) {
        findDriver().addOrder(order);
        order.updateStatus(Order.Status.ACCEPTED);
    }
}