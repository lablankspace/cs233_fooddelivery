class Customer extends User {
    private List<Order> orderHistory;
    Customer(String name, int id) { 
        super(name, id) 
        List<Order> orderHistory = new ArrayList<>();
    }
    public void placeOrder(){
        Order order = new Order(this);
        //Adding items via UI somehow
        item = selectItem();
        order.addItem(item)''
        //Push order once finished
        orderHistory.add(order);
        OrderQueue.addOrder(order)
    }
    public void rateDriver(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        return rating;
    }
    }
    public String viewOrderStatus(order) {
        return order.delivered.toString();
    }
    public String viewOrderHistory() {
        return orderHistory.toString();
    }
}