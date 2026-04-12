package app.fooddelivery.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Customer;
import app.fooddelivery.object.Driver;
import app.fooddelivery.service.LoginProcess;
import app.fooddelivery.service.Restaurant;



public class Login {
        public static void run(Restaurant restaurant) {
                DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
                Terminal terminal = null;
                try {
                        terminal = defaultTerminalFactory.createTerminal();
                        TerminalSize screenSize = terminal.getTerminalSize();
                        terminal.clearScreen();

                        final TextGraphics text = terminal.newTextGraphics();
                        text.setForegroundColor(TextColor.ANSI.GREEN);

                        Utils.Title(screenSize, text);
                        Utils.subTitle(screenSize, text, "PLEASE LOGIN", 1);
                        
                        int selection = 0;
                        String[] options = {"Username: ", "Password: ", "Exit"};
                        Utils.selection(selection, options, screenSize, text, 3);

                        terminal.flush();
                        StringBuilder user = new StringBuilder();
                        StringBuilder pass = new StringBuilder();

                    OUTER:
                    while (true) {
                        KeyStroke key = terminal.readInput();
                        if ( key.getKeyType() == KeyType.ArrowDown ) {
                            selection++;
                            Utils.selection(selection, options, screenSize, text, 3);
                        }
                        if ( key.getKeyType() == KeyType.ArrowUp ) { 
                            selection--;
                            Utils.selection(selection, options, screenSize, text, 3);
                        }
                        if (selection < 0) {
                            selection = options.length - 1;
                            Utils.selection(selection, options, screenSize, text, 3);
                        }
                        if (selection >= options.length) {
                            selection = 0;
                            Utils.selection(selection, options, screenSize, text, 3);
                        }
                        if (key.getKeyType() == KeyType.Enter) {
                            switch (selection) {
                                case 0:
                                    selection = 1;
                                    break;
                                case 1:
                                    //Add a login / dashboard call here
                                    switch (LoginProcess.checkLogin(restaurant, user.toString(), pass.toString())) {
                                        case 0:
                                            Home.admin(restaurant, (Admin) LoginProcess.getUser(restaurant, user.toString()));
                                            break OUTER;
                                        case 1:
                                            Home.driver(restaurant, (Driver) LoginProcess.getUser(restaurant, user.toString()));
                                            break OUTER;
                                        case 2:
                                            Home.customer(restaurant, (Customer) LoginProcess.getUser(restaurant, user.toString()));
                                            break OUTER;
                                        case 3:
                                            Utils.subTitle(screenSize, text, "INCORRECT LOGIN PLEASE TRY AGAIN", 2);
                                            break;
                                    }
                                    break;
                                case 2:
                                    break OUTER;
                                default:
                                    break;
                            }
                        }
                        if ( key.getKeyType() == KeyType.Backspace) { 
                                if ( selection == 0 && user.length() > 0 ) { 
                                        user.deleteCharAt( user.length() - 1 ); 
                                } 
                                if ( selection == 1 && pass.length() > 0 ) { 
                                        pass.deleteCharAt( pass.length() - 1 ); 
                                }
                        }
                        if ( key.getKeyType() == KeyType.Character) {
                            if (selection == 0 ) user.append(key.getCharacter());
                            if (selection == 1 ) pass.append(key.getCharacter());
                        }
                        String[] labels = {options[0], options[1]};
                        String[] inputs = {user.toString(), pass.toString()};
                        boolean[] hidden = {false, true};
                        
                        Utils.input(selection, labels, inputs, screenSize, text, 3, hidden);
                        terminal.flush();
                    }
                        Thread.sleep(200);
                } catch(Exception e) {
                        e.printStackTrace();
                } 
        }
}