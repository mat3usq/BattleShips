package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainScreen extends JFrame {
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    public MainScreen() {
        super("Menu - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.requestFocusInWindow();
        this.setFocusable(true);
        this.setSize(460, 720);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        JPanel container = new JPanel(null);
        JPanelBG splashPanel = new JPanelBG(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/backgroundMenuu.gif")));
        ImageIcon leftImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/loading.gif")));
        ImageIcon rightImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/loading.gif")));
        JLabel leftLabel = new JLabel(leftImg);
        JLabel rightLabel = new JLabel(rightImg);

        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 460, 720);

        container.add(leftLabel, 0);
        leftLabel.setBounds(280, 220, 40, 40);

        container.add(rightLabel, 0);
        rightLabel.setBounds(300, 220, 40, 40);

        this.add(container);
        this.setVisible(true);
    }
}
