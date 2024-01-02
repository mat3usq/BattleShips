package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class JPanelBG extends JPanel {
    private static final long serialVersionUID = 1L;
    Image image;

    public JPanelBG(Image img) {
        this.image = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
