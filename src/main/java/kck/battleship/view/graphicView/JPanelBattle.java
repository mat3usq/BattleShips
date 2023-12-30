package kck.battleship.view.graphicView;

import kck.battleship.controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class JPanelBattle extends JPanelBG implements ActionListener {
    public JPanelMap firstMap;
    public JPanelMap secondMap;
    public JPanelBG barrier = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/barrierGame.png")));
    public JLabel countBarrier = new JLabel("5");
    public JPanelBG missImgDefender = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/miss.png")));
    public JPanelBG shotImgDefender = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/shot.png")));
    public JLabel missLabelDefender = new JLabel();
    public JLabel shotLabelDefender = new JLabel();
    public JPanelBG missImgAttacker = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/miss.png")));
    public JPanelBG shotImgAttacker = new JPanelBG(Toolkit.getDefaultToolkit().createImage(GameScreen.class.getResource("/shot.png")));
    public JLabel missLabelAttacker = new JLabel();
    public JLabel shotLabelAttacker = new JLabel();

    public int x;
    public int y;

    public JPanelBattle(boolean type) {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/battle.png")));
        this.setSize(1200, 750);

        if (type) {
            firstMap = new JPanelMap("Computer");
            secondMap = new JPanelMap("Computer2");
        } else {
            firstMap = new JPanelMap(GraphicView.name);
            secondMap = new JPanelMap("Enemy");
        }

        secondMap.setBounds(550, 0, 600, 600);
        this.add(secondMap);
        this.add(firstMap);

        if (!type) {
            for (int i = 0; i < secondMap.jButtons.length; i++) {
                for (int j = 0; j < secondMap.jButtons[i].length; j++) {
                    secondMap.jButtons[i][j].addActionListener(this);
                    secondMap.jButtons[i][j].setActionCommand("" + i + " " + j);
                }
            }
        }

        countBarrier.setForeground(Color.black);
        countBarrier.setFont(new Font("Arial", Font.BOLD, 26));
        countBarrier.setBounds(80, 50, 50, 50);
        barrier.add(countBarrier);

        barrier.setBounds(260, 615, 175, 100);
        barrier.setOpaque(false);
        barrier.setVisible(false);
        this.add(barrier);

        missLabelDefender.setForeground(Color.orange);
        missLabelDefender.setFont(new Font("Arial", Font.BOLD, 26));
        missLabelDefender.setBounds(80, 20, 50, 50);
        missImgDefender.add(missLabelDefender);

        missImgDefender.setBounds(260, 615, 175, 100);
        missImgDefender.setOpaque(false);
        missImgDefender.setVisible(false);
        this.add(missImgDefender);

        shotLabelDefender.setForeground(Color.orange);
        shotLabelDefender.setFont(new Font("Arial", Font.BOLD, 26));
        shotLabelDefender.setBounds(80, 20, 50, 50);
        shotImgDefender.add(shotLabelDefender);

        shotImgDefender.setBounds(260, 615, 175, 100);
        shotImgDefender.setOpaque(false);
        shotImgDefender.setVisible(false);
        this.add(shotImgDefender);

//      -------------------------------------------------------------

        missLabelAttacker.setForeground(Color.orange);
        missLabelAttacker.setFont(new Font("Arial", Font.BOLD, 26));
        missLabelAttacker.setBounds(80, 20, 50, 50);
        missImgAttacker.add(missLabelAttacker);

        missImgAttacker.setBounds(840, 615, 175, 100);
        missImgAttacker.setOpaque(false);
        missImgAttacker.setVisible(false);
        this.add(missImgAttacker);

        shotLabelAttacker.setForeground(Color.orange);
        shotLabelAttacker.setFont(new Font("Arial", Font.BOLD, 26));
        shotLabelAttacker.setBounds(80, 20, 50, 50);
        shotImgAttacker.add(shotLabelAttacker);

        shotImgAttacker.setBounds(840, 615, 175, 100);
        shotImgAttacker.setOpaque(false);
        shotImgAttacker.setVisible(false);
        this.add(shotImgAttacker);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());

        ((GraphicView) ViewController.getInstance()).playTurn();
    }
}
