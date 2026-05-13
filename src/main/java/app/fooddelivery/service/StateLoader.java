package app.fooddelivery.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Deserialization;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Order;
import app.fooddelivery.object.QuantizedItem;
import app.fooddelivery.object.Rating;
import app.fooddelivery.object.Serialization;
import app.fooddelivery.object.State;

public class StateLoader {
    private static Gson gson;

    private static void initGsonIfNull() {
        if (gson != null)
            return;
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(State.class, new Serialization.StateSerializer())
                .registerTypeAdapter(State.class, new Deserialization.StateDeserializer())
                .registerTypeAdapter(Order.class, new Serialization.OrderSerializer())
                .registerTypeAdapter(Order.class, new Deserialization.OrderDeserializer())
                .registerTypeAdapter(QuantizedItem.class, new Serialization.QuantizedItemSerializer())
                .registerTypeAdapter(QuantizedItem.class, new Deserialization.QuantizedItemDeserializer())
                .registerTypeAdapter(Item.class, new Serialization.ItemSerializer())
                .registerTypeAdapter(Item.class, new Deserialization.ItemDeserializer())
                .registerTypeAdapter(Admin.class, new Serialization.AdminSerializer())
                .registerTypeAdapter(Admin.class, new Deserialization.AdminDeserializer())
                .registerTypeAdapter(Customer.class, new Serialization.CustomerSerializer())
                .registerTypeAdapter(Customer.class, new Deserialization.CustomerDeserializer())
                .registerTypeAdapter(Driver.class, new Serialization.DriverSerializer())
                .registerTypeAdapter(Driver.class, new Deserialization.DriverDeserializer())
                .registerTypeAdapter(Rating.class, new Serialization.UserRatingSerializer())
                .registerTypeAdapter(Rating.class, new Deserialization.UserRatingDeserializer())
                .create();
    }

    public static Optional<State> loadFrom(File file) {
        initGsonIfNull();
        try (FileReader reader = new FileReader(file)) {
            return Optional.of(gson.fromJson(reader, State.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void saveTo(File file, State state) throws IOException {
        initGsonIfNull();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(state, writer);
        }
    }
}
