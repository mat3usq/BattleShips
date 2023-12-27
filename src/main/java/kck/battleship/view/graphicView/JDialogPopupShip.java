package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JDialogPopupShip extends JDialog {
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private JLabel questionLabel;
    public JButton okButton;
    public JButton cancelButton;

    public JDialogPopupShip(JFrame parentFrame, String title, String message) {
        super(parentFrame, title, true);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(600, 400);

        JLayeredPane layeredPane = new JLayeredPane();
        this.add(layeredPane);
        layeredPane.setBounds(0, 0, 600, 400);

        JPanelBG background = new JPanelBG(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundShipPopup.png")));

        questionLabel = new JLabel(message);
        questionLabel.setForeground(Color.BLACK);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));


        ImageIcon okPopupImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tak.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        okButton = new JButton(okPopupImg);
        okButton.setCursor(cursor);
//        play.setRolloverIcon(playViewImgHover);
        okButton.setBorderPainted(false);
        okButton.setBorder(null);
        okButton.setMargin(new Insets(0, 0, 0, 0));
        okButton.setContentAreaFilled(false);
        okButton.setFocusPainted(false);

        ImageIcon cancelPopupImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/nie.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        cancelButton = new JButton(cancelPopupImg);
        cancelButton.setCursor(cursor);
//        play.setRolloverIcon(playViewImgHover);
        cancelButton.setBorderPainted(false);
        cancelButton.setBorder(null);
        cancelButton.setMargin(new Insets(0, 0, 0, 0));
        cancelButton.setContentAreaFilled(false);
        cancelButton.setFocusPainted(false);

        layeredPane.add(background, Integer.valueOf(1));
        background.setBounds(0, 0, 600, 400);

        layeredPane.add(questionLabel, Integer.valueOf(2));
        questionLabel.setBounds(150, 60, 500, 50);

        layeredPane.add(okButton, Integer.valueOf(2));
        okButton.setBounds(80, 200, 140, 140);

        layeredPane.add(cancelButton, Integer.valueOf(2));
        cancelButton.setBounds(420, 200, 140, 140);

        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }
}
