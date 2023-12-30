package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {
    public JDialogPopupShip popup;
    public JPanelManage manage;
    public JPanelBattle battle;

    public GameScreen(boolean type) {
        super("Game - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.setSize(1200, 750);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        popup = new JDialogPopupShip(this, "Losowo Ustawione Statki", "Czy chciał(a)byś losowo ustawic statki?");
        popup.setVisible(false);

        manage = new JPanelManage();
        manage.setVisible(false);
        this.add(manage);

        battle = new JPanelBattle(type);
        battle.setVisible(false);
        this.add(battle);

        this.setVisible(false);
    }
}
