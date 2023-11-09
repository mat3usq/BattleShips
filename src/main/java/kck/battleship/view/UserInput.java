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
import kck.battleship.model.clases.Ship;
import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.clases.Position;

import java.io.IOException;

public class UserInput {
    public static void getMovedShipPosition(Ship ship, Terminal terminal, BattleField battleField) throws IOException {
        KeyStroke keyStroke;
        boolean canSubmit = true;

        try {
            if (ship.getPosition() == null) {
                ship.setPosition(new Position(0, 0));
                ship.setDirection(TypesDirection.VERTICAL);
            } else
                ship.setPosition(new Position(ship.getPosition().getColumn(), ship.getPosition().getRow()));
        } catch (GameException e) {
            throw new RuntimeException(e);
        }

        int x = ship.getLength();
        int y = 1;

        do {
            TextView.printBoardWithFutureShip(battleField, ship);
            keyStroke = terminal.pollInput();

            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Character) {
                    char c = keyStroke.getCharacter();
                    if (c == 'r' && ship.getPosition().getColumn() <= 10 - ship.getLength() && ship.getPosition().getRow() <= 10 - ship.getLength())
                        if (ship.getDirection() == TypesDirection.HORIZONTAL) {
                            ship.setDirection(TypesDirection.VERTICAL);
                            x = ship.getLength();
                            y = 1;
                        } else {
                            ship.setDirection(TypesDirection.HORIZONTAL);
                            x = 1;
                            y = ship.getLength();
                        }
                } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                    if (ship.getPosition().getColumn() != 0) {
                        ship.getPosition().setColumn(ship.getPosition().getColumn() - 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                    if (ship.getPosition().getColumn() < 10 - x) {
                        ship.getPosition().setColumn(ship.getPosition().getColumn() + 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                    if (ship.getPosition().getRow() < 10 - y) {
                        ship.getPosition().setRow(ship.getPosition().getRow() + 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                    if (ship.getPosition().getRow() != 0) {
                        ship.getPosition().setRow(ship.getPosition().getRow() - 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.Enter) {
                    try {
                        ship.setPosition(new Position(ship.getPosition().getColumn(), ship.getPosition().getRow()));
                    } catch (GameException e) {
                        throw new RuntimeException(e);
                    }
                    canSubmit = false;
                }
            }
        }
        while (canSubmit);
    }

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

    public static Position readPositionToShot(Terminal terminal, BattleField defenderBattleField) {
        KeyStroke keyStroke;
        Position shoot;
        boolean canSubmit = true;
        try {
            shoot = new Position(4, 4);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }

        do {
            TextView.printAim(shoot, defenderBattleField);

            try {
                keyStroke = terminal.pollInput();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                    if (shoot.getRow() != 0) {
                        shoot.setRow(shoot.getRow() - 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                    if (shoot.getRow() < 9) {
                        shoot.setRow(shoot.getRow() + 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                    if (shoot.getColumn() < 9) {
                        shoot.setColumn(shoot.getColumn() + 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                    if (shoot.getColumn() != 0) {
                        shoot.setColumn(shoot.getColumn() - 1);
                    }
                } else if (keyStroke.getKeyType() == KeyType.Enter) {
                    canSubmit = false;
                }
            }
        }
        while (canSubmit);

        return shoot;
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
            TextView.printError("Błąd: " + userInput + ", wybierz od a1 do j10");
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