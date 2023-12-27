package kck.battleship.view.graphicView;

import kck.battleship.controller.Game;
import kck.battleship.model.types.TypesShips;

import javax.swing.*;
import java.awt.*;

public class JPanelOptions extends JPanelBG {
    JRadioButtonMenuItem[] ships;
    JRadioButton[] directions = new JRadioButton[2];

    public JPanelOptions() {
        super(Toolkit.getDefaultToolkit()
                .createImage(GameScreen.class.getResource("/manageBackground.png")));
        this.setLayout(null);
        this.setOpaque(false);
        this.setBounds(550, -20, 800, 800);

//        JLabel managePanelLabel = new JLabel("Statki oraz Opcje");
//        managePanelLabel.setForeground(Color.BLACK);
//        managePanelLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        managePanelLabel.setBounds(260, 140, 200, 50);
//        this.add(managePanelLabel);


        ImageIcon ship1 = new ImageIcon(getClass().getResource("/ship/ship1.png"));
        ImageIcon ship2 = new ImageIcon(getClass().getResource("/ship/ship2.png"));
        ImageIcon ship3 = new ImageIcon(getClass().getResource("/ship/ship3.png"));
        ImageIcon ship4 = new ImageIcon(getClass().getResource("/ship/ship4.png"));


        if (Game.hasExtraShip) {
            ships = new JRadioButtonMenuItem[7];
            ships[6] = new JRadioButtonMenuItem(ship4);
            ships[6].setBounds(270, 600, 160, 40);
        } else
            ships = new JRadioButtonMenuItem[6];


        ships[0] = new JRadioButtonMenuItem(ship1);
        ships[0].setBounds(150, 500, 160, 40);
        ships[1] = new JRadioButtonMenuItem(ship2);
        ships[1].setBounds(215, 500, 160, 40);
        ships[2] = new JRadioButtonMenuItem(ship2);
        ships[2].setBounds(305, 500, 160, 40);
        ships[3] = new JRadioButtonMenuItem(ship3);
        ships[3].setBounds(400, 500, 160, 40);
        ships[4] = new JRadioButtonMenuItem(ship3);
        ships[4].setBounds(180, 550, 160, 40);
        ships[5] = new JRadioButtonMenuItem(ship4);
        ships[5].setBounds(320, 550, 160, 40);

        for (JRadioButtonMenuItem ship : ships) {
            ship.setOpaque(false);
            this.add(ship);
        }

        ButtonGroup radioButtonDirection = new ButtonGroup();
        directions[0] = new JRadioButton("Horyzontalnie");
        directions[0].setBounds(200, 470, 150, 20);
        directions[0].setSelected(true);
        directions[0].setOpaque(false);
        radioButtonDirection.add(directions[0]);

        directions[1] = new JRadioButton("Vertykalnie");
        directions[1].setBounds(360, 470, 150, 20);
        directions[1].setOpaque(false);
        radioButtonDirection.add(directions[1]);

        this.add(directions[0]);
        this.add(directions[1]);

        JLabel rules = new JLabel("<html><body style='width: 300px; color: darkred;'>" +
                "<div style='text-align: center; font-size: 20px; font-weight: bold; border-bottom: 2px solid brown; margin-bottom: 5px;'>Jak ustawic statki?</div>" +
                "<p style='margin: 5px;'>→ Aby ustawic statek zgodnie z oczekiwaniami nalezy kliknac w na plansze w konkretne pole.</p>" +
                "<p style='margin: 5px;'>→ Nastepnie statek pojawi sie w konkretnym miejscu na planszy.</p>" +
                "<p style='margin: 5px;'>→ Klikniecie na mapie wskazuje poczatek statku.</p>" +
                "<p style='margin: 5px;'>→ Mozna rowniez wybierac kierunek poprzez klikniecie w dany guzik.</p>" +
                "<p style='margin: 5px;'>→ Nie mozna zmieniac kierunku statku gdy sie znajduje blisko krawedzi mapy.</p>" +
                "<p style='margin: 5px;'>→ Mozna rowniez rotowac statek poprzez klikniecie klawisza \"r\".</p>" +
                "<p style='margin: 5px;'>→ Po ustawieniu wszystkich statków na wybranych pozycjach gra rozpoczyna sie automatycznie.</p>" +
                "<p style='margin: 5px;'>→ Nie daj sie zatopic piracie!</p>" +
                "</body></html>");

        rules.setVerticalAlignment(SwingConstants.TOP);

        JScrollPane scrollPane = new JScrollPane(rules);
        scrollPane.setBounds(170, 160, 400, 290);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        this.add(scrollPane);
    }
}
