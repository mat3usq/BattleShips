package kck.battleship;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import kck.battleship.exceptions.PositionException;
import kck.battleship.view.Display;

import java.io.IOException;

public class BattleShips {
    public static void main(String[] args) throws IOException, PositionException, InterruptedException {
//        TerminalSize terminalSize = new TerminalSize(80, 24); // Tworzy obiekt TerminalSize z odpowiednimi wymiarami
//        Terminal terminal = new DefaultTerminalFactory()
//                .setInitialTerminalSize(terminalSize) // Ustawia początkowy rozmiar terminala
//                .createTerminal();
//        Screen screen = new TerminalScreen(terminal);
//        screen.startScreen();
//        Display display = new Display(screen);

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        new Display(screen);

        Display.printHomePage();
        Display.waitForKeyHomePage(terminal);
        Display.chooseOption(terminal, 0);
    }
}