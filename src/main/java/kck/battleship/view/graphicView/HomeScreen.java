package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class HomeScreen extends JFrame {
    private JLabel playLabel;
    private JLabel enterLabel;
    private Timer timer;

    public HomeScreen() {
        super("BattleShips - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 400);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        JPanel container = new JPanel(null);
        JPanelBG splashPanel = new JPanelBG(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/homeScreen.jpg")));
        ImageIcon loadingIMG = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pirateSticker.gif")));
        JLabel loadingLabel = new JLabel(loadingIMG);

        playLabel = new JLabel("Jesli chcesz zagrac kliknij");
        playLabel.setForeground(Color.DARK_GRAY);
        playLabel.setFont(new Font("Arial", Font.BOLD, 18));

        enterLabel = new JLabel("ENTER");
        enterLabel.setForeground(Color.GRAY);
        enterLabel.setFont(new Font("Arial", Font.BOLD, 20));

        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 600, 400);

        container.add(loadingLabel, 0);
        loadingLabel.setBounds(120, 240, 80, 80);

        container.add(playLabel, 1);
        playLabel.setBounds(50, 130, 300, 20);

        container.add(enterLabel, 2);
        enterLabel.setBounds(120, 180, 300, 20);

        timer = new Timer(600, new BlinkAction());
        timer.start();

        this.add(container);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setVisible(true);
    }

    private class BlinkAction implements ActionListener {
        private boolean visible = true;
        @Override
        public void actionPerformed(ActionEvent e) {
            visible = !visible;
            enterLabel.setVisible(visible);
        }
    }
}
