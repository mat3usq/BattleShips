package kck.battleship.controller;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.model.clases.Player;
import kck.battleship.model.clases.Position;
import kck.battleship.model.clases.Ranking;
import kck.battleship.model.types.TypesField;
import kck.battleship.view.TextView;
import kck.battleship.view.UserInput;
import kck.battleship.view.View;

import java.io.IOException;
import java.util.Date;

public class Game {
    private final Player firstPlayer;
    private final Ranking firstPlayerRank;
    private final Player secondPlayer;
    private Screen screen;
    private Terminal terminal;

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

    public void run(Screen screen, Terminal terminal) throws GameException, IOException, InterruptedException {
        this.screen = screen;
        this.terminal = terminal;

        addAllShips();

        if (firstPlayer.isAI() && secondPlayer.isAI())
            playGame(firstPlayer, secondPlayer);
        else
            playGameHumanVsAI(firstPlayer, secondPlayer);

        if (!firstPlayer.isAI())
            firstPlayerRank.save();

        printResultGame();

        view.printMenuPage(0);
        view.chooseOption(terminal, 0);
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
                try {
                    screen.refresh();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                do {
                    try {
                        shoot = attacker.shoot(terminal, defender.getBattleField().getbattleFieldHideShips());
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
            if (shouldRandomlyArrangeShips())
                firstPlayer.randAddShips();
            else
                firstPlayer.addShips(screen, terminal);

            secondPlayer.addShips(screen, terminal);
        }
        view.printBoards(firstPlayer, secondPlayer);
    }

    private boolean bothPlayersAreAI() {
        return firstPlayer.isAI() && secondPlayer.isAI();
    }

    private void addShipsForAIPlayers() throws IOException, InterruptedException {
        firstPlayer.addShips(screen, terminal);
        secondPlayer.addShips(screen, terminal);
    }

    private boolean shouldRandomlyArrangeShips() throws IOException, InterruptedException {
        TextGraphics tg = screen.newTextGraphics();
        for (int i = 9; i <22; i++)
            tg.putString(1, i, " ".repeat(100), SGR.BOLD);
        return UserInput.question(screen, terminal, "Czy chcesz losowo rozmiescic statki (y/n)?");
    }

    private void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft())
            view.printWinner(firstPlayer, firstPlayerRank);
        else
            view.printWinner(secondPlayer, firstPlayerRank);
    }
}
