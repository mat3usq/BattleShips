package kck.battleship.controller;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.exceptions.BoardException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.Player;
import kck.battleship.model.clases.Position;
import kck.battleship.model.clases.Ranking;
import kck.battleship.view.Display;
import kck.battleship.view.Input;

import java.io.IOException;
import java.util.Date;

public class Game {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Ranking firstPlayerRank;
    private Screen screen;
    private Terminal terminal;

    public Game(String name) {
        firstPlayer = new Player(name);
        firstPlayer.getShop();
        firstPlayerRank = new Ranking(firstPlayer, 0);
        secondPlayer = new Player("COMPUTER", true);
    }

    public Game() {
        firstPlayer = new Player("COMPUTER1", true);
        secondPlayer = new Player("COMPUTER2", true);
        firstPlayerRank = null;
    }

    public void run(Screen screen, Terminal terminal) throws PositionException, IOException, InterruptedException {
        this.screen = screen;
        this.terminal = terminal;

        addAllShips();
        Display.printBoards(firstPlayer, secondPlayer);

        if (firstPlayer.isAI() && secondPlayer.isAI())
            playGame(firstPlayer, secondPlayer);
        else
            playGameHumanVsAI(firstPlayer, secondPlayer);

        if (!firstPlayer.isAI())
            firstPlayerRank.save();

        printResultGame();

        Display.printMenuPage(0);
        Display.chooseOption(terminal, 0);
    }

    private void playGame(Player player1, Player player2) throws PositionException {
        while (playTurn(player1, player2, false) && playTurn(player2, player1, true)) {
        }
    }

    private void playGameHumanVsAI(Player humanPlayer, Player aiPlayer) throws PositionException {
        while (playTurn(humanPlayer, aiPlayer, false) && playTurn(aiPlayer, humanPlayer, false)) {
        }
    }

    private boolean playTurn(Player attacker, Player defender, Boolean reverse) throws PositionException {
        Position shoot = null;
        boolean isHit, isAddHit;

        if (attacker.areShipsStillSailing()) {

            if (defender.getDurabilityForceField() > 0) {
                defender.setDurabilityForceField(defender.getDurabilityForceField() - 1);
                TextGraphics tg = screen.newTextGraphics();
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(46, 15, "Computer nie trafil w ciebie!", SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
                if (defender.getDurabilityForceField() == 0)
                    tg.putString(50, 16, "Poniewaz miales bariere!", SGR.BOLD);
                else {
                    tg.putString(33, 16, "Poniewaz masz bariere jeszcze przez: " + defender.getDurabilityForceField(), SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    tg.putString(70, 16, String.valueOf(defender.getDurabilityForceField()), SGR.BOLD);
                    tg.putString(72, 16, "rund", SGR.BOLD);
                }
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                try {
                    screen.refresh();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                do {
                    try {
                        shoot = attacker.shoot(screen, terminal, defender.getBoard().getBoardHideShips());
                        isAddHit = defender.addShoot(shoot);
                    } catch (BoardException e) {
                        if (!attacker.isAI()) Display.printError("Błąd, już strzelałeś w tą pozycję!");
                        isAddHit = false;
                    }
                } while (!isAddHit);

                isHit = defender.getBoard().IsHit(shoot);

                if (isHit) {
                    attacker.registerShoot(shoot);
                    updatePlayerPoints(attacker);
                }

                Display.printShot(attacker, shoot, isHit);
            }

            delayForGameplay();

            if (attacker.isAI() && defender.isAI() && !reverse)
                Display.printBoards(attacker, defender);
            else if (attacker.isAI() && defender.isAI() && reverse)
                Display.printBoards(defender, attacker);
            else if (!attacker.isAI())
                Display.printBoards(attacker, defender);
            else if (!defender.isAI())
                Display.printBoards(defender, attacker);

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
        } catch (InterruptedException e) {
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
        Display.printBoards(firstPlayer, secondPlayer);
    }

    private boolean bothPlayersAreAI() {
        return firstPlayer.isAI() && secondPlayer.isAI();
    }

    private void addShipsForAIPlayers() throws IOException, InterruptedException {
        firstPlayer.addShips(screen, terminal);
        secondPlayer.addShips(screen, terminal);
    }

    private boolean shouldRandomlyArrangeShips() throws IOException, InterruptedException {
        return Input.question(screen, terminal, "Czy chcesz losowo rozmiescic swoje statki (y/n): ");
    }

    private void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft())
            Display.printWinner(firstPlayer, firstPlayerRank);
        else
            Display.printWinner(secondPlayer, firstPlayerRank);
    }
}
