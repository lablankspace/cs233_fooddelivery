package app.fooddelivery;

import app.fooddelivery.service.StateManager;

import java.util.Objects;
import java.util.UUID;

public class Main {
    public static void main(String[] args){
        StateManager stateManager = new StateManager();
        System.out.println(stateManager.getJson());
    }
}
