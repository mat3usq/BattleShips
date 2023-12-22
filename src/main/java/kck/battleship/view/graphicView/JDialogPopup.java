package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JDialogPopup extends JDialog {
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private JLabel questionLabel;
    public JLabel confrimLabel;
    public JButton okButton;
    public JButton cancelButton;

    public JDialogPopup(JFrame parentFrame, String title, String message) {
        super(parentFrame, title, false);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(600, 400);

        JLayeredPane layeredPane = new JLayeredPane();
        this.add(layeredPane);
        layeredPane.setBounds(0, 0, 600, 400);

        JPanelBG background = new JPanelBG(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundPopup.png")));

        questionLabel = new JLabel(message);
        questionLabel.setForeground(Color.white);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));

        confrimLabel = new JLabel();
        confrimLabel.setForeground(Color.white);
        confrimLabel.setFont(new Font("Arial", Font.BOLD, 18));

        ImageIcon okPopupImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/kupie.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        okButton = new JButton(okPopupImg);
        okButton.setCursor(cursor);
//        play.setRolloverIcon(playViewImgHover);

        ImageIcon cancelPopupImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/anuluj.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        cancelButton = new JButton(cancelPopupImg);
        cancelButton.setCursor(cursor);
//        play.setRolloverIcon(playViewImgHover);

        layeredPane.add(background, Integer.valueOf(1));
        background.setBounds(0, 0, 600, 400);

        layeredPane.add(questionLabel, Integer.valueOf(2));
        questionLabel.setBounds(150, 60, 500, 50);

        layeredPane.add(confrimLabel, Integer.valueOf(2));
        confrimLabel.setBounds(150, 170, 500, 50);
        confrimLabel.setVisible(false);

        layeredPane.add(okButton, Integer.valueOf(2));
        okButton.setBounds(50, 220, 100, 100);

        layeredPane.add(cancelButton, Integer.valueOf(2));
        cancelButton.setBounds(450, 220, 100, 100);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
