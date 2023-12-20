package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginScreen extends JFrame {
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JButton play;
    public JButton exit;
    public JTextField nicknameField;

    public LoginScreen() {
        super("BattleShips - Pirate Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 400);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        JPanel container = new JPanel(null);
        JPanelBG splashPanel = new JPanelBG(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/backgroundIntro.jpeg")));
        ImageIcon loadingIMG = new ImageIcon(Objects.requireNonNull(getClass().getResource("/loading.gif")));
        JLabel loadingLabel = new JLabel(loadingIMG);

        ImageIcon playViewImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/grajMenu.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        play = new JButton(playViewImg);
//        play.setRolloverIcon(playViewImgHover);
        play.setBorder(null);
        play.setOpaque(false);
        play.setBorderPainted(false);
        play.setContentAreaFilled(false);
        play.setCursor(cursor);
        play.setText("playViewImg");

        ImageIcon exitViewImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wyjscieMenu.png")));
//        ImageIcon exitViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wyjscie.png")));
        exit = new JButton(exitViewImg);
//        exit.setRolloverIcon(exitViewImgHover);
        exit.setBorder(null);
        exit.setOpaque(false);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setCursor(cursor);
        exit.setText("exitViewImg");

        JLabel chooseNickLabel = new JLabel("Wprowadz swoj nick piracie!");
        chooseNickLabel.setForeground(Color.WHITE);
        chooseNickLabel.setFont(new Font("Arial", Font.BOLD, 18));

        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 600, 400);

        container.add(loadingLabel, 0);
        loadingLabel.setBounds(280, 200, 40, 40);

        container.add(play, 1);
        play.setBounds(150, 250, 100, 100);

        container.add(exit, 2);
        exit.setBounds(380, 250, 100, 100);

        container.add(chooseNickLabel, 3);
        chooseNickLabel.setBounds(185, 90, 300, 20);

        nicknameField = new JTextField();
        nicknameField.setBounds(220, 140, 160, 30);
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nicknameField.setBackground(new Color(255, 255, 255));
        nicknameField.setForeground(new Color(0, 0, 0));
        nicknameField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        nicknameField.setBorder(BorderFactory.createCompoundBorder(
                nicknameField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        container.add(nicknameField, 4);

        this.add(container);
        this.setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
    }
}
