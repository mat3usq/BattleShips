package kck.battleship;


import kck.battleship.controller.ViewController;

import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) {
        System.out.println("Wybierz rodzaj wyswietlania gry:");
        System.out.println("1. Interfejs Graficzny");
        System.out.println("2. Interfejs tekstowy");
//        new ViewController(new Scanner(System.in).nextInt());
        new ViewController(2);
    }
}