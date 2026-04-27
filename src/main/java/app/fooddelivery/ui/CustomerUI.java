package app.fooddelivery.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Menu;
import app.fooddelivery.service.Restaurant;


public class CustomerUI {
    public static void home(Terminal terminal, Customer customer) {
        try {
            TerminalSize screenSize = terminal.getTerminalSize();
            terminal.clearScreen();
            final TextGraphics text = terminal.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.GREEN);
            Utils.Title(screenSize, text);
            Utils.subTitle(screenSize, text, "CUSTOMER", 1);
                  
            int selection = 0;
            String[] options = {"Place Order", "View Order History", "Rate Order", "Exit"};
            Utils.selection(selection, options, screenSize, text, 3);
            OUTER:
                while (true) {
                KeyStroke key = terminal.readInput();
                selection = Utils.changeSelection(selection, key, options.length);
                Utils.selection(selection, options, screenSize, text, 3);
                if (key.getKeyType() == KeyType.Enter) {
                    switch (selection) {
                        case 0:
                            terminal.clearScreen();
                            placeOrder(terminal, customer); 
                            break;
                        case 1:
                            // TODO: IMPLEMENT VIEW ORDER HISTORY
                            break;
                        case 2:
                            // TODO: IMPLEMENT RATE ORDER
                            break;
                        case 3:
                            terminal.clearScreen();
                            break OUTER;
                        default: 
                            break;
                    }
                }
                terminal.flush();
            }
        } catch(Exception e) {
                        e.printStackTrace();
                }
    }
    public static void placeOrder(Terminal terminal, Customer customer) {
        try {
            TerminalSize screenSize = terminal.getTerminalSize();
            terminal.clearScreen();
            final TextGraphics text = terminal.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.GREEN);
            Utils.Title(screenSize, text);
            Utils.subTitle(screenSize, text, "PLACE ORDER", 1);
                  
            int selection = 0;
            //String[] items = restaurant.getMenu();  //TODO FIND SOME WAY TO GET THE MENU WITHOUT PASSING RESTAURANT
            String[] options = {"Burgers   $12.00", "Pizza   $6.00","Salad $4.50"};
            int[] quantity = {0,0,0};
            Utils.selection(selection, options, screenSize, text, 3);
            OUTER:
                while (true) {
                KeyStroke key = terminal.readInput();
                quantity = Utils.changeAmount(selection, key, quantity);
                selection = Utils.changeSelection(selection, key, options.length);
                Utils.amount(selection, options, quantity, screenSize, text, 3);
               /* if (key.getKeyType() == KeyType.Enter) {
                    switch (selection) {
                        case 0:
                            // TODO: IMPLEMENT PLACE ORDER 
                            break;
                        case 1:
                            // TODO: IMPLEMENT VIEW ORDER HISTORY
                            break;
                        case 2:
                            // TODO: IMPLEMENT RATE ORDER
                            break;
                        case 3:
                            terminal.clearScreen();
                            break OUTER;
                        default: 
                            break;
                    } 
                } */
                terminal.flush();
            }
        } catch(Exception e) {
                        e.printStackTrace();
                }
    }
}
