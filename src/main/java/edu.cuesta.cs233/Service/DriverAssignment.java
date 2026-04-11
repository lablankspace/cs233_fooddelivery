package service;

public class DriverAssignment {
    private List<Driver> drivers;
    DriverAssignment() {
        List<Driver> drivers = new ArrayList<>();
    }
    public void newDriver(driver) {
        drivers.add(driver);
    }
    public void removeDriver(driver) {
        drivers.remove(driver);
    }
    public Driver findDriver() {
        List<Driver> available = new List<>();
        for ( driver : drivers) {
            if (driver.status == Driver.Status.FREE) {
                available.add(driver);
            }
        }
        Drive best = null;
        for ( driver : available) {
            if (best == null || driver.averageRate() > best.averageRate()) {
                best = driver;
            }
        }
        return best;
    }
    public void assignDriver(order) {
        findDriver().addOrder(order);
        order.updateStatus(Order.Status.ACCEPTED);
    }
}