package app.fooddelivery.service;

import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Order;

public class DriverAssignment {
    public static void assignDriver(Restaurant restaurant) {
        Order order = restaurant.nextOrder();
        if (order == null) return;
        Driver driver = findDriver(restaurant);
        if (driver == null) assignDriver(restaurant);
        order.updateStatus(Order.Status.ACCEPTED);
        driver.addOrder(order);
    }
    public static Driver findDriver(Restaurant restaurant) {
        Driver best = null;
        for ( Driver driver : restaurant.getDrivers()) {
            if (driver.getStatus() == Driver.Status.FREE) {
                if (best == null || driver.averageRate() > best.averageRate()) {
                    best = driver;
                }
            }
        }
        return best;
    }
}