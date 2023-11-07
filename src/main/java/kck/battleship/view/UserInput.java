package kck.battleship.view;


import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.controller.GameException;
import kck.battleship.model.clases.BattleField;
import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.clases.Position;

import java.io.IOException;

public class UserInput {

    public static String getUserInput(Screen screen, Terminal terminal, String message) throws IOException, InterruptedException, GameException {
        TextGraphics tg = screen.newTextGraphics();
        StringBuilder userInput = new StringBuilder();
        KeyStroke keyStroke;
        boolean canSubmit = false;

        do {
            keyStroke = terminal.pollInput();

            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Backspace) {
                    if (userInput.length() > 0)
                        userInput.deleteCharAt(userInput.length() - 1);

                } else if (keyStroke.getKeyType() == KeyType.Character) {
                    char c = keyStroke.getCharacter();

                    if (Character.isLetterOrDigit(c) && userInput.length() < 15)
                        userInput.append(c);
                } else if (keyStroke.getKeyType() == KeyType.Enter) {
                    if (userInput.toString().trim().length() > 0) {
                        canSubmit = true;
                    }
                } else if (keyStroke.getKeyType() == KeyType.Escape) {
                    TextView.printMenuPage(0);
                    TextView.chooseOption(terminal, 0);
                    return null;
                }
            }

            screen.clear();
            TextView.printTitle();

            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            if (message.length() > 40)
                tg.putString(12, 12, message, SGR.BOLD);
            else
                tg.putString(20, 12, message, SGR.BOLD);

            tg.putString(25, 14, "NICK: ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(31, 14, String.valueOf(userInput), SGR.BOLD);
            tg.putString(31 + String.valueOf(userInput).length(), 14, "|  ", SGR.BOLD, SGR.BLINK);

            screen.refresh();

            Thread.sleep(10);
        } while (!canSubmit);

        tg.putString(31 + String.valueOf(userInput).length(), 14, "  ");
        screen.refresh();

        return userInput.toString();
    }

    public static boolean question(Screen screen, Terminal terminal, String message) throws IOException, InterruptedException {
        char userResponse = '\0';
        TextGraphics tg = screen.newTextGraphics();
        KeyStroke keyStroke;

        do {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            keyStroke = terminal.pollInput();

            if (keyStroke != null)
                if (keyStroke.getKeyType() == KeyType.Character) {
                    char c = Character.toLowerCase(keyStroke.getCharacter());

                    if (c == 'y' || c == 'n')
                        userResponse = c;
                }

            tg.putString(16, 17, message, SGR.BOLD);
            tg.putString(25, 19, "Odpowiedź: ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(36, 19, String.valueOf((userResponse == '\0' ? "" : userResponse)), SGR.BOLD);
            if (userResponse == '\0')
                tg.putString(36, 19, "|  ", SGR.BOLD, SGR.BLINK);
            else
                tg.putString(37, 19, "|  ", SGR.BOLD, SGR.BLINK);
            screen.refresh();
            Thread.sleep(10);
        } while (userResponse == '\0');
        return userResponse == 'y';
    }

    public static Position readPosition(Screen screen, Terminal terminal, String message) {
        TextGraphics tg = screen.newTextGraphics();
        StringBuilder userInput = new StringBuilder();
        KeyStroke keyStroke;
        boolean canSubmit = false;

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(2, 19, "Pozycja:     ", SGR.BOLD);

        try {
            do {
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(2, 18, message, SGR.BOLD);
                tg.putString(2, 19, "                           ", SGR.BOLD);
                tg.putString(2, 19, "Pozycja: ", SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                tg.putString(11, 19, String.valueOf(userInput), SGR.BOLD);
                tg.putString(11 + userInput.length(), 19, "|  ", SGR.BOLD, SGR.BLINK);
                Thread.sleep(10);

                keyStroke = terminal.pollInput();

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Backspace) {
                        if (userInput.length() > 0) {
                            userInput.deleteCharAt(userInput.length() - 1);
                            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                            tg.putString(2, 19, "Pozycja: ", SGR.BOLD);
                            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                            tg.putString(11, 19, String.valueOf(userInput), SGR.BOLD);
                            tg.putString(11 + userInput.length(), 19, "|  ", SGR.BOLD, SGR.BLINK);
                        }
                    } else if (keyStroke.getKeyType() == KeyType.Character) {
                        char c = keyStroke.getCharacter();

                        if (Character.isLetterOrDigit(c) && userInput.length() < 3) {
                            c = Character.toLowerCase(c);
                            userInput.append(c);
                        }
                    } else if (keyStroke.getKeyType() == KeyType.Enter) {
                        if (userInput.toString().trim().length() > 0) {
                            canSubmit = true;
                        }
                    }
                }
                screen.refresh();
            } while (!canSubmit);

            tg.putString(11 + userInput.length(), 19, "   ");
            screen.refresh();

            int row = userInput.charAt(0) - 'a';
            int column = Integer.parseInt(userInput.substring(1));

            if (row >= BattleField.getLength() || column > BattleField.getLength() || row < 0 || column < 0)
                throw new GameException(null);

            return new Position(row, column - 1);
        } catch (GameException | NumberFormatException | StringIndexOutOfBoundsException e) {
            TextView.printError("Błąd: " + userInput + " , wybierz od a1 do j10");
            return readPosition(screen, terminal, message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static TypesDirection readDirection(Screen screen, Terminal terminal, String message) {
        TextGraphics tg = screen.newTextGraphics();
        StringBuilder userInput = new StringBuilder();
        KeyStroke keyStroke;
        boolean canSubmit = false;


        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(2, 22, "Kierunek:     ", SGR.BOLD);

        try {
            do {
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(2, 21, message, SGR.BOLD);
                tg.putString(2, 22, "                           ", SGR.BOLD);
                tg.putString(2, 22, "Kierunek: ", SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                tg.putString(12, 22, String.valueOf(userInput), SGR.BOLD);
                tg.putString(12 + userInput.length(), 22, "|  ", SGR.BOLD, SGR.BLINK);
                Thread.sleep(10);

                keyStroke = terminal.pollInput();

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Backspace) {
                        if (userInput.length() > 0) {
                            userInput.deleteCharAt(userInput.length() - 1);
                            tg.putString(2, 22, "Kierunek: ", SGR.BOLD);
                            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                            tg.putString(12, 22, String.valueOf(userInput), SGR.BOLD);
                            tg.putString(12 + userInput.length(), 22, "|  ", SGR.BOLD, SGR.BLINK);
                        }
                    } else if (keyStroke.getKeyType() == KeyType.Character) {
                        char c = keyStroke.getCharacter();

                        if (Character.isLetterOrDigit(c) && userInput.length() < 1) {
                            c = Character.toLowerCase(c);
                            userInput.append(c);
                        }
                    } else if (keyStroke.getKeyType() == KeyType.Enter) {
                        if (userInput.toString().trim().length() > 0) {
                            canSubmit = true;
                        }
                    }
                }
                screen.refresh();
            } while (!canSubmit);

            if (userInput.charAt(0) == 'h' || userInput.charAt(0) == 'H') return TypesDirection.HORIZONTAL;
            else if (userInput.charAt(0) == 'v' || userInput.charAt(0) == 'V') return TypesDirection.VERTICAL;
            else throw new GameException("Podany znak: '" + userInput.charAt(0) + "' nie jest poprawny");

        } catch (GameException | StringIndexOutOfBoundsException e) {
            TextView.printError(e.getMessage());
            return readDirection(screen, terminal, message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}