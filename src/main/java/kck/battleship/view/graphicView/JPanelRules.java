package kck.battleship.view.graphicView;

import kck.battleship.model.types.TypesShips;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JPanelRules extends JPanelBG {
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JButton backRules;
    public JLabel rulesTitle;
    public JPanelRules() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundRules.png")));

        this.setBounds(0, 0, 600, 800);

        ImageIcon titleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rulesTitle.png")));
        rulesTitle = new JLabel(titleImg);

        ImageIcon backRulesImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wroc.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        backRules = new JButton(backRulesImg);
//        play.setRolloverIcon(playViewImgHover);
        backRules.setBorder(null);
        backRules.setOpaque(false);
        backRules.setBorderPainted(false);
        backRules.setContentAreaFilled(false);
        backRules.setCursor(cursor);
        backRules.setText("backRules");

        this.add(backRules, 0);
        backRules.setBounds(20, 20, 100, 100);

        JLabel rules = new JLabel("<html><body style='width: 220px; color: darkred;'>" +
                "<div style='text-align: center; font-size: 20px; font-weight: bold; border-bottom: 2px solid brown; margin-bottom: 5px;'>Jak wygrać?</div>" +
                "<p style='margin: 5px;'>→ Każdy gracz ma pole bitwy reprezentowane przez siatkę 10x10 (domyślną), na której rozmieszcza " + TypesShips.countAllShips() + " statków, ukrytych przed przeciwnikiem.</p>" +
                "<p style='margin: 5px;'>→ Celem gry jest zatopienie wszystkich statków przeciwnika!</p>" +
                "<p style='margin: 5px;'>→ Np. statek typu \"Czarna Perła\", który zajmuje " + TypesShips.values()[0].getShipLength() + " pola, zostaje zatopiony po dwóch trafieniach.</p>" +
                "<p style='margin: 5px;'>→ Wszystkie " + TypesShips.countAllShips() + " statki zajmują łącznie " + TypesShips.lengthAllShips() + " pól, więc pierwszy gracz, który odnotuje " + TypesShips.lengthAllShips() + " trafień, wygrywa!</p>" +
                "<div style='text-align: center; font-size: 18px; font-weight: bold; border-top: 2px solid brown; margin-top: 5px;'>Rozgrywka</div>" +
                "<p style='margin: 5px;'>→ Postępuj zgodnie z instrukcjami w celu skonfigurowania swoich " + TypesShips.countAllShips() + " statków.</p>" +
                "<p style='margin: 5px;'>→ Umieścić statek na planszy poprzez kliknięcie na dane pole na mapie.</p>" +
                "<p style='margin: 5px;'>→ Statki nie mogą na siebie nachodzić ani stykać się.</p>" +
                "<p style='margin: 5px;'>→ Gdy obaj gracze skonfigurują swoje statki, bitwa się rozpoczyna!</p>" +
                "<p style='margin: 5px;'>→ Wystrzel ze wystrzel z dział w statki przeciwnika, zgadując współrzędne statków.</p>" +
                "<p style='margin: 5px;'>→ Zostaniesz poinformowany, czy trafiłeś w statek, czy nie.</p>" +
                "<p style='margin: 5px;'>→ Zatop wszystkie " + TypesShips.countAllShips() + " statków przeciwnika, aby wygrać!</p>" +
                "</body></html>");

        rules.setVerticalAlignment(SwingConstants.TOP);

        JScrollPane scrollPane = new JScrollPane(rules);
        scrollPane.setBounds(150, 300, 320, 300);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        this.add(scrollPane);

        rulesTitle.setBounds(195, -25, 200, 200);
        this.add(rulesTitle);
    }
}
