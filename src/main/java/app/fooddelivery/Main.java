package app.fooddelivery;

import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Login;
import app.fooddelivery.service.StateManager;

import java.util.Objects;
import java.util.UUID;

public class Main {
    public static void main(String[] args){
        StateManager stateManager = new StateManager();
        System.out.println(stateManager.getJson());
        Restaurant restaurant = new Restaurant();
      //  boolean createdNew = false;

        // Try loading saved school data
      /*  try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("school.dat"))) {
            school = (School) in.readObject();
            System.out.println("Loaded school data.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved school data found, starting fresh.");
            System.out.print("Enter School Name: ");
            school = new School(scnr.nextLine().trim());
            createdNew = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // If a new school was created, create the first teacher
        if (createdNew) {
            System.out.println("\n=== Setup: Create the first teacher ===");
            System.out.print("Full name: ");
            String name = scnr.nextLine().trim();
            System.out.print("Username: ");
            String username = scnr.nextLine().trim();
            System.out.print("Email: ");
            String email = scnr.nextLine().trim();
            System.out.print("Password: ");
            String password = scnr.nextLine().trim();

            Teacher firstTeacher = new Teacher(name, username, email);
            school.addTeacher(firstTeacher, password);
            System.out.println("New School and Teacher created.");
        }
*/
        try {
            Login.run(restaurant);        
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
}