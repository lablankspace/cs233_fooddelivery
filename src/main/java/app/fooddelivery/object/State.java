package app.fooddelivery.object;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class State {
    public final HashMap<UUID, User> users;
    public final Queue<Order> orderQueue;
    public Menu menu;

    public State() {
        users = new HashMap<>();
        orderQueue = new LinkedList<>();
        menu = new Menu();
    }
}
