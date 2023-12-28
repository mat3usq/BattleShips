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
import java.util.StringTokenizer;

public class JPanelManage extends JPanelBG implements ActionListener {
    public JPanelMap map;
    private JPanelOptions options;
    private int counter = 0;
    public Ship currentShip;
    public ArrayList<Ship> ships;
    public BattleField battleField;
    private boolean noadd = true;

    public JPanelManage() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundManageShips.png")));
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (noadd) {
            System.out.println(counter + " " + ships.size());
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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (isAdded) {
                if (counter + 1 != ships.size())
                    counter++;
                else noadd = false;

                ViewController.getInstance().addShipsVisually(battleField, ships.get(counter), ships);
            }
        }
    }
}