package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;

public class ExitScreen extends JFrame{
    private Timer blinkTimer;
    private JLabel exitTitle = new JLabel(new ImageIcon(getClass().getResource((("/exit.png")))));

    public ExitScreen() {
        super("Exit - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        this.setSize(600, 600);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        JPanelBG background = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GraphicView.class.getResource("/exitBackground.png")));
        background.setBounds(0, 0, 600, 600);
        this.add(background);

        exitTitle.setBounds(300, 350, 200, 200);
        background.add(exitTitle);

        this.setVisible(true);
        setupBlinkTimer();
    }

    private void setupBlinkTimer() {
        blinkTimer = new Timer(500, evt -> exitTitle.setVisible(!exitTitle.isVisible()));
        blinkTimer.start();
    }
}
