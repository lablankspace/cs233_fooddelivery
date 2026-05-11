package app.fooddelivery.object;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class State {
    public final HashMap<UUID, User> users;
    public final HashSet<Order> orders;
    public final Queue<Order> orderQueue;
    public final Menu menu;
    public State(){
        users = new HashMap<>();
        orders = new HashSet<>();
        orderQueue = new LinkedList<>();
        menu = new Menu();
    }
}
