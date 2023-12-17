package kck.battleship.view.graphicView;

import kck.battleship.model.clases.*;
import kck.battleship.view.View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GraphicView extends View {
    private static String name;
    private LoginScreen loginScreen;
    private HomeScreen homeScreen;

    @Override
    public void printHomePage() {
        homeScreen = new HomeScreen();
    }

    @Override
    public void printLoginPage() {
        loginScreen = new LoginScreen();
    }

    @Override
    public void waitForKeyHomePage() {
        homeScreen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        printLoginPage();
                        homeScreen.setVisible(false);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        printExit();
                        homeScreen.dispose();
                        System.exit(0);
                        break;
                }
            }
        });
    }

    @Override
    public void printShipImage() {
    }

    @Override
    public void printTitle() {

    }

    @Override
    public void printMenu() {

    }

    @Override
    public void printMenuPage(int selected) {
    }

    @Override
    public void printExit() {

    }

    @Override
    public void chooseOption(int selected){

    }

    @Override
    public void option(int selected) {

    }

    @Override
    public void printRules()  {

    }

    @Override
    public void printInfoRules(int x) {

    }

    @Override
    public void printError(String message) {

    }

    @Override
    public void printShot(Player player, Position position, boolean isHit) {

    }

    @Override
    public void printWinner(Player player, Ranking rank) {

    }

    @Override
    public void printShip(Ship ship){

    }

    @Override
    public void printBoards(Player firstPlayer, Player secondPlayer) {

    }

    @Override
    public void printBoard(BattleField battleField){

    }

    @Override
    public void showOptionToManuallyAddShip(){

    }

    @Override
    public void showOptionToPlay() {

    }

    @Override
    public void showOptionToSimulatedGame() {

    }

    @Override
    public void printBoardWithFutureShip(BattleField battleField, Ship ship){

    }

    @Override
    public void printAim(Position shoot, BattleField battleField) {

    }

    @Override
    public void printRanking(int page){

    }

    @Override
    public void printShop(){

    }

    @Override
    public void printItemsInShop(int x) {

    }

    @Override
    public void printBarrier(Player defender) {

    }

    public static void setName(String name) {
        GraphicView.name = name;
    }
}
