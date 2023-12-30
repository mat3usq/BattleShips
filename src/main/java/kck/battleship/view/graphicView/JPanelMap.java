package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;

public class JPanelMap extends JPanel {
    private JLabel nameLabel;
    public JPanelBG banner = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/banner.png")));
    private Cursor cursorHand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private int fieldDim = 48;
    private int offX = 2;
    private int offY = 2;
    public JButton[][] jButtons = new JButton[10][10];
    private JLabel[] labelX = new JLabel[10];
    private JLabel[] labelY = new JLabel[10];

    public JPanelMap(String player) {
        this.setSize(600, 600);
        this.setLayout(null);
        this.setOpaque(false);

        nameLabel = new JLabel(player);

        if(player.equals("manage"))
            nameLabel = new JLabel(GraphicView.name);

        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setBounds(80, -5, 500, 50);
        banner.add(nameLabel);

        banner.setBounds(200, 10, 260, 60);
        banner.setOpaque(false);
        this.add(banner);

        JPanelBG sea = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/sea.png")));
        sea.setBounds(90, 100, 502, 502);
        this.add(sea);

        ImageIcon gray = new ImageIcon(getClass().getResource("/grayButton.png"));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                jButtons[i][j] = new JButton(gray);
                jButtons[i][j].setSize(fieldDim, fieldDim);
                sea.add(jButtons[i][j]);
                jButtons[i][j].setCursor(cursorHand);
                jButtons[i][j].setBorder(null);
                jButtons[i][j].setOpaque(false);
                jButtons[i][j].setBorderPainted(false);
                jButtons[i][j].setContentAreaFilled(false);
                jButtons[i][j].setBounds(offX, offY, fieldDim, fieldDim);
                if (player.equals("manage") || player.equals("Enemy"))
                    jButtons[i][j].setCursor(cursorHand);
                else {
                    jButtons[i][j].setCursor(cursorDefault);
                    jButtons[i][j].setDisabledIcon(gray);
                    jButtons[i][j].setEnabled(false);
                }
                offX += fieldDim + 2;
            }
            offY += fieldDim + 2;
            offX = 2;
        }

        offX = 100;
        offY = 50;
        for (int i = 0; i < 10; i++) {
            labelX[i] = new JLabel();
            labelY[i] = new JLabel();
            this.add(labelX[i]);
            this.add(labelY[i]);
            labelX[i].setIcon(new ImageIcon(getClass().getResource((("/coordinates/" + (i + 1) + ".png")))));
            labelX[i].setBounds(offX, offY, fieldDim, fieldDim);
            labelY[i].setIcon(new ImageIcon(getClass().getResource((("/coordinates/" + (i + 11) + ".png")))));
            labelY[i].setBounds(offY, offX, fieldDim, fieldDim);
            offX += 50;
        }
    }
}
