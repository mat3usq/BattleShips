package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class JPanelBattle extends JPanelBG implements ActionListener {
    public JPanelMap firstMap;
    public JPanelMap secondMap;

    public JPanelBattle(boolean type) {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/battle.png")));
        this.setSize(1200, 750);

        firstMap = new JPanelMap("player");
        this.add(firstMap);

        secondMap = new JPanelMap("computer");
        secondMap.setBounds(550, 0, 600, 600);
        this.add(secondMap);

        if (type) {
            for (int i = 0; i < secondMap.jButtons.length; i++) {
                for (int j = 0; j < secondMap.jButtons[i].length; j++) {
                    secondMap.jButtons[i][j].addActionListener(this);
                    secondMap.jButtons[i][j].setActionCommand("" + i + " " + j);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        System.out.println(x + " " + y);
    }
}
