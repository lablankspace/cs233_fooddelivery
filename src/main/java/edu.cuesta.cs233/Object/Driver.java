package object;

public class Driver extends User {
    private Status status;
    private List<Order> orders;
    private List<Rate> rates;
    Driver() {
        super(name, id);
        this.status = Status.FREE;
        List<Order> orders = new ArrayList<>();
        List<Rate> rates = new ArrayList<>();
    }
    public void addOrder(order) {
        orders.add(order);
    }
    public String getOrders() {
        return orders.toString();
    }
    public void startOrder(order) {
        order.updateStatus(Order.Status.INPROGRESS);
        status = BUSY;
    }
    public void deliverOrder(order) {
        order.updateStatus(Order.Status.DELIVERED);
        status = FREE;
    }
    public String getStatus() {
        return status;
    }
    public void goOffline() {
        status = Status.OFFLINE;
    }
    public double averageRate() {
        double avg = 0;
        for (Rate rate : rates) {
            avg += rate.getScore();
        }
        return (avg / 5);;
        
    }
    public String getRates() {

    }
    public void deleteAccount() {
        DriverAssignment.removeDriver(this);
    }
    enum Status {
        FREE,
        BUSY,
        OFFLINE
}
}