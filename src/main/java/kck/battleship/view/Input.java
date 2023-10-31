package kck.battleship.view;


import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.exceptions.DirectionException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.Board;
import kck.battleship.model.enum_.Direction;
import kck.battleship.model.clases.Position;

import java.io.IOException;

public class Input {

    public static Position readPosition(Screen screen, Terminal terminal, Board board, String message) {
        TextGraphics tg = screen.newTextGraphics();
        StringBuilder userInput = new StringBuilder();
        KeyStroke keyStroke;
        boolean canSubmit = false;
        tg.putString(2, 19, "Pozycja:     ");


        try {
            do {
                tg.putString(2, 18, message);
                tg.putString(2, 19, "Pozycja: " + userInput);
                Thread.sleep(10);

                keyStroke = terminal.pollInput();

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Backspace) {
                        if (userInput.length() > 0) {
                            userInput.deleteCharAt(userInput.length() - 1);
                            tg.putString(2, 19, "Pozycja: " + userInput + "  ");
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

            char row = userInput.charAt(0);
            int column = Integer.parseInt(userInput.substring(1));
            Position.isInRange(row, column, board);
            return new Position(row, column - 1);
        } catch (PositionException | NumberFormatException | StringIndexOutOfBoundsException e) {
            Display.printError("Błąd, dozwolone wartości od A1 do " + Position.encode(board.getLength() - 1) + board.getLength());
            return readPosition(screen, terminal, board, message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Direction readDirection(Screen screen, Terminal terminal, String message) {
        TextGraphics tg = screen.newTextGraphics();
        StringBuilder userInput = new StringBuilder();
        KeyStroke keyStroke;
        boolean canSubmit = false;
        tg.putString(2, 21, "Kierunek:     ");

        try {
            do {
                tg.putString(2, 20, message);
                tg.putString(2, 21, "Kierunek: " + userInput);
                Thread.sleep(10);

                keyStroke = terminal.pollInput();

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Backspace) {
                        if (userInput.length() > 0) {
                            userInput.deleteCharAt(userInput.length() - 1);
                            tg.putString(2, 21, "Kierunek: " + userInput + "  ");
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

            return Direction.decode(userInput.charAt(0));
        } catch (DirectionException | StringIndexOutOfBoundsException e) {
            Display.printError("Błąd, dozwolone kierunki to 'h' lub 'v'");
            return readDirection(screen, terminal, message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean question(Screen screen, Terminal terminal, String message) throws IOException, InterruptedException {
        char userResponse = '\0';
        TextGraphics tg = screen.newTextGraphics();
        KeyStroke keyStroke;

        do {
            keyStroke = terminal.pollInput();

            if (keyStroke != null)
                if (keyStroke.getKeyType() == KeyType.Character) {
                    char c = Character.toLowerCase(keyStroke.getCharacter());

                    if (c == 'y' || c == 'n')
                        userResponse = c;
                }

            tg.putString(15, 17, message);
            tg.putString(25, 19, "Odpowiedź: " + (userResponse == '\0' ? "" : userResponse));
            screen.refresh();
            Thread.sleep(10);
        } while (userResponse == '\0');
        return userResponse == 'y';
    }

    public static String getUserInput(Screen screen, Terminal terminal, String message) throws IOException, InterruptedException, PositionException {
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
                } else if (keyStroke.getKeyType() == KeyType.Escape)
                {
                    Display.printMenuPage(0);
                    Display.chooseOption(terminal, 0);
                    return null;
                }
            }

            screen.clear();
            tg.putString(6, 4, "  ____    _  _____ _____ _     _____   ____  _   _ ___ ____  ____");
            tg.putString(6, 5, " | __ )  / \\|_   _|_   _| |   | ____| / ___|| | | |_ _|  _ \\/ ___|");
            tg.putString(6, 6, " |  _ \\ / _ \\ | |   | | | |   |  _|   \\___ \\| |_| || || |_) \\___ \\ \n");
            tg.putString(6, 7, " | |_) / ___ \\| |   | | | |___| |___   ___) |  _  || ||  __/ ___) |\n");
            tg.putString(6, 8, " |____/_/   \\_\\_|   |_| |_____|_____| |____/|_| |_|___|_|   |____/ \n");

            tg.putString(19, 12, message);
            tg.putString(25, 14, "NICK: " + userInput);
            screen.refresh();

            Thread.sleep(10);
        } while (!canSubmit);

        return userInput.toString();
    }
}