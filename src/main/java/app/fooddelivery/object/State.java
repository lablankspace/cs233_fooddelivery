package app.fooddelivery.object;

import java.util.*;

public class State {
    public final Set<User> users;
    public final Set<Order> orders;
    public final Queue<Order> orderQueue;
    public State(){
        users = new HashSet<>();
        orders = new HashSet<>();
        orderQueue = new LinkedList<>();
    }
}
