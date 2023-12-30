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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public JLabel label = new JLabel();
    public JPanelBG errorPage = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/errorPage.png")));
    public JButton game;

    public JPanelManage() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundManageShips.png")));
        this.setSize(1200, 750);

        map = new JPanelMap("manager");
        this.add(map);

        options = new JPanelOptions();
        this.add(options);

        for (int i = 0; i < map.jButtons.length; i++) {
            for (int j = 0; j < map.jButtons[i].length; j++) {
                map.jButtons[i][j].addActionListener(this);
                map.jButtons[i][j].setActionCommand("" + i + " " + j);
            }
        }

        label = new JLabel();
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
        label.setBounds(290, 40, 660, 40);

        errorPage.setBounds(-120, 600, 900, 120);
        errorPage.setOpaque(false);
        errorPage.setLayout(null);
        errorPage.add(label);
        errorPage.setVisible(false);
        this.add(errorPage);

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
            boolean isAdded = false;
            JButton source = (JButton) e.getSource();
            StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            TypesDirection type;
            if (options.directions[0].isSelected())
                type = TypesDirection.VERTICAL;
            else type = TypesDirection.HORIZONTAL;

            try {
                currentShip.setPosition(new Position(x, y));
            } catch (GameException ex) {
                throw new RuntimeException(ex);
            }
            currentShip.setDirection(type);

            try {
                isAdded = battleField.addShip(currentShip);
            } catch (GameException exception) {
                ViewController.getInstance().printError(exception.getMessage());
                Timer timer = new Timer(2000, e1 -> {
                    errorPage.setVisible(false);
                    label.setText("");
                });
                timer.setRepeats(false);
                timer.start();
            }

            if (isAdded) {
                if (counter + 1 != ships.size())
                    counter++;
                else {
                    noAdd = false;
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