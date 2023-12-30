package kck.battleship.view.graphicView;

import kck.battleship.model.clases.Ranking;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JPanelRanking extends JPanelBG {
    public Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JButton backRanking;
    public JLabel rankingTitle;
    public JScrollPane scrollPane;

    public JPanelRanking() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundRanking.png")));

        this.setBounds(0, 0, 600, 800);

        ImageIcon rankingImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rankingTitle.png")));
        rankingTitle = new JLabel(rankingImg);

        ImageIcon backRankingImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wroc.png")));
//        ImageIcon playViewImgHover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/menu.png")));
        backRanking = new JButton(backRankingImg);
//        play.setRolloverIcon(playViewImgHover);
        backRanking.setBorder(null);
        backRanking.setOpaque(false);
        backRanking.setBorderPainted(false);
        backRanking.setContentAreaFilled(false);
        backRanking.setCursor(cursor);
        backRanking.setText("backRanking");

        updateRanking();

        this.add(backRanking, 0);
        backRanking.setBounds(55, 123, 100, 100);

        rankingTitle.setBounds(200, 35, 200, 200);
        this.add(rankingTitle);
    }

    public void updateRanking() {
        DefaultTableModel rankingModel = new DefaultTableModel(new String[]{"Pozycja", "Nick", "Punkty"}, 0);
        List<Ranking> rankings = Ranking.getRanking();
        rankings.sort(Collections.reverseOrder(Comparator.comparingInt(Ranking::getPoints)));
        for (int i = 0; i < rankings.size(); i++)
            rankingModel.addRow(new Object[]{i + 1 + ".", rankings.get(i).getPlayer().getName(), rankings.get(i).getPoints()});

        JTable rankingTable = new JTable(rankingModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rankingTable.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public boolean isSelectedIndex(int index) {
                return false;
            }

            @Override
            public void setSelectionInterval(int index0, int index1) {
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < rankingTable.getColumnCount(); i++) {
            rankingTable.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            rankingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        rankingTable.setFont(new Font("Roboto", Font.PLAIN, 16));
        rankingTable.setRowHeight(20);
        rankingTable.setOpaque(false);
        rankingTable.setBackground(new Color(0, 0, 0, 0));
        ((DefaultTableCellRenderer) rankingTable.getDefaultRenderer(Object.class)).setOpaque(false);

        rankingTable.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 19));
        rankingTable.getTableHeader().setPreferredSize(new Dimension(rankingTable.getTableHeader().getWidth(), 22));

        if (scrollPane != null)
            this.remove(scrollPane);

        scrollPane = new JScrollPane(rankingTable);
        rankingTable.setFillsViewportHeight(true);
        scrollPane.setBounds(177, 272, 250, 280);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane);
    }
}
