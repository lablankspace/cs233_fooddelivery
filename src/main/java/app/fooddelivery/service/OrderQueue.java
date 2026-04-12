package app.fooddelivery.service;

import java.util.LinkedList;
import java.util.Queue;
import app.fooddelivery.object.Order;

public class OrderQueue {
    private Queue<Order> queue;
    OrderQueue(){
        Queue queue = new LinkedList<>();
    }
    public void addOrder(Order order) {
        queue.add(order);
    }
    public Order getOrder() {
        return queue.poll();
    }
    public Order viewNextOrder() { 
        return queue.peek();
    }
}