class Order {
    private Customer customer;
    private List<Item> items;
    private Status status;
    Order(Customer customer) {
        this.customer = customer;
        List<Item> items = new ArrayList<>();
        this.status = Status.PLACED;
    }
    public void addItem(item) {
        items.add(item);
    }
    public void removeItem(item) {
        items.remove(item);
    }
    public void getCustomer() {
        return customer;
    }
    public List<Item> getItems() {
        return items;
    }
    public String getStatus() {
        return status;
    }
    public void updateStatus(Status status) {
        this.status = status;
    }
    enum Status {
        PLACED,
        ACCEPTED,
        INPROGRESS,
        DELIVERED
    }
}