package kck.battleship;

import kck.battleship.controller.GameException;
import kck.battleship.controller.ViewController;

import java.io.IOException;
import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) throws IOException, InterruptedException, GameException {
        System.out.println("Wybierz widok:");
        System.out.println("1. Widok tekstowy");
        System.out.println("2. Widok graficzny");
        new ViewController(new Scanner(System.in).nextInt());
    }
}