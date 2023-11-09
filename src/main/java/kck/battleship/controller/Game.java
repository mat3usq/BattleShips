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

import java.io.IOException;
import java.util.Date;

public class Game {
    private final Player firstPlayer;
    private final Ranking firstPlayerRank;
    private final Player secondPlayer;
    private Screen screen;
    private Terminal terminal;

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
        TextView.printBoards(firstPlayer, secondPlayer);

        if (firstPlayer.isAI() && secondPlayer.isAI())
            playGame(firstPlayer, secondPlayer);
        else
            playGameHumanVsAI(firstPlayer, secondPlayer);

        if (!firstPlayer.isAI())
            firstPlayerRank.save();

        printResultGame();

        TextView.printMenuPage(0);
        TextView.chooseOption(terminal, 0);
    }

    private void playGame(Player player1, Player player2) throws GameException {
        while (playTurn(player1, player2, false) && playTurn(player2, player1, true)) {
        }
    }

    private void playGameHumanVsAI(Player humanPlayer, Player aiPlayer) throws GameException {
        while (playTurn(humanPlayer, aiPlayer, false) && playTurn(aiPlayer, humanPlayer, false)) {
        }
    }

    private boolean playTurn(Player attacker, Player defender, Boolean reverse) {
        Position shoot = null;
        boolean isHit, isAddHit;

        TextView.showOptionToPlay();

        if (attacker.areShipsStillSailing()) {
            if (defender.getDurabilityForceField() > 0) {
                defender.setDurabilityForceField(defender.getDurabilityForceField() - 1);
                TextGraphics tg = screen.newTextGraphics();
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(46, 15, "Wróg nie trafil w ciebie!", SGR.BOLD);
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
//                        shoot = attacker.shoot(terminal, defender.getBattleField().getbattleFieldHideShips());
                        shoot = attacker.shoot(terminal, defender.getBattleField());
                        isAddHit = defender.addShoot(shoot);
                    } catch (GameException e) {
                        if (!attacker.isAI()) TextView.printError(e.getMessage());
                        isAddHit = false;
                    }
                } while (!isAddHit);

                isHit = defender.getBattleField().at(shoot) == TypesField.HIT.name;

                if (isHit) {
                    attacker.registerShoot(shoot);
                    updatePlayerPoints(attacker);
                }

                TextView.printShot(attacker, shoot, isHit);
            }

            delayForGameplay();

            if (attacker.isAI() && defender.isAI() && !reverse)
                TextView.printBoards(attacker, defender);
            else if (attacker.isAI() && defender.isAI() && reverse)
                TextView.printBoards(defender, attacker);
            else if (!attacker.isAI())
                TextView.printBoards(attacker, defender);
            else if (!defender.isAI())
                TextView.printBoards(defender, attacker);

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
        TextView.printBoards(firstPlayer, secondPlayer);
    }

    private boolean bothPlayersAreAI() {
        return firstPlayer.isAI() && secondPlayer.isAI();
    }

    private void addShipsForAIPlayers() throws IOException, InterruptedException {
        firstPlayer.addShips(screen, terminal);
        secondPlayer.addShips(screen, terminal);
    }

    private boolean shouldRandomlyArrangeShips() throws IOException, InterruptedException {
        return UserInput.question(screen, terminal, "Czy chcesz losowo rozmiescic swoje statki (y/n): ");
    }

    private void printResultGame() {
        if (firstPlayer.shipsLeft() > secondPlayer.shipsLeft())
            TextView.printWinner(firstPlayer, firstPlayerRank);
        else
            TextView.printWinner(secondPlayer, firstPlayerRank);
    }
}
