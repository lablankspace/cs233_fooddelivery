package app.fooddelivery;

import app.fooddelivery.object.State;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.service.StateLoader;
import app.fooddelivery.ui.Login;
import app.fooddelivery.ui.Utils.Term;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        File stateFileTarget = new File("state.json");
        State state = StateLoader.loadFrom(stateFileTarget).orElseGet(State::new);
        Restaurant restaurant = new Restaurant(state);
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