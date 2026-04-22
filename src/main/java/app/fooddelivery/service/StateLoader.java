package app.fooddelivery.service;

import app.fooddelivery.object.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Optional;

public class StateLoader {
    private static Gson gson;
    private static void initGsonIfNull(){
        if (gson != null)return;
        gson = new GsonBuilder()
                .setPrettyPrinting()
                // State
                .registerTypeAdapter(State.class, new Serialization.StateSerializer())
                .registerTypeAdapter(State.class, new Deserialization.StateDeserializer())
                // Order
                .registerTypeAdapter(Order.class, new Serialization.OrderSerializer())
                .registerTypeAdapter(Order.class, new Deserialization.OrderDeserializer())
                // QuantizedItemSerializer
                .registerTypeAdapter(QuantizedItem.class, new Serialization.QuantizedItemSerializer())
                .registerTypeAdapter(QuantizedItem.class, new Deserialization.QuantizedItemDeserializer())
                // Admin
                .registerTypeAdapter(Admin.class, new Serialization.AdminSerializer())
                .registerTypeAdapter(Admin.class, new Deserialization.AdminDeserializer())
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
    }
    public static Optional<State> loadFrom(File file){
        initGsonIfNull();
        try (FileReader reader = new FileReader(file)){
            return Optional.of(gson.fromJson(reader,State.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void saveTo(File file, State state) throws IOException {
        initGsonIfNull();
        FileWriter writer = new FileWriter(file);
        gson.toJson(state, writer);
        writer.close();
    }
}
