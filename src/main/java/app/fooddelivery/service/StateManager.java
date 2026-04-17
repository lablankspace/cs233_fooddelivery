package app.fooddelivery.service;

import app.fooddelivery.object.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public class StateManager {
    private final Gson gson;
    private final State state;
    public StateManager(){
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                // State
                .registerTypeAdapter(State.class, new Serialization.StateSerializer())
                .registerTypeAdapter(State.class, new Deserialization.StateDeserializer())
                // Order
                .registerTypeAdapter(Order.class, new Serialization.OrderSerializer())
                .registerTypeAdapter(Order.class, new Deserialization.OrderDeserializer())
                // Customer
                .registerTypeAdapter(Customer.class, new Serialization.CustomerSerializer())
                .registerTypeAdapter(Customer.class, new Deserialization.CustomerDeserializer())
                // Driver
                .registerTypeAdapter(Driver.class, new Serialization.DriverSerializer())
                .registerTypeAdapter(Driver.class, new Deserialization.DriverDeserializer())
                // UserRating
                .registerTypeAdapter(UserRating.class, new Serialization.UserRatingSerializer())
                .registerTypeAdapter(UserRating.class, new Deserialization.UserRatingDeserializer())
                .create();
        this.state = new State();
    }

    public String getJson(){
        return this.gson.toJson(state);
    }
}
