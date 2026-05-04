public class Order {
    private final String orderId;
    private final String customerName;
    public Order(String orderId, String customerName) {
        this.orderId = orderId;
        this.customerId = customerName;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getCustomerName() {
        return customerName;
    }
    @Override
    public String toString() {
        return "Order{id='" + orderId + "', customerName='" + customerName + "'}";
    }
}
