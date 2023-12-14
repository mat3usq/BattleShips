package kck.battleship.controller;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import kck.battleship.view.TextView;

import java.awt.*;
import java.io.IOException;

public class ViewController {
    int choice;
    static TextView tv;
    public ViewController(int x) throws IOException, InterruptedException, GameException {
        choice = x;
        if( x == 1 )
        {
            Font myFont = new Font("Monospaced", Font.PLAIN, 24);
            AWTTerminalFontConfiguration myFontConfiguration = AWTTerminalFontConfiguration.newInstance(myFont);
            DefaultTerminalFactory dtf = new DefaultTerminalFactory();
            dtf.setForceAWTOverSwing(true);
            dtf.setTerminalEmulatorFontConfiguration(myFontConfiguration);
            Terminal terminal = dtf.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);
            tv = new TextView(screen);

            tv.printHomePage();
            tv.waitForKeyHomePage(terminal);
            tv.chooseOption(terminal, 0);
        }
    }


    public static TextView getInstance(){
        return tv;
    }
}
