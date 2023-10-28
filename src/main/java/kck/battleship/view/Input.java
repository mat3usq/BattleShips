package kck.battleship.view;


import kck.battleship.exceptions.DirectionException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.Board;
import kck.battleship.model.enum_.Direction;
import kck.battleship.model.clases.Position;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    public static Position readPosition(Scanner sc, Board board, String message) {
        try {
            System.out.print(message);
            String s = sc.nextLine().toLowerCase();
            char row = s.charAt(0);
            int column = Integer.parseInt(s.substring(1));
            Position.isInRange(row, column, board);
            return new Position(row, column - 1);
        } catch (PositionException | NumberFormatException | StringIndexOutOfBoundsException e) {
            Display.printError("Błąd, dozwolone wartości od A1 do " + Position.encode(board.getLength() - 1) + board.getLength());
            return readPosition(sc, board, message);
        }

    }

    public static Direction readDirection(Scanner sc, String message) {
        try {
            System.out.print(message);
            String s = sc.nextLine();
            return Direction.decode(s.charAt(0));
        } catch (DirectionException | StringIndexOutOfBoundsException e) {
            Display.printError("Błąd, dozwolone kierunki to 'h' lub 'v'");
            return readDirection(sc, message);
        }
    }

    public static int readOption(Scanner sc, String message) {
        try {
            System.out.print(message);
            return Integer.parseInt(sc.next());
        } catch (NumberFormatException | InputMismatchException e) {
            Display.printError("Błąd, wprowadź liczbę, aby kontynuować");
            return readOption(sc, message);
        }
    }

    public static boolean randAddShips(Scanner sc, String message) {
        System.out.print(message);
        String response = sc.next().toLowerCase();
        if (response.toLowerCase().equals("y"))
            return true;
        else if (response.toLowerCase().equals("n"))
            return false;

        Display.printError("Błąd, wprowadź poprawna litere, aby kontynuować");
        return randAddShips(sc, message);
    }
}