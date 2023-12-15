package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class IntroScreen extends JFrame {
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JButton play;
    public JButton exit;
    public JTextField nicknameField;

    public IntroScreen() {
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

        ImageIcon graphicViewImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/random.png")));
        ImageIcon graphicViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/randomOver.png")));
        play = new JButton(graphicViewImg);
        play.setRolloverIcon(graphicViewImgHover);
        play.setBorder(null);
        play.setOpaque(false);
        play.setBorderPainted(false);
        play.setContentAreaFilled(false);
        play.setCursor(cursor);
        play.setText("graphicViewButton");

        ImageIcon textViewImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/random.png")));
        ImageIcon textViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/randomOver.png")));
        exit = new JButton(textViewImg);
        exit.setRolloverIcon(textViewImgHover);
        exit.setBorder(null);
        exit.setOpaque(false);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setCursor(cursor);
        exit.setText("textViewButton");

        JLabel chooseViewLabel = new JLabel("Wprowadz swoj nick piracie!");
        chooseViewLabel.setForeground(Color.WHITE);
        chooseViewLabel.setFont(new Font("Arial", Font.BOLD, 18));

        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 600, 400);

        container.add(loadingLabel, 0);
        loadingLabel.setBounds(280, 220, 40, 40);

        container.add(play, 1);
        play.setBounds(80, 280, 200, 30);

        container.add(exit, 2);
        exit.setBounds(320, 280, 200, 30);

        container.add(chooseViewLabel, 3);
        chooseViewLabel.setBounds(185, 130, 300, 20);

        nicknameField = new JTextField();
        nicknameField.setBounds(220, 160, 160, 30);
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nicknameField.setBackground(new Color(255, 255, 255));
        nicknameField.setForeground(new Color(0, 0, 0));
        nicknameField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        nicknameField.setBounds(220, 190, 160, 20);
        nicknameField.setBorder(BorderFactory.createCompoundBorder(
                nicknameField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        container.add(nicknameField, 4);

        play.addActionListener(e -> {
            String nickname = nicknameField.getText();
        });

        exit.addActionListener(e -> dispose());

        this.add(container);
        this.setVisible(true);
    }
}
