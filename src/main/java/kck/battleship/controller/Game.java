package kck.battleship.controller;

import kck.battleship.model.clases.Player;
import kck.battleship.model.clases.Position;
import kck.battleship.model.clases.Ranking;
import kck.battleship.model.types.TypesField;
import kck.battleship.view.textView.UserInput;
import kck.battleship.view.View;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {
    private static Player firstPlayer = null;
    private final Ranking firstPlayerRank;
    private static Player secondPlayer = null;
    private final View view = ViewController.getInstance();
    public static boolean hasExtraShip;

    public Game(String name) {
        firstPlayer = new Player(name);
        firstPlayer.getShop();
        hasExtraShip = firstPlayer.hasAirCrafter;
        firstPlayerRank = new Ranking(firstPlayer, 0);
        secondPlayer = new Player("Wróg", true);
    }

    public Game() {
        firstPlayer = new Player("Enemy", true);
        secondPlayer = new Player("Enemy2", true);
        firstPlayerRank = null;
    }

    public void run() throws GameException, IOException, InterruptedException {
        addAllShips();
        view.printBoards(firstPlayer, secondPlayer);

        if (firstPlayer.isAI() && secondPlayer.isAI())
            playGame(firstPlayer, secondPlayer);
        else
            playGameHumanVsAI(firstPlayer, secondPlayer);

        saveRanking();
        printResultGame();

        view.printMenuPage(0);
        view.chooseOption(0);
    }

    private void playGame(Player player1, Player player2) {
        while (playTurn(player1, player2, false) && playTurn(player2, player1, true)) {
        }
    }

    private void playGameHumanVsAI(Player humanPlayer, Player aiPlayer) {
        while (playTurn(humanPlayer, aiPlayer, false) && playTurn(aiPlayer, humanPlayer, false)) {
        }
    }

    public boolean playTurn(Player attacker, Player defender, Boolean reverse) {
        Position shoot;
        boolean isHit;

        if (attacker.isAI() && defender.isAI())
            view.showOptionToSimulatedGame();
        else
            view.showOptionToPlay();

        if (attacker.areShipsStillSailing()) {
            if (defender.getDurabilityForceField() > 0) {
                defender.setDurabilityForceField(defender.getDurabilityForceField() - 1);
                view.printBarrier(defender);
            } else {
                shoot = ViewController.getPositionToShot(defender, attacker);

                isHit = defender.getBattleField().at(shoot) == TypesField.HIT.name;

                if (isHit) {
//                    System.out.println(attacker.getName() + " game:" + shoot.getColumn() + " " + shoot.getRow());
                    attacker.registerShoot(shoot);
                    updatePlayerPoints(attacker);
                }

                view.printShot(attacker, shoot, isHit);
            }

            view.delayForGameplay();

            if (attacker.isAI() && defender.isAI() && !reverse)
                view.printBoards(attacker, defender);
            else if (attacker.isAI() && defender.isAI() && reverse)
                view.printBoards(defender, attacker);
            else if (!attacker.isAI())
                view.printBoards(attacker, defender);
            else if (!defender.isAI())
                view.printBoards(defender, attacker);

            return true;
        } else return false;
    }

    public void playRound(){
        boolean attacker = playTurn(Game.getFirstPlayer(), Game.getSecondPlayer(), false);
        AtomicBoolean defender = new AtomicBoolean(false);
        if (attacker) {
            Timer timer = new Timer(2000, e -> defender.set(playTurn(Game.getSecondPlayer(), Game.getFirstPlayer(), false)));
            timer.setRepeats(false);
            timer.start();
        }else{
            saveRanking();
            printResultGame();
        }

        if(defender.get()){

        }
    }

    private void updatePlayerPoints(Player player) {
        if (!player.isAI()) {
            long diff = (new Date().getTime() - player.getLastShootTime().getTime()) / 1000;
            firstPlayerRank.addPoints((int) (100 / diff));
            player.setLastShootTime(new Date());
        }
    }

    public void addAllShips() throws IOException, InterruptedException {
        if (bothPlayersAreAI()) {
            addShipsForAIPlayers();
        } else {
            boolean random = view.isRandomShipsArranged();
            if (random)
                firstPlayer.randAddShips();
            else
                firstPlayer.addShips();

            secondPlayer.addShips();

            if (random)
                view.printBoards(getFirstPlayer(), getSecondPlayer());
        }
    }

    private boolean bothPlayersAreAI() {
        return firstPlayer.isAI() && secondPlayer.isAI();
    }

    private void addShipsForAIPlayers() throws IOException {
        firstPlayer.addShips();
        secondPlayer.addShips();
    }

    public void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft())
            view.printWinner(firstPlayer, firstPlayerRank);
        else
            view.printWinner(secondPlayer, firstPlayerRank);
    }

    public void saveRanking(){
        if (!firstPlayer.isAI())
            firstPlayerRank.save();
    }

    public static Player getFirstPlayer() {
        return firstPlayer;
    }

    public static Player getSecondPlayer() {
        return secondPlayer;
    }
}
