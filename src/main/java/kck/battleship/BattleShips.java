package kck.battleship;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import kck.battleship.controller.GameException;
import kck.battleship.view.TextView;

import java.awt.*;
import java.io.IOException;

public class BattleShips {
    public static void main(String[] args) throws IOException, GameException, InterruptedException {
        Font myFont = new Font("Monospaced", Font.PLAIN, 27);
        AWTTerminalFontConfiguration myFontConfiguration = AWTTerminalFontConfiguration.newInstance(myFont);
        DefaultTerminalFactory dtf = new DefaultTerminalFactory();
        dtf.setForceAWTOverSwing(true);
        dtf.setTerminalEmulatorFontConfiguration(myFontConfiguration);
        Terminal terminal = dtf.createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);
        new TextView(screen);

        TextView.printHomePage();
        TextView.waitForKeyHomePage(terminal);
        TextView.chooseOption(terminal, 0);
    }
}