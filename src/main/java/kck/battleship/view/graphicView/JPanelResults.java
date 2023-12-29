package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;

public class JPanelResults extends JPanelBG {
    public JLabel winnerName = new JLabel();
    public JLabel points = new JLabel();

    public JPanelResults() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/winnerBackground.png")));
        this.setBounds(0, 0, 600, 800);

        winnerName.setForeground(Color.black);
        winnerName.setFont(new Font("Arial", Font.BOLD, 26));
        winnerName.setBounds(90, 100, 300, 100);
        this.add(winnerName);

        points.setForeground(Color.black);
        points.setFont(new Font("Arial", Font.BOLD, 24));
        points.setBounds(110, 150, 300, 100);
        this.add(points);
    }
}
