package app.fooddelivery.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class lanterna {
        public static void login() {
                DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
                Terminal terminal = null;
                try {
                        terminal = defaultTerminalFactory.createTerminal();
                        TerminalSize screenSize = terminal.getTerminalSize();
                        terminal.clearScreen();

                        final TextGraphics text = terminal.newTextGraphics();
                        text.setForegroundColor(TextColor.ANSI.GREEN);
                        String output = "ISLAND GIRLS DELIVERY SERVICE";
                        text.putString(screenSize.getColumns() / 2 - output.length() / 2, 0, output, SGR.BOLD);

                        output = "PLEASE LOGIN";
                        text.putString(screenSize.getColumns() / 2 - output.length() / 2, 1, output, SGR.BOLD);
                        
                        int selection = 0;
                        String[] options = {"Username: ", "Password: ", "Exit"};
                        for (int i = 0; i < options.length; i++) {
                                String prefix = (i == selection) ? "> " : "  ";
                                String line = prefix + options[i];
                                int x = screenSize.getColumns() / 2 - line.length() / 2;
                                text.putString(x, 3 + i, line);
                        }
                        terminal.flush();
                        StringBuilder user = new StringBuilder();
                        StringBuilder pass = new StringBuilder();
                    OUTER:
                    while (true) {
                        KeyStroke key = terminal.readInput();
                        if ( key.getKeyType() == KeyType.ArrowDown ) selection++;
                        if ( key.getKeyType() == KeyType.ArrowUp ) selection--;
                        if (selection < 0) selection = options.length - 1;
                        if (selection >= options.length) selection = 0;
                        if (key.getKeyType() == KeyType.Enter) {
                            switch (selection) {
                                case 0:
                                    selection = 1;
                                    break;
                                case 1:
                                    //Add a login / dashboard call here
                                    
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
                        for (int i = 0; i < options.length; i++) {
                            String prefix = (i == selection) ? "> " : "  ";
                            String line = prefix + options[i];
                            if ( i == 0) line += " " + user;
                            if ( i == 1) {
                                String mask = "*".repeat(pass.length());
                                line += " " + mask;
                            }
                            text.putString(0,3 + i, " ".repeat(screenSize.getColumns()));
                            int x = screenSize.getColumns() / 2 - line.length() / 2;
                            text.putString(x, 3 + i, line);
                        }
                        terminal.flush();
                    }
                        Thread.sleep(200);
                } catch(Exception e) {
                        e.printStackTrace();
                } finally {
                        if(terminal != null) {
                        try {
                                terminal.close();
                        } catch(Exception e) {
                                e.printStackTrace();
                                }
                        }
                }
        }
}