package app.fooddelivery;

import app.fooddelivery.object.State;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.service.StateLoader;
import app.fooddelivery.ui.Login;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        File stateFileTarget = new File("state.json");
        State state = StateLoader.loadFrom(stateFileTarget).orElseGet(State::new);
        Restaurant restaurant = new Restaurant(state);
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
        }finally{
            try {
				StateLoader.saveTo(stateFileTarget,state);
			} catch (IOException e) {
				System.err.println("Failed to save!");
			}
        }
    }
}