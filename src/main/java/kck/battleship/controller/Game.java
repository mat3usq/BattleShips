package kck.battleship.controller;

import kck.battleship.exceptions.BoardException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.Player;
import kck.battleship.model.clases.Position;
import kck.battleship.model.clases.Ranking;
import kck.battleship.view.Display;
import kck.battleship.view.Input;

import java.util.Date;
import java.util.Scanner;

public class Game {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Ranking firstPlayerRank;

    public Game(String name) {
        firstPlayer = new Player(name);
        firstPlayerRank = new Ranking(firstPlayer, 0);
        secondPlayer = new Player("COMPUTER", true);
    }

    public Game() {
        firstPlayer = new Player("COMPUTER" + "1", true);
        secondPlayer = new Player("COMPUTER" + "2", true);
        firstPlayerRank = null;
    }

    private boolean turn(Player attack, Player defend, Boolean reverse) throws PositionException {
        Position shoot = null;
        boolean isHit, isAddHit;
        if (attack.hasShipsLive()) {
            do {
                try {
                    shoot = attack.shoot(defend.getBoard().getBoardHideShips());
                    isAddHit = defend.addShoot(shoot);
                } catch (BoardException e) {
                    if (!attack.isAI()) Display.printError("Błąd, już strzelałeś w tą pozycję!");
                    isAddHit = false;
                }
            } while (!isAddHit);
            isHit = defend.getBoard().thereIsHit(shoot);

            if (isHit) {
                attack.registerShoot(shoot);
                if (!attack.isAI()) {
                    long diff = (new Date().getTime() - attack.getLastShootTime().getTime()) / 1000;
                    firstPlayerRank.setPoints((int) (100 / diff));
                    attack.setLastShootTime(new Date());
                }
            }

            Display.printShot(attack, shoot, isHit);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            if (attack.isAI() && defend.isAI() && !reverse) Display.printAdjacentBoard(attack, defend);
            else if (attack.isAI() && defend.isAI() && reverse) Display.printAdjacentBoard(defend, attack);
            else if (!attack.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!defend.isAI()) Display.printAdjacentBoard(defend, attack);

            if (!attack.isAI() && !defend.isAI()) try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return true;
        } else return false;
    }

    private void addAllShips() throws PositionException {
        if (firstPlayer.isAI() && secondPlayer.isAI()) {
            firstPlayer.addAllShips();
            secondPlayer.addAllShips();
        } else if (Input.randAddShips(new Scanner(System.in), "\nCzy chcesz losowo rozmiescic swoje statki(y/n): ")) {
            firstPlayer.randAddAllShips();
            secondPlayer.addAllShips();
            Display.printAdjacentBoard(firstPlayer, secondPlayer);
        } else {
            firstPlayer.addAllShips();
            secondPlayer.addAllShips();
        }
    }

    private void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft()) Display.printWinner(firstPlayer, firstPlayerRank);
        else Display.printWinner(secondPlayer, firstPlayerRank);
    }

    public void run() throws PositionException {
        addAllShips();
        if (firstPlayer.isAI() && secondPlayer.isAI()) {
            while (turn(firstPlayer, secondPlayer, false) && turn(secondPlayer, firstPlayer, true)) {
            }
        } else
            while (turn(firstPlayer, secondPlayer, false) && turn(secondPlayer, firstPlayer, false)) {
            }
        printResultGame();
        firstPlayerRank.save();
    }
}
