package kck.battleship.view.textView;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.controller.GameException;
import kck.battleship.controller.ViewController;
import kck.battleship.model.clases.BattleField;
import kck.battleship.model.clases.Ship;
import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.clases.Position;
import kck.battleship.view.View;

import java.io.IOException;

public class UserInput {
    private static final View view = ViewController.getInstance();
    private static final Screen screen = TextView.getScreen();
    private static final Terminal terminal = TextView.getTerminal();

    public static void getMovedShipPosition(Ship ship, BattleField battleField) throws IOException {
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
            view.printBoardWithFutureShip(battleField, ship);
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

    public static String getUserInput(String message) throws IOException, InterruptedException, GameException {
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
                }
            }

            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            if (message.length() > 40)
                tg.putString(14, 16, message, SGR.BOLD);
            else
                tg.putString(22, 16, message, SGR.BOLD);

            tg.putString(27, 18, "NICK: ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(33, 18, String.valueOf(userInput), SGR.BOLD);
            tg.putString(33 + String.valueOf(userInput).length(), 18, "|  ", SGR.BOLD, SGR.BLINK);

            screen.refresh();

            Thread.sleep(10);
        } while (!canSubmit);

        tg.putString(33 + String.valueOf(userInput).length(), 18, "  ");
        screen.refresh();

        return userInput.toString();
    }

    public static boolean question(String message) throws IOException, InterruptedException {
        TextGraphics tg = screen.newTextGraphics();
        for (int i = 9; i <22; i++)
            tg.putString(1, i, " ".repeat(100), SGR.BOLD);

        char userResponse = '\0';
        KeyStroke keyStroke;

        do {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            keyStroke = terminal.pollInput();

            if (keyStroke != null)
                if (keyStroke.getKeyType() == KeyType.Character) {
                    char c = Character.toLowerCase(keyStroke.getCharacter());

                    if (c == 'y' || c == 'n')
                        userResponse = c;
                } else if (keyStroke.getKeyType() == KeyType.Escape) {
                    try {
                        view.printShop();
                    } catch (GameException e) {
                        throw new RuntimeException(e);
                    }
                }

            tg.putString(20, 11, message, SGR.BOLD);
            tg.putString(26, 13, "Odpowiedź: ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(37, 13, String.valueOf((userResponse == '\0' ? "" : userResponse)), SGR.BOLD);
            if (userResponse == '\0')
                tg.putString(37, 13, "|  ", SGR.BOLD, SGR.BLINK);
            else
                tg.putString(38, 13, "|  ", SGR.BOLD, SGR.BLINK);
            screen.refresh();
            Thread.sleep(10);
        } while (userResponse == '\0');
        return userResponse == 'y';
    }

    public static Position readPositionToShot(BattleField defenderBattleField) {
        KeyStroke keyStroke;
        Position shoot;
        boolean canSubmit = true;
        try {
            shoot = new Position(4, 4);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }

        do {
            view.printAim(shoot, defenderBattleField);

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
}