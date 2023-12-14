package kck.battleship;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import kck.battleship.controller.GameException;
import kck.battleship.controller.ViewController;
import kck.battleship.view.TextView;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) throws IOException, InterruptedException, GameException {
        System.out.println("Wybierz widok:");
        System.out.println("1. Widok tekstowy");
        System.out.println("1. Widok graficzny");
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();
        new ViewController(x);
    }
}