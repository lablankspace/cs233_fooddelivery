package app.fooddelivery.object;

import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

import app.fooddelivery.object.Menu;

public class State {
    public final HashSet<User> users;
    public final HashSet<Order> orders;
    public final Queue<Order> orderQueue;
    public final Menu menu;
    public State(){
        users = new HashSet<>();
        orders = new HashSet<>();
        orderQueue = new LinkedList<>();
        menu = new Menu();
    }
}
