package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainScreen extends JFrame {
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JLabel upLabel;
    public JLabel leftLabel;
    public JLabel rightLabel;
    public JLabel shopTitle;
    public JLabel menuTitle;
    public JButton playGame;
    public JButton simulateGame;
    public JButton shop;
    public JButton ranking;
    public JButton rules;
    public JButton exit;
    public JButton backShop;
    public JButton extraShip;
    public JButton barrierShop;

    public MainScreen() {
        super("Menu - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.requestFocusInWindow();
        this.setFocusable(true);
        this.setSize(600, 800);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        JPanel container = new JPanel(null);
        JPanelBG splashPanel = new JPanelBG(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/backgroundMenu.jpg")));
        ImageIcon titleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/title.png")));
        ImageIcon upArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowUp.png")));
        ImageIcon rightArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowRight.png")));
        ImageIcon leftArrow  = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowLeft.png")));
        menuTitle = new JLabel(titleImg);
        upLabel = new JLabel(upArrow);
        leftLabel = new JLabel(leftArrow);
        rightLabel = new JLabel(rightArrow);

        ImageIcon playGameImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/zagraj.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        playGame = new JButton(playGameImg);
//        play.setRolloverIcon(playViewImgHover);
        playGame.setBorder(null);
        playGame.setOpaque(false);
        playGame.setBorderPainted(false);
        playGame.setContentAreaFilled(false);
        playGame.setCursor(cursor);
        playGame.setText("playGame");

        ImageIcon simulateGameImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/symuluj.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        simulateGame = new JButton(simulateGameImg);
//        play.setRolloverIcon(playViewImgHover);
        simulateGame.setBorder(null);
        simulateGame.setOpaque(false);
        simulateGame.setBorderPainted(false);
        simulateGame.setContentAreaFilled(false);
        simulateGame.setCursor(cursor);
        simulateGame.setText("simulateGame");

        ImageIcon shopImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/sklep.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        shop = new JButton(shopImg);
//        play.setRolloverIcon(playViewImgHover);
        shop.setBorder(null);
        shop.setOpaque(false);
        shop.setBorderPainted(false);
        shop.setContentAreaFilled(false);
        shop.setCursor(cursor);
        shop.setText("simulateGame");

        ImageIcon rankingImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/ranking.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        ranking = new JButton(rankingImg);
//        play.setRolloverIcon(playViewImgHover);
        ranking.setBorder(null);
        ranking.setOpaque(false);
        ranking.setBorderPainted(false);
        ranking.setContentAreaFilled(false);
        ranking.setCursor(cursor);
        ranking.setText("ranking");

        ImageIcon rulesImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/zasady.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        rules = new JButton(rulesImg);
//        play.setRolloverIcon(playViewImgHover);
        rules.setBorder(null);
        rules.setOpaque(false);
        rules.setBorderPainted(false);
        rules.setContentAreaFilled(false);
        rules.setCursor(cursor);
        rules.setText("rules");

        ImageIcon exitImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wyjscie.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        exit = new JButton(exitImg);
//        play.setRolloverIcon(playViewImgHover);
        exit.setBorder(null);
        exit.setOpaque(false);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setCursor(cursor);
        exit.setText("rules");

        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 600 , 800);

        container.add(upLabel, 0);
        container.add(leftLabel, 1);
        container.add(rightLabel, 2);

        container.add(menuTitle, 3);
        menuTitle.setBounds(150, 10, 299, 171);

        container.add(playGame, 4);
        playGame.setBounds(245, 160, 125, 125);

        container.add(simulateGame, 5);
        simulateGame.setBounds(220, 280, 175, 100);

        container.add(shop, 6);
        shop.setBounds(220, 400, 175, 100);

        container.add(ranking, 7);
        ranking.setBounds(490, 10, 100, 100);

        container.add(rules, 8);
        rules.setBounds(220, 520, 175, 100);

        container.add(exit, 9);
        exit.setBounds(220, 640, 175, 100);

//        --------------------------------SHOP-----------------------------------

        ImageIcon backShopImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wroc.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        backShop = new JButton(backShopImg);
//        play.setRolloverIcon(playViewImgHover);
        backShop.setBorder(null);
        backShop.setOpaque(false);
        backShop.setBorderPainted(false);
        backShop.setContentAreaFilled(false);
        backShop.setCursor(cursor);
        backShop.setText("backShop");

        container.add(backShop, 10);
        backShop.setBounds(20, 20, 100, 100);
        backShop.setVisible(false);

        ImageIcon shipImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/statek.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        extraShip = new JButton(shipImg);
//        play.setRolloverIcon(playViewImgHover);
        extraShip.setBorder(null);
        extraShip.setOpaque(false);
        extraShip.setBorderPainted(false);
        extraShip.setContentAreaFilled(false);
        extraShip.setCursor(cursor);
        extraShip.setText("extraShip");

        container.add(extraShip, 11);
        extraShip.setBounds(100, 490, 400, 229);
        extraShip.setVisible(false);

        ImageIcon barrierImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/bariera.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        barrierShop = new JButton(barrierImg);
//        play.setRolloverIcon(playViewImgHover);
        barrierShop.setBorder(null);
        barrierShop.setOpaque(false);
        barrierShop.setBorderPainted(false);
        barrierShop.setContentAreaFilled(false);
        barrierShop.setCursor(cursor);
        barrierShop.setText("barrierShop");

        container.add(barrierShop, 11);
        barrierShop.setBounds(100, 220, 400, 229);
        barrierShop.setVisible(false);

        ImageIcon shopTitleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/titleShop.png")));
        shopTitle = new JLabel(shopTitleImg);
        container.add(shopTitle, 12);
        shopTitle.setBounds(210, 10, 180, 180);
        shopTitle.setVisible(false);

        this.add(container);
        this.setVisible(true);
    }
}
