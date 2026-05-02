package app.fooddelivery;

import java.io.File;
import java.io.IOException;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.State;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.service.StateLoader;
import app.fooddelivery.ui.Login;
import app.fooddelivery.ui.Utils.Term;

public class Main {
    public static void main(String[] args){
        File stateFileTarget = new File("state.json");
        State state = StateLoader.loadFrom(stateFileTarget).orElseGet(State::new);
        Restaurant restaurant = new Restaurant(state);
        Customer c = new Customer("Sage", "password");
        restaurant.addUser(c, "password");
        Term term = new Term();
        try {
            Login.run(term, restaurant);        
        } catch(Exception e) {
            e.printStackTrace();
        }finally{
            try {
				StateLoader.saveTo(stateFileTarget,state);
			} catch (IOException e) {
				System.err.println("Failed to save!");
			}
        }
    }
}