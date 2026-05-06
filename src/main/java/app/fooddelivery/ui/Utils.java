package app.fooddelivery.ui;

import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Utils {
        interface Line {
                String render(boolean selected);
                void onKey(KeyStroke key);
        }
        interface Action {
                void run();
        }
        public static class exitCondition {
                boolean condition = true;
        }
        public static class Term {
                private Terminal terminal;
                private TextGraphics text;
                private TerminalSize size;

                public Term() {
                        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
                        try {
                        this.terminal = defaultTerminalFactory.createTerminal();
                        this.size = terminal.getTerminalSize();
                        text = terminal.newTextGraphics().setForegroundColor(TextColor.ANSI.GREEN);
                        } catch (Exception e) {
                                throw new RuntimeException(e);
                        }
                }
                public Terminal terminal() {
                        return terminal;
                }
                public TextGraphics text() {
                        return text;
                }
                public TerminalSize size() {
                        return size;
                }
                public KeyStroke readKey() {
                        try {
                                return terminal.readInput();
                        } catch (Exception e) {
                                throw new RuntimeException(e);
                        }
                }

                public void flush() {
                        try {
                                terminal.flush();
                        } catch (Exception e) {
                        throw new RuntimeException(e);
                        }
                }
                public void clear() {
                        try {
                                terminal.clearScreen();
                        } catch (Exception e) {
                        throw new RuntimeException(e);
                        }
                }

        }
        public static class Text implements Line {
                private String label;

                Text(String label) {
                        this.label = label;
                }

                public String render(boolean selected) {
                        return label;
                }

                public void onKey(KeyStroke key) {}
        }
        public static class Button implements Line {
                private String label;
                private Action action;
                Button(String label, Action action) {
                        this.label = label;
                        this.action = action;
                }

                public String render(boolean selected) {
                        return (selected ? "> " : " ") + label;
                }
                
                public void onKey(KeyStroke key) {
                        if (key.getKeyType() == KeyType.Enter) {
                                action.run();
                        }
                }
        }
        public static class Amount implements Line {
                private String label;
                private int quantity;
                final int min;
                final int max;

                Amount(String label) {
                        this.label = label;
                        this.min = 0;
                        this.max = 100;
                        this.quantity = this.min;
                }
                Amount(String label, int min, int max) {
                        this.label = label;
                        this.quantity = min;
                        this.min = min;
                        this.max = max;
                }

                public String render(boolean selected) {
                        return (selected ? "> " : " ") + label + " - " + quantity + " + ";
                }

                public void onKey(KeyStroke key) {
                        if ( key.getKeyType() == KeyType.ArrowRight && quantity < max) {
                                quantity++;
                        }
                        if ( key.getKeyType() == KeyType.ArrowLeft && quantity > min) { 
                                quantity--;
                        }
                }

                public int quantity() {
                        return quantity;
                }
        }

        public static class Input implements Line {
                private String label;
                private String value ="";
                private boolean hidden;

                Input(String label, boolean hidden) {
                        this.label = label;
                        this.hidden = hidden;
                }

                public String render(boolean selected) {
                        String display = hidden ? "*".repeat(value.length()) : value;
                        return (selected ? "> " : " ") + label + " " + display;
                }

                public void onKey(KeyStroke key) {
                        if (key.getKeyType() == KeyType.Character) {
                                value += key.getCharacter();
                        }
                        if (key.getKeyType() == KeyType.Backspace && value.length() > 0) {
                                value = value.substring(0, value.length() - 1);
                        }
                }
                public String getInput() {
                        return value;
                }
        }
        public static void render(List<Line> lines, int selection, TerminalSize screenSize, TextGraphics text, int y) {
                for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i).render(i == selection);
                        
                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                } 
        }
        public static int keyPress(int selection, KeyStroke key, List<Line> lines) {
                if (key.getKeyType() == KeyType.ArrowDown) selection++;
                else if (key.getKeyType() == KeyType.ArrowUp) selection--;
                else if (key.getKeyType() == KeyType.Enter && (lines.get(selection) instanceof Utils.Input || lines.get(selection) instanceof Utils.Amount)) selection++;
                else  { lines.get(selection).onKey(key);} 
                
                if (selection < 0) selection = lines.size() - 1;
                if (selection >= lines.size()) selection = 0;


                return selection;
        }
        public static void loop(List<Line> title, List<Line> lines, Term term, exitCondition active) {
                int selection = 0;

                render(title, -1, term.size(), term.text(), 0);
                render(lines, selection, term.size(), term.text(), 3);

                term.flush();

                while (active.condition) {
                        KeyStroke key = term.readKey();

                        selection = keyPress(selection, key, lines);

                        render(title, -1, term.size(), term.text(), 0);
                        render(lines, selection, term.size(), term.text(), 3);

                        term.flush();
                }
        }
        public static void loop(List<Line> title, List<Line> lines, Term term, exitCondition active, int y) {
                int selection = 0;

                render(title, -1, term.size(), term.text(), 0);
                render(lines, selection, term.size(), term.text(), y);

                term.flush();

                while (active.condition) {
                        KeyStroke key = term.readKey();

                        selection = keyPress(selection, key, lines);

                        render(title, -1, term.size(), term.text(), 0);
                        render(lines, selection, term.size(), term.text(), y);

                        term.flush();
                }
        }
}