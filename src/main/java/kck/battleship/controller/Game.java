package kck.battleship.controller;

import kck.battleship.exceptions.BoardException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.Player;
import kck.battleship.model.clases.Position;
import kck.battleship.view.Display;
import kck.battleship.view.Input;

import java.util.Scanner;

public class Game {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final String COMPUTER = "AI";


    public Game(String name) {
        firstPlayer = new Player(name);
        secondPlayer = new Player(COMPUTER, true);
    }

    public Game() {
        firstPlayer = new Player(COMPUTER + "1", true);
        secondPlayer = new Player(COMPUTER + "2", true);
    }

    private boolean turn(Player attack, Player defend) throws PositionException {
        Position shoot = null;
        boolean isHit, isAddHit;
        if (attack.hasShipsLive()) {
            do {
                try {
                    shoot = attack.shoot(defend.getBoard().getBoardHideShips());
                    isAddHit = defend.addShoot(shoot);
                } catch (BoardException e) {
                    if (!attack.isAI()) Display.printError("Błąd, już strzelałeś w tą pozycji!");
                    isAddHit = false;
                }
            } while (!isAddHit);
            isHit = defend.getBoard().thereIsHit(shoot);
            if (isHit) attack.registerShoot(shoot);

            Display.printShot(attack, shoot, isHit);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            if (attack.isAI() && defend.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!attack.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!defend.isAI()) Display.printAdjacentBoard(defend, attack);

            if (!attack.isAI() && !defend.isAI()) try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return true;
        } else return false;
    }

    private void addAllShips() {

        if (Input.randAddShips(new Scanner(System.in), "\nCzy chcesz losowo rozmiescic swoje statki(y/n): "))
        {
            firstPlayer.randAddAllShips();
            Display.printBoard(firstPlayer.getBoard());
        }
        else
            firstPlayer.addAllShips();

        secondPlayer.addAllShips();
    }

    private void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft()) Display.printWinner(firstPlayer);
        else Display.printWinner(secondPlayer);
    }

    public void run() throws PositionException {
        addAllShips();
        while (turn(firstPlayer, secondPlayer) && turn(secondPlayer, firstPlayer)) {
        }
        printResultGame();
    }
}
