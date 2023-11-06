package kck.battleship;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import kck.battleship.exceptions.PositionException;
import kck.battleship.view.TextView;

import java.io.IOException;

public class BattleShips {
    public static void main(String[] args) throws IOException, PositionException, InterruptedException {
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