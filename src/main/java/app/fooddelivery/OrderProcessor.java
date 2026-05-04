import java.util.Queue;
import java.util.ArrayDeque;

public class OrderProcessor {
    private final Queue<Order> orderQueue = new ArrayDeque<>();
    public void receiveOrder(Order order) {
        orderQueue.offer(order);
        System.out.println("Order received: " + order);
    }
    public void processNextOrder() {
        Order order = orderQueue.poll();
        if (order == null) {
            System.out.println("No Current Orders");
            return;
        }
        System.out.println("Order processing: " + order);
    }
    public void processAllOrders() {
        while (!orderQueue.isEmpty()) {
            processNextOrder();
        }
    }
}