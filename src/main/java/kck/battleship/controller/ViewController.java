package kck.battleship.controller;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import kck.battleship.view.TextView;
import kck.battleship.view.View;

import java.awt.*;
import java.io.IOException;

public class ViewController {
    private static int choice;
    private static TextView textView;
//    private static GraphicView graphicView;

    public ViewController(int choice) throws IOException, InterruptedException, GameException {
        ViewController.choice = choice;
        if (choice == 1) {
            Font myFont = new Font("Monospaced", Font.PLAIN, 24);
            AWTTerminalFontConfiguration myFontConfiguration = AWTTerminalFontConfiguration.newInstance(myFont);
            DefaultTerminalFactory dtf = new DefaultTerminalFactory();
            dtf.setForceAWTOverSwing(true);
            dtf.setTerminalEmulatorFontConfiguration(myFontConfiguration);
            Terminal terminal = dtf.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            textView = new TextView(terminal, screen);

            textView.printHomePage();
            textView.waitForKeyHomePage();
            textView.chooseOption(0);
        }
    }


    public static View getInstance() {
//        if (choice == 1)
            return textView;
//        else if (choice == 2)
//            return graphicView;
    }
}
