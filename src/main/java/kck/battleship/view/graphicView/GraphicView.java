package kck.battleship.view.graphicView;

import kck.battleship.controller.Game;
import kck.battleship.controller.GameException;
import kck.battleship.model.clases.*;
import kck.battleship.view.View;
import kck.battleship.view.textView.UserInput;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphicView extends View {
    private int menuSelected = 0;
    private int shopSelected = 0;
    private final int sizeOptions = 6;
    private final int sizeOptionsShop = 2;
    public static String name;
    private HomeScreen homeScreen;
    private LoginScreen loginScreen;
    private MainScreen mainScreen;
    private GameScreen gameScreen;

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

                addShopActionsListeners();
                addShopKeyListeners();

                addShopPopupActionsListeners();
                addShopPopupKeyListeners();

                addRulesActionsListeners();
                addRulesKeyListeners();

                addRankingActionsListeners();
                addRankingKeyListeners();
            }
        });

        loginScreen.exit.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void addMenuActionsListeners() {
        mainScreen.menuPanel.playGame.addActionListener(ev -> {
            printMenuPage(0);
            option(0);
        });

        mainScreen.menuPanel.simulateGame.addActionListener(ev -> {
            printMenuPage(1);
            option(1);
        });

        mainScreen.menuPanel.shop.addActionListener(ev -> {
            printMenuPage(2);
            option(2);
        });

        mainScreen.menuPanel.rules.addActionListener(ev -> {
            printMenuPage(3);
            option(3);
        });

        mainScreen.menuPanel.ranking.addActionListener(ev -> {
            printMenuPage(4);
            option(4);
        });

        mainScreen.menuPanel.exit.addActionListener(ev -> {
            printMenuPage(5);
            option(5);
        });
    }

    private void addMenuKeyListeners() {
        mainScreen.menuPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        option(menuSelected);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                    case KeyEvent.VK_DOWN:
                        if (menuSelected + 1 < sizeOptions)
                            printMenuPage(++menuSelected);
                        break;
                    case KeyEvent.VK_UP:
                        if (menuSelected - 1 >= 0)
                            printMenuPage(--menuSelected);
                        break;
                }
            }
        });
    }

    private void addShopActionsListeners() {
        mainScreen.shopPanel.extraShip.addActionListener(ev -> {
            printShopPage(0);
            mainScreen.popup.setVisible(true);
            mainScreen.popup.requestFocusInWindow();
        });

        mainScreen.shopPanel.barrierShop.addActionListener(ev -> {
            printShopPage(1);
            mainScreen.popup.setVisible(true);
            mainScreen.popup.requestFocusInWindow();
        });

        mainScreen.shopPanel.backShop.addActionListener(ev -> {
            mainScreen.menuPanel.setVisible(true);
            mainScreen.shopPanel.setVisible(false);
            printMenuPage(2);
        });
    }

    private void addShopKeyListeners() {
        mainScreen.shopPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        mainScreen.popup.setVisible(true);
                        mainScreen.popup.requestFocusInWindow();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        mainScreen.menuPanel.setVisible(true);
                        mainScreen.shopPanel.setVisible(false);
                        printMenuPage(2);
                        break;
                    case KeyEvent.VK_DOWN:
                        if (shopSelected + 1 < sizeOptionsShop)
                            printShopPage(++shopSelected);
                        break;
                    case KeyEvent.VK_UP:
                        if (shopSelected - 1 >= 0)
                            printShopPage(--shopSelected);
                        break;
                }
            }
        });
    }

    private void addShopPopupActionsListeners() {
        mainScreen.popup.okButton.addActionListener(ev -> {
            buyItemInShop();
        });

        mainScreen.popup.cancelButton.addActionListener(ev -> {
            mainScreen.popup.setVisible(false);
        });
    }

    private void addShopPopupKeyListeners() {
        mainScreen.popup.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        buyItemInShop();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        mainScreen.popup.setVisible(false);
                        break;
                }
            }
        });
    }

    private void addRulesActionsListeners() {
        mainScreen.rules.backRules.addActionListener(ev -> {
            mainScreen.menuPanel.setVisible(true);
            mainScreen.rules.setVisible(false);
            printMenuPage(3);
        });
    }

    private void addRulesKeyListeners() {
        mainScreen.rules.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    mainScreen.menuPanel.setVisible(true);
                    mainScreen.rules.setVisible(false);
                    printMenuPage(3);
                }
            }
        });
    }

    private void addRankingActionsListeners() {
        mainScreen.ranking.backRanking.addActionListener(ev -> {
            mainScreen.menuPanel.setVisible(true);
            mainScreen.ranking.setVisible(false);
            printMenuPage(4);
        });
    }

    private void addRankingKeyListeners() {
        mainScreen.ranking.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    mainScreen.menuPanel.setVisible(true);
                    mainScreen.ranking.setVisible(false);
                    printMenuPage(4);
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
    public void printExit() {

    }

    @Override
    public void chooseOption(int selected) {

    }

    @Override
    public void option(int selected) {
        switch (selected) {
            case 0 -> {
                try {
                    mainScreen.setVisible(false);
                    gameScreen = new GameScreen();
                    Game game = new Game(name);
                    game.run();
                } catch (IOException | GameException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            case 1 -> {
//                Game game = new Game();
//                game.run();
            }
            case 2 -> {
                printShop();
                printShopPage(0);
            }
            case 3 -> {
                printRules();
            }
            case 4 -> {
                printRanking(0);
            }
            case 5 -> {
                printExit();
                System.exit(0);
            }
        }
    }

    @Override
    public void printMenuPage(int selected) {
        this.menuSelected = selected;
        mainScreen.menuPanel.requestFocusInWindow();
        mainScreen.menuPanel.upLabel.setVisible(false);
        mainScreen.menuPanel.leftLabel.setVisible(true);
        mainScreen.menuPanel.rightLabel.setVisible(true);
        switch (selected) {
            case 0:
                mainScreen.menuPanel.leftLabel.setBounds(185, 220, 30, 30);
                mainScreen.menuPanel.rightLabel.setBounds(385, 220, 30, 30);
                break;
            case 1:
                mainScreen.menuPanel.leftLabel.setBounds(185, 320, 30, 30);
                mainScreen.menuPanel.rightLabel.setBounds(385, 320, 30, 30);
                break;
            case 2:
                mainScreen.menuPanel.leftLabel.setBounds(185, 430, 30, 30);
                mainScreen.menuPanel.rightLabel.setBounds(385, 430, 30, 30);
                break;
            case 3:
                mainScreen.menuPanel.leftLabel.setBounds(185, 550, 30, 30);
                mainScreen.menuPanel.rightLabel.setBounds(385, 550, 30, 30);
                break;
            case 4:
                mainScreen.menuPanel.upLabel.setBounds(520, 120, 30, 30);
                mainScreen.menuPanel.upLabel.setVisible(true);
                mainScreen.menuPanel.leftLabel.setVisible(false);
                mainScreen.menuPanel.rightLabel.setVisible(false);
                break;
            case 5:
                mainScreen.menuPanel.leftLabel.setBounds(185, 670, 30, 30);
                mainScreen.menuPanel.rightLabel.setBounds(385, 670, 30, 30);
                break;
        }
    }

    private void printShopPage(int selected) {
        this.shopSelected = selected;
        switch (selected) {
            case 0:
                mainScreen.shopPanel.leftLabel.setBounds(125, 360, 30, 30);
                mainScreen.shopPanel.rightLabel.setBounds(445, 360, 30, 30);
                break;
            case 1:
                mainScreen.shopPanel.leftLabel.setBounds(105, 620, 30, 30);
                mainScreen.shopPanel.rightLabel.setBounds(490, 620, 30, 30);
                break;
        }
    }

    @Override
    public void printRules() {
        mainScreen.menuPanel.setVisible(false);
        mainScreen.rules.setVisible(true);
        mainScreen.rules.requestFocusInWindow();
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
        gameScreen.setVisible(true);
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
        mainScreen.ranking.setVisible(true);
        mainScreen.menuPanel.setVisible(false);
        mainScreen.ranking.requestFocusInWindow();
    }

    @Override
    public void printShop() {
        mainScreen.shopPanel.setVisible(true);
        mainScreen.menuPanel.setVisible(false);
        mainScreen.shopPanel.requestFocusInWindow();
    }

    @Override
    public void printItemsInShop(int x) {

    }

    @Override
    public void printBarrier(Player defender) {

    }

    private void buyItemInShop() {
        String s;
        mainScreen.popup.cancelButton.setVisible(false);
        mainScreen.popup.okButton.setVisible(false);
        mainScreen.popup.confrimLabel.setVisible(true);

        if (shopSelected == 0)
            s = Ranking.enoughPoints(name, 500, shopSelected, true);
        else
            s = Ranking.enoughPoints(name, 300, shopSelected, true);

        mainScreen.popup.confrimLabel.setText(Objects.requireNonNullElse(s, "Pomyslnie zakupiono wybrana rzecz!"));

        Timer timer = new Timer(2000, e -> {
            mainScreen.popup.setVisible(false);
            mainScreen.popup.confrimLabel.setVisible(false);
            mainScreen.popup.cancelButton.setVisible(true);
            mainScreen.popup.okButton.setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public boolean isRandomShipsArranged() {
        AtomicBoolean isOkPressed = new AtomicBoolean();

        gameScreen.popup.okButton.addActionListener(e -> {
            isOkPressed.set(true);
            gameScreen.popup.setVisible(false);
        });

        gameScreen.popup.cancelButton.addActionListener(e -> {
            isOkPressed.set(false);
            gameScreen.popup.setVisible(false);
        });

        gameScreen.popup.setVisible(true);

        return isOkPressed.get();
    }

    @Override
    public void addShipsVisually(BattleField battleField, Ship ship) {
//        printBoard(battleField);
//        printShip(ship);
//        UserInput.getMovedShipPosition(ship, battleField);
    }
}
