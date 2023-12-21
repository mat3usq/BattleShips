package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainScreen extends JFrame {
    public JPanelShop shopPanel;
    public JPanelMenu menuPanel;
    public JDialogPopup popup;

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

        menuPanel = new JPanelMenu();
        menuPanel.setVisible(true);
        this.add(menuPanel);

        shopPanel = new JPanelShop();
        shopPanel.setVisible(false);
        this.add(shopPanel);

        popup = new JDialogPopup(this, "Sklep", "Czy napewno chcial(a)bys to kupic ?");
        popup.setVisible(false);

        this.setVisible(true);
    }
}
