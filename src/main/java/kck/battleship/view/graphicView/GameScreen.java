package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {
    public JDialogPopupShip popup;
    public GameScreen() {
        super("Game - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.requestFocusInWindow();
        this.setFocusable(true);
        this.setSize(1200, 700);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        popup = new JDialogPopupShip(this, "Losowo Ustawione Statki", "Czy chciał(a)byś losowo ustawic statki?");

        this.setVisible(false);
    }
}
