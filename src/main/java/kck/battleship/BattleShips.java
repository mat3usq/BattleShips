package kck.battleship;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import kck.battleship.controller.GameException;
import kck.battleship.model.types.TypesField;
import kck.battleship.view.TextView;

import java.io.IOException;

public class BattleShips {
    public static void main(String[] args) throws IOException, GameException, InterruptedException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);
        new TextView(screen);

        TextView.printHomePage();
        TextView.waitForKeyHomePage(terminal);
        TextView.chooseOption(terminal, 0);
    }
}