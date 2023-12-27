package kck.battleship.view.graphicView;

import javax.swing.*;
import java.awt.*;

public class JPanelManage extends JPanelBG {
    private JPanelMap map;
    private JPanelOptions options;

    public JPanelManage() {
        super(Toolkit.getDefaultToolkit()
                .createImage(MainScreen.class.getResource("/backgroundManageShips.png")));
        map = new JPanelMap("manager");
        this.add(map);

        options = new JPanelOptions();
        this.add(options);
    }
}
