package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JPanelShop extends JPanelBG{
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JLabel leftLabel;
    public JLabel rightLabel;
    public JButton backShop;
    public JButton extraShip;
    public JButton barrierShop;
    public JLabel shopTitle;

    public JPanelShop() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundShop.png")));

        this.setBounds(0, 0, 600, 800);

        ImageIcon rightArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowRight.png")));
        ImageIcon leftArrow = new ImageIcon(Objects.requireNonNull(getClass().getResource("/arrowLeft.png")));
        leftLabel = new JLabel(leftArrow);
        rightLabel = new JLabel(rightArrow);

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

        this.add(backShop, 0);
        backShop.setBounds(20, 20, 100, 100);

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

        this.add(extraShip, 1);
        extraShip.setBounds(100, 240, 400, 229);

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

        this.add(barrierShop, 2);
        barrierShop.setBounds(100, 520, 400, 229);

        ImageIcon shopTitleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/titleShop.png")));
        shopTitle = new JLabel(shopTitleImg);
        this.add(shopTitle, 3);
        shopTitle.setBounds(210, 10, 180, 180);

        ImageIcon img300 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/shop300.png")));
        JLabel shop300 = new JLabel(img300);
        this.add(shop300, 4);
        shop300.setBounds(350, 550, 120, 120);

        ImageIcon img500 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/shop500.png")));
        JLabel shop500 = new JLabel(img500);
        this.add(shop500, 5);
        shop500.setBounds(340, 400, 120, 120);

        this.add(leftLabel, 6);
        this.add(rightLabel, 7);

        this.setComponentZOrder(shop300, 0);
        this.setComponentZOrder(shop500, 1);
    }
}
