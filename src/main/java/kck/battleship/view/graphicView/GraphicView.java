package kck.battleship.view.graphicView;

import kck.battleship.model.clases.*;
import kck.battleship.view.View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GraphicView extends View {
    private int selected = 0;
    private final int sizeOptions = 6;
    private String name;
    private HomeScreen homeScreen;
    private LoginScreen loginScreen;
    private MainScreen mainScreen;
    private boolean listen = true;

    @Override
    public void printHomePage() {
        homeScreen = new HomeScreen();
    }

    @Override
    public void printLoginPage() {
        loginScreen = new LoginScreen();
        loginScreen.play.addActionListener(e -> {
            if (!loginScreen.nicknameField.getText().isEmpty()) {
                name = loginScreen.nicknameField.getText();
                loginScreen.setVisible(false);
                mainScreen = new MainScreen();
                printMenuPage(0);

                addMenuActionsListeners();
                addMenuKeyListeners();
            }
        });

        loginScreen.exit.addActionListener(e -> {
            homeScreen.dispose();
            loginScreen.dispose();
            System.exit(0);
        });
    }

    private void addMenuActionsListeners() {
        mainScreen.playGame.addActionListener(ev -> {
            mainScreen.requestFocusInWindow();
            printMenuPage(0);
        });

        mainScreen.simulateGame.addActionListener(ev -> {
            mainScreen.requestFocusInWindow();
            printMenuPage(1);
        });

        mainScreen.shop.addActionListener(ev -> {
            listen = false;
            mainScreen.requestFocusInWindow();
            printMenuPage(2);
            printShop();
        });

        mainScreen.rules.addActionListener(ev -> {
            mainScreen.requestFocusInWindow();
            printMenuPage(3);
        });

        mainScreen.ranking.addActionListener(ev -> {
            mainScreen.requestFocusInWindow();
            printMenuPage(4);
        });

        mainScreen.exit.addActionListener(ev -> {
            mainScreen.requestFocusInWindow();
            printMenuPage(5);
            printExit();
            mainScreen.dispose();
            loginScreen.dispose();
            homeScreen.dispose();
            System.exit(0);
        });
    }

    private void addMenuKeyListeners() {
        mainScreen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
               if(listen)
               {
                   switch (e.getKeyCode()) {
                       case KeyEvent.VK_ENTER:
                           option(selected);
                           break;
                       case KeyEvent.VK_ESCAPE:
                           printExit();
                           mainScreen.dispose();
                           loginScreen.dispose();
                           homeScreen.dispose();
                           System.exit(0);
                           break;
                       case KeyEvent.VK_DOWN:
                           if (selected + 1 < sizeOptions)
                               printMenuPage(++selected);
                           break;
                       case KeyEvent.VK_UP:
                           if (selected - 1 >= 0)
                               printMenuPage(--selected);
                           break;
                   }
               }
            }
        });
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
        this.selected = selected;
        mainScreen.leftLabel.setVisible(true);
        mainScreen.rightLabel.setVisible(true);
        mainScreen.upLabel.setVisible(false);
        switch (selected) {
            case 0:
                mainScreen.leftLabel.setBounds(185, 220, 30, 30);
                mainScreen.rightLabel.setBounds(385, 220, 30, 30);
                break;
            case 1:
                mainScreen.leftLabel.setBounds(185, 320, 30, 30);
                mainScreen.rightLabel.setBounds(385, 320, 30, 30);
                break;
            case 2:
                mainScreen.leftLabel.setBounds(185, 430, 30, 30);
                mainScreen.rightLabel.setBounds(385, 430, 30, 30);
                break;
            case 3:
                mainScreen.leftLabel.setBounds(185, 550, 30, 30);
                mainScreen.rightLabel.setBounds(385, 550, 30, 30);
                break;
            case 4:
                mainScreen.upLabel.setVisible(true);
                mainScreen.leftLabel.setVisible(false);
                mainScreen.rightLabel.setVisible(false);
                mainScreen.upLabel.setBounds(520, 120, 30, 30);
                break;
            case 5:
                mainScreen.leftLabel.setBounds(185, 670, 30, 30);
                mainScreen.rightLabel.setBounds(385, 670, 30, 30);
                break;
        }
    }

    @Override
    public void printExit() {

    }

    @Override
    public void chooseOption(int selected) {

    }

    @Override
    public void option(int selected) {
        switch(selected) {
            case 0 -> {
//                if (name != null) {
//                    Game game = new Game(name);
//                    game.run();
//                }
            }
            case 1 -> {
//                Game game = new Game();
//                game.run();
            }
            case 2 -> {
                printShop();
            }
            case 3 -> {
                printRanking(0);
            }
            case 4 -> {
                printRules();
            }
            case 5 -> {
                printExit();
                mainScreen.dispose();
                loginScreen.dispose();
                homeScreen.dispose();
                System.exit(0);
            }
        }
    }

    @Override
    public void printRules() {

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
    public void printShip(Ship ship) {

    }

    @Override
    public void printBoards(Player firstPlayer, Player secondPlayer) {

    }

    @Override
    public void printBoard(BattleField battleField) {

    }

    @Override
    public void showOptionToManuallyAddShip() {

    }

    @Override
    public void showOptionToPlay() {

    }

    @Override
    public void showOptionToSimulatedGame() {

    }

    @Override
    public void printBoardWithFutureShip(BattleField battleField, Ship ship) {

    }

    @Override
    public void printAim(Position shoot, BattleField battleField) {

    }

    @Override
    public void printRanking(int page) {

    }

    @Override
    public void printShop() {
        shopButtons();
    }

    private void shopButtons(){
        mainScreen.playGame.setVisible(false);
        mainScreen.simulateGame.setVisible(false);
        mainScreen.shop.setVisible(false);
        mainScreen.rules.setVisible(false);
        mainScreen.exit.setVisible(false);
        mainScreen.ranking.setVisible(false);
        mainScreen.menuTitle.setVisible(false);
        mainScreen.upLabel.setVisible(false);
        mainScreen.leftLabel.setVisible(false);
        mainScreen.rightLabel.setVisible(false);

        mainScreen.backShop.setVisible(true);
        mainScreen.extraShip.setVisible(true);
        mainScreen.barrierShop.setVisible(true);
        mainScreen.shopTitle.setVisible(true);
    }

    @Override
    public void printItemsInShop(int x) {

    }

    @Override
    public void printBarrier(Player defender) {

    }
}
