package app.fooddelivery.object;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Serialization {
    public static class StateSerializer implements JsonSerializer<State> {

        @Override
        public JsonElement serialize(State state, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            obj.add("users",context.serialize(state.users));
            obj.add("orders",context.serialize(state.orders));
            JsonArray orderQueue = new JsonArray();
            for (Order order : state.orderQueue){
                orderQueue.add(order.getId().toString());
            }
            obj.add("orderQueue",orderQueue);

            return obj;
        }
    }
    public static class OrderSerializer implements JsonSerializer<Order> {

        @Override
        public JsonElement serialize(Order order, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            obj.addProperty("id",order.getId().toString());
            obj.add("customerId",context.serialize(order.getCustomer().getId()));

            return obj;
        }
    }
    public static class CustomerSerializer implements JsonSerializer<Customer> {

        @Override
        public JsonElement serialize(Customer customer, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            obj.addProperty("name",customer.getName());
            obj.addProperty("id",customer.getId().toString());
            obj.addProperty("role",customer.getRole());
            obj.addProperty("phash",customer.getId().toString());

            return obj;
        }
    }
    public static class DriverSerializer implements JsonSerializer<Driver> {

        @Override
        public JsonElement serialize(Driver driver, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            obj.addProperty("name",driver.getName());
            obj.addProperty("id",driver.getId().toString());
            obj.addProperty("role",driver.getRole());
            obj.add("ratings", context.serialize(driver.ratings));

            return obj;
        }
    }
    public static class UserRatingSerializer implements JsonSerializer<UserRating> {

        @Override
        public JsonElement serialize(UserRating userRating, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            obj.addProperty("score",userRating.getScore());
            obj.addProperty("timestamp",userRating.getTimeStamp());
            obj.addProperty("creatorId",userRating.getCreatorId().toString());

            return obj;
        }
    }
}
