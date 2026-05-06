package app.fooddelivery;

import java.io.File;
import java.io.IOException;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.State;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.service.StateLoader;
import app.fooddelivery.ui.Login;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;

public class Main {
    public static void main(String[] args){
        File stateFileTarget = new File("state.json");
        State state = StateLoader.loadFrom(stateFileTarget).orElseGet(State::new);
        Restaurant restaurant = new Restaurant(state);

        Customer c = new Customer("cust", "cust");
        Driver d = new Driver("driv", "driv");
        Admin a = new Admin("admin", "admin");
        restaurant.addUser(c);
        restaurant.addUser(d);
        restaurant.addUser(a);
        
        Item burger = new Item("Burger", "A thing that Burgs", 12.50);
        Item fries = new Item("Fries", "A thing that lies about being french", 5.00);
        restaurant.getMenu().addItem(fries);
        restaurant.getMenu().addItem(burger);

        Term term = new Term();
        Screen current = new Login();
        while (current != null) {
            current = current.run(term, restaurant);
        }
    //    try {
    //        Login.run(term, restaurant);        
    //    } catch(Exception e) {
    //        e.printStackTrace();
    //    }finally{
            try {
				StateLoader.saveTo(stateFileTarget,state);
			} catch (IOException e) {
				System.err.println("Failed to save!");
			}
        }
    }