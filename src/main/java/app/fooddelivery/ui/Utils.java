package app.fooddelivery.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;


public class Utils {
        public static void Title(TerminalSize screenSize, TextGraphics text){
                String output = "ISLAND GIRLS DELIVERY SERVICE";
                text.putString(screenSize.getColumns() / 2 - output.length() / 2, 0, output, SGR.BOLD);
        } 
        public static void subTitle(TerminalSize screenSize, TextGraphics text, String output, int y){
                text.putString(screenSize.getColumns() / 2 - output.length() / 2, y, output, SGR.BOLD);
        }
        public static void selection(int selection, String[] options, TerminalSize screenSize, TextGraphics text, int y) {
                for (int i = 0; i < options.length; i++) {
                        String prefix = (i == selection) ? "> " : "  ";
                        String line = prefix + options[i];
                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                }
        }
        public static int changeSelection(int selection, KeyStroke key, int optionsLength) {
                if ( key.getKeyType() == KeyType.ArrowDown ) {
                        selection++;
                }
                if ( key.getKeyType() == KeyType.ArrowUp ) { 
                        selection--;
                }
                if (selection < 0) {
                        selection = optionsLength - 1;
                }
                if (selection >= optionsLength) {
                        selection = 0;
                }
        return selection;
        }
        public static void input(int selection, String[] options, String[] inputs, TerminalSize screenSize, TextGraphics text, int y, boolean[] hidden) {
                for (int i = 0; i < options.length; i++) {
                        String prefix = (i == selection) ? "> " : "  ";
                        String line = prefix + options[i];
                        if ( !hidden[i] ) line += " " + inputs[i];
                        else {
                        String mask = "*".repeat(inputs[i].length());
                        line += " " + mask ;
                        }
                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                }
       }
        public static void selection2D(Select2D selection, String[][] options, TerminalSize screenSize, TextGraphics text, int y) {
                int rows = options.length;
                for (int row = 0; row < rows; row++) {
                        int currentX = screenSize.getColumns() / 2;
                        int totalWidth = 0;
                        for (int col = 0; col < options[row].length; col++) {
                                totalWidth += options[row][col].length() + 3;
                        }
                        currentX -= totalWidth / 2;
                        for (int col = 0; col < options[row].length; col++) {

                                boolean isSelected = (row == selection.y && col == selection.x);

                                if (isSelected) {
                                        text.setBackgroundColor(TextColor.ANSI.RED);
                                } else {
                                        text.setBackgroundColor(TextColor.ANSI.DEFAULT);
                                }

                                String value = options[row][col];
                                text.putString(currentX, y + row, value);

                                currentX += value.length() + 3;
                        }
                }
        }
        public static Select2D changeSelection2D(Select2D selection, KeyStroke key, int width, int height) {
                if ( key.getKeyType() == KeyType.ArrowDown ) {
                        selection.y++;
                }
                if ( key.getKeyType() == KeyType.ArrowUp ) { 
                        selection.y--;
                }
                if (selection.y < 0) {
                        selection.y = height - 1;
                }
                if (selection.y >= height) {
                        selection.y = 0;
                }
                if ( key.getKeyType() == KeyType.ArrowRight ) {
                        selection.x++;
                }
                if ( key.getKeyType() == KeyType.ArrowLeft ) { 
                        selection.x--;
                }
                if (selection.x < 0) {
                        selection.x = width - 1;
                        selection.y--;
                }
                if (selection.x >= width) {
                        selection.x = 0;
                        selection.y++;
                }
        return selection;
        }
}