package kck.battleship.controller;

import kck.battleship.model.clases.Player;
import kck.battleship.model.clases.Position;
import kck.battleship.model.clases.Ranking;
import kck.battleship.model.types.TypesField;
import kck.battleship.view.textView.UserInput;
import kck.battleship.view.View;

import java.io.IOException;
import java.util.Date;

public class Game {
    private final Player firstPlayer;
    private final Ranking firstPlayerRank;
    private final Player secondPlayer;
    private final View view = ViewController.getInstance();

    public Game(String name) {
        firstPlayer = new Player(name);
        firstPlayer.getShop();
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

        if (firstPlayer.isAI() && secondPlayer.isAI())
            playGame(firstPlayer, secondPlayer);
        else
            playGameHumanVsAI(firstPlayer, secondPlayer);

        if (!firstPlayer.isAI())
            firstPlayerRank.save();

        printResultGame();

        view.printMenuPage(0);
        view.chooseOption(0);
    }

    private void playGame(Player player1, Player player2){
        while (playTurn(player1, player2, false) && playTurn(player2, player1, true)) {
        }
    }

    private void playGameHumanVsAI(Player humanPlayer, Player aiPlayer){
        while (playTurn(humanPlayer, aiPlayer, false) && playTurn(aiPlayer, humanPlayer, false)) {
        }
    }

    private boolean playTurn(Player attacker, Player defender, Boolean reverse) {
        Position shoot = null;
        boolean isHit, isAddHit;

        if(attacker.isAI() && defender.isAI())
            view.showOptionToSimulatedGame();
        else
            view.showOptionToPlay();

        if (attacker.areShipsStillSailing()) {
            if (defender.getDurabilityForceField() > 0) {
                defender.setDurabilityForceField(defender.getDurabilityForceField() - 1);
                view.printBarrier(defender);
            } else {
                do {
                    try {
                        shoot = attacker.shoot(defender.getBattleField().getbattleFieldHideShips());
                        isAddHit = defender.addShoot(shoot);
                    } catch (GameException e) {
                        if (!attacker.isAI()) view.printError(e.getMessage());
                        isAddHit = false;
                    }
                } while (!isAddHit);

                isHit = defender.getBattleField().at(shoot) == TypesField.HIT.name;

                if (isHit) {
                    attacker.registerShoot(shoot);
                    updatePlayerPoints(attacker);
                }

                view.printShot(attacker, shoot, isHit);
            }

            delayForGameplay();

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

    private void updatePlayerPoints(Player player) {
        if (!player.isAI()) {
            long diff = (new Date().getTime() - player.getLastShootTime().getTime()) / 1000;
            firstPlayerRank.addPoints((int) (100 / diff));
            player.setLastShootTime(new Date());
        }
    }

    private void delayForGameplay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
    }

    private void addAllShips() throws IOException, InterruptedException {
        if (bothPlayersAreAI()) {
            addShipsForAIPlayers();
        } else {
            if (UserInput.question("Czy chcesz losowo rozmiescic statki (y/n)?"))
                firstPlayer.randAddShips();
            else
                firstPlayer.addShips();

            secondPlayer.addShips();
        }
        view.printBoards(firstPlayer, secondPlayer);
    }

    private boolean bothPlayersAreAI() {
        return firstPlayer.isAI() && secondPlayer.isAI();
    }

    private void addShipsForAIPlayers() throws IOException, InterruptedException {
        firstPlayer.addShips();
        secondPlayer.addShips();
    }

    private void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft())
            view.printWinner(firstPlayer, firstPlayerRank);
        else
            view.printWinner(secondPlayer, firstPlayerRank);
    }
}
