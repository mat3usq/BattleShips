package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JPanelMenu extends JPanelBG{
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JLabel upLabel;
    public JLabel leftLabel;
    public JLabel rightLabel;
    public JLabel menuTitle;
    public JButton playGame;
    public JButton simulateGame;
    public JButton shop;
    public JButton ranking;
    public JButton rules;
    public JButton exit;

    public JPanelMenu() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundMenu.jpg")));

        ImageIcon titleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/title.png")));
        ImageIcon upArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowUp.png")));
        ImageIcon rightArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowRight.png")));
        ImageIcon leftArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowLeft.png")));
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

        this.setBounds(0, 0, 600, 800);

        this.add(upLabel, 0);
        this.add(leftLabel, 1);
        this.add(rightLabel, 2);

        this.add(menuTitle, 3);
        menuTitle.setBounds(150, 10, 299, 171);

        this.add(playGame, 4);
        playGame.setBounds(245, 160, 125, 125);

        this.add(simulateGame, 5);
        simulateGame.setBounds(220, 280, 175, 100);

        this.add(shop, 6);
        shop.setBounds(220, 400, 175, 100);

        this.add(ranking, 7);
        ranking.setBounds(490, 10, 100, 100);

        this.add(rules, 8);
        rules.setBounds(220, 520, 175, 100);

        this.add(exit, 9);
        exit.setBounds(220, 640, 175, 100);
    }
}
