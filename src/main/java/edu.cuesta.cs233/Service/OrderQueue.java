package service;

public class OrderQueue {
    private Queue queue;
    OrderQueue(){
        Queue queue = new Queue<>();
    }
    public void addOrder(order) {
        queue.add(order);
    }
    public Order getOrder() {
        return queue.poll();
    }
    public Order viewNextOrder() { 
        return queue.peek();
    }
}