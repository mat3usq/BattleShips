package kck.battleship.view.graphicView;

import kck.battleship.controller.GameException;
import kck.battleship.controller.ViewController;
import kck.battleship.model.clases.BattleField;
import kck.battleship.model.clases.Position;
import kck.battleship.model.clases.Ship;
import kck.battleship.model.types.TypesDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

public class JPanelManage extends JPanelBG implements ActionListener {
    public JPanelMap map;
    private JPanelOptions options;
    private int counter = 0;
    private Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public Ship currentShip;
    public ArrayList<Ship> ships;
    public BattleField battleField;
    private boolean noAdd = true;
    public JLabel errorLabel = new JLabel();
    public JPanelBG errorPage = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/errorPage.png")));
    public JLabel shipLabel = new JLabel();
    public JLabel imgShip = new JLabel();

    public JButton game;

    public JPanelManage() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundManageShips.png")));
        this.setSize(1200, 750);

        map = new JPanelMap("manage");
        this.add(map);

        options = new JPanelOptions();
        this.add(options);

        for (int i = 0; i < map.jButtons.length; i++) {
            for (int j = 0; j < map.jButtons[i].length; j++) {
                map.jButtons[i][j].addActionListener(this);
                map.jButtons[i][j].setActionCommand("" + i + " " + j);
            }
        }

        errorLabel = new JLabel();
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        errorLabel.setForeground(Color.BLACK);
        errorLabel.setBounds(290, 40, 660, 40);

        errorPage.setBounds(-120, 600, 900, 120);
        errorPage.setOpaque(false);
        errorPage.setLayout(null);
        errorPage.add(errorLabel);
        errorPage.setVisible(false);
        this.add(errorPage);

        shipLabel = new JLabel();
        shipLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        shipLabel.setForeground(Color.ORANGE);
        shipLabel.setBounds(100, 620, 400, 40);
        this.add(shipLabel);

        imgShip = new JLabel();
        imgShip.setBounds(140, 650, 200, 50);
        this.add(imgShip);

        ImageIcon playGameImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/bitwa.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        game = new JButton(playGameImg);
//        play.setRolloverIcon(playViewImgHover);
        game.setBorder(null);
        game.setOpaque(false);
        game.setBorderPainted(false);
        game.setContentAreaFilled(false);
        game.setCursor(cursor);
        game.setText("playGame");
        game.setBounds(250, 610, 175, 100);
        this.add(game, 0);
        game.setVisible(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (noAdd) {
            boolean isAdded;
            JButton source = (JButton) e.getSource();
            StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            TypesDirection type;
            if (options.directions[0].isSelected())
                type = TypesDirection.VERTICAL;
            else type = TypesDirection.HORIZONTAL;

            try {
                isAdded = ViewController.addShipGraphic(currentShip, new Position(x, y), type, battleField);
            } catch (GameException ex) {
                throw new RuntimeException(ex);
            }

            if (isAdded) {
                if (counter + 1 != ships.size())
                    counter++;
                else {
                    noAdd = false;
                    shipLabel.setVisible(false);
                    imgShip.setVisible(false);
                    options.directions[0].setEnabled(false);
                    options.directions[1].setEnabled(false);
                    options.ships[counter].setEnabled(false);
                    game.setVisible(true);
                }

                options.ships[counter - 1].setEnabled(false);
                ViewController.getInstance().addShipsVisually(battleField, ships.get(counter), ships);
            }
        }
    }
}