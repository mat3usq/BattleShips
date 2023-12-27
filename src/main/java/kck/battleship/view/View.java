package kck.battleship.view;

import kck.battleship.controller.GameException;
import kck.battleship.model.clases.*;

import java.io.IOException;

public abstract class View {
    public abstract void printHomePage();

    public abstract void printShipImage();

    public abstract void printTitle();

    public abstract void printMenu();

    public abstract void waitForKeyHomePage() throws IOException;

    public abstract void printLoginPage();

    public abstract void printMenuPage(int selected);

    public abstract void printExit();

    public abstract void chooseOption(int selected) throws IOException, GameException, InterruptedException;

    public abstract void option(int selected) throws IOException, GameException, InterruptedException;

    public abstract void printRules() throws IOException, GameException, InterruptedException;

    public abstract void printInfoRules(int x);

    public abstract void printError(String message);

    public abstract void printShot(Player player, Position position, boolean isHit);

    public abstract void printWinner(Player player, Ranking rank);

    public abstract void printShip(Ship ship) throws IOException;

    public abstract void printBoards(Player firstPlayer, Player secondPlayer);

    public abstract void printBoard(BattleField battleField) throws IOException;

    public abstract void showOptionToManuallyAddShip() throws IOException;

    public abstract void showOptionToPlay();

    public abstract void showOptionToSimulatedGame();

    public abstract void printBoardWithFutureShip(BattleField battleField, Ship ship) throws IOException;

    public abstract void printAim(Position shoot, BattleField battleField);

    public abstract void printRanking(int page) throws IOException, GameException, InterruptedException;

    public abstract void printShop() throws IOException, GameException, InterruptedException;

    public abstract void printItemsInShop(int x);

    public abstract void printBarrier(Player defender);

    public abstract boolean isRandomShipsArranged();

    public abstract void addShipsVisually(BattleField battleField, Ship ship);
}
