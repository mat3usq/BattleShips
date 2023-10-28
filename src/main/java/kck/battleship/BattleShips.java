package kck.battleship;

import kck.battleship.controller.Game;
import kck.battleship.exceptions.PositionException;
import kck.battleship.view.Display;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game;
        String name = "";
        int opt;
        boolean hasName = false;

        try {
            do {
                opt = Display.printMenu();

                switch (opt) {
                    case 1 -> {
                        if (!hasName) {
                            System.out.print("\nWprowadź swoje imię: ");
                            name = sc.next();
                            hasName = true;
                        }

                        game = new Game(name);
                        game.run();
                    }
                    case 2 -> {
                        game = new Game();
                        game.run();
                    }
                    case 3 -> {
                        Display.printRules();
                    }
                    case 4 -> {
                        Display.printRanking();
                    }
                }
            } while (opt != 0);
        } catch (InputMismatchException | PositionException e) {
        }
        Display.printCredits();
    }
}
