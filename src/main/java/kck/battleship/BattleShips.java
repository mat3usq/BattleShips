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


//Scanner sc = new Scanner(System.in);
//        Game game;
//        String name = "";
//        int opt;
//        boolean hasName = false;
//
//        try {
//            do {
//                opt = Display.printMenu();
//
//                switch (opt) {
//                    case 1 -> {
//                        if (!hasName) {
//                            System.out.print("\nWprowadź swoje imię: ");
//                            name = sc.next();
//                            hasName = true;
//                        }
//
//                        game = new Game(name);
//                        game.run();
//                    }
//                    case 2 -> {
//                        game = new Game();
//                        game.run();
//                    }
//                    case 3 -> {
//                        Display.printRules();
//                    }
//                    case 4 -> {
//                        Display.printRanking();
//                    }
//                }
//            } while (opt != 0);
//        } catch (InputMismatchException | PositionException e) {
//        }
//        Display.printCredits();
