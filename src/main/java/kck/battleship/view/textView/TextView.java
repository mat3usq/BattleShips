package kck.battleship.view.textView;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.controller.Game;
import kck.battleship.controller.GameException;
import kck.battleship.model.clases.*;
import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.types.TypesField;
import kck.battleship.model.types.TypesShips;
import kck.battleship.view.View;

import java.io.IOException;
import java.util.*;

public class TextView extends View {
    private static String name;
    private static Screen screen;
    private static Terminal terminal;

    private static final List<String> menuList = new ArrayList<>(Arrays.asList("Rozpocznij Grę", "Symuluj Grę", "Sklep", "Ranking", "Zasady Gry", "Wyjście"));

    public TextView(Terminal terminal, Screen screen) {
        TextView.screen = screen;
        TextView.terminal = terminal;
    }

    @Override
    public void printHomePage() {
        TextGraphics tg = screen.newTextGraphics();

        printTitle();
        printShipImage();

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(16, 11, "Jeśli chcesz przejść dalej, kliknij", SGR.BOLD, SGR.FRAKTUR);
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(52, 11, "ENTER", SGR.BLINK, SGR.ITALIC, SGR.BOLD);

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printShipImage() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
        tg.putString(0, 14, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~o~~o~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.putString(0, 16, "                                               |   #)\n\n", SGR.BOLD);
        tg.putString(0, 17, "                                              _|_|_#_\n", SGR.BOLD);
        tg.putString(0, 18, "                                             | 879   |\n", SGR.BOLD);
        tg.putString(0, 19, "    __                    ___________________|       |_________________", SGR.BOLD);
        tg.putString(0, 20, "   |   -_______-----------                                              \\\n", SGR.BOLD);
        tg.putString(0, 21, "  >|    _____                                                   --->     )\n", SGR.BOLD);
        tg.putString(0, 22, "   |__ -     ---------_________________________________________________ /\n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(0, 15, "                                  o o\n", SGR.BOLD);
        tg.putString(0, 16, "                                    o ooo\n", SGR.BOLD);
        tg.putString(0, 17, "                                       o o o\n", SGR.BOLD);
        tg.putString(0, 18, "                                           oo\n", SGR.BOLD);
    }

    @Override
    public void printTitle() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(3, 1, "                                                                         ", SGR.UNDERLINE);
        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(6, 2, "  ____    _  _____ _____ _     _____   ____  _   _ ___ ____  ____", SGR.BOLD);
        tg.putString(6, 3, " | __ )  / \\|_   _|_   _| |   | ____| / ___|| | | |_ _|  _ \\/ ___|", SGR.BOLD);
        tg.putString(6, 4, " |  _ \\ / _ \\ | |   | | | |   |  _|   \\___ \\| |_| || || |_) \\___ \\ \n", SGR.BOLD);
        tg.putString(6, 5, " | |_) / ___ \\| |   | | | |___| |___   ___) |  _  || ||  __/ ___) |\n", SGR.BOLD);
        tg.putString(6, 6, " |____/_/   \\_\\_|   |_| |_____|_____| |____/|_| |_|___|_|   |____/ \n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(3, 8, "                                                                         ", SGR.CROSSED_OUT);
    }

    @Override
    public void printMenu() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(24, 10, " __  __  ____  _  _  __  __ \n", SGR.BOLD);
        tg.putString(24, 11, "(  \\/  )( ___)( \\( )(  )(  )\n", SGR.BOLD);
        tg.putString(24, 12, " )    (  )__)  )  (  )(__)( \n", SGR.BOLD);
        tg.putString(24, 13, "(_/\\/\\_)(____)(_)\\_)(______)\n", SGR.BOLD);
    }

    @Override
    public void waitForKeyHomePage() throws IOException {
        boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printExit();
                        screen.close();
                        System.exit(0);
                        b = false;
                    }
                    case Enter -> {
                        printLoginPage();
                        printMenuPage(0);
                        b = false;
                    }
                }
        }
    }

    @Override
    public void printLoginPage() {
        screen.clear();
        printTitle();

        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(2,10," ___  _  _  ____  ___  ___     _  _  __  __  _ _   _  _   __   __  __  ___ \n", SGR.BOLD);
        tg.putString(2,11,"(  _)( \\( )(_  _)(  _)(  ,)   ( \\( )(  )/ _)( ) ) ( \\( ) (  ) (  \\/  )(  _)\n", SGR.BOLD);
        tg.putString(2,12," ) _) )  (   )(   ) _) )  \\    )  (  )(( (_  )  \\  )  (  /__\\  )    (  ) _)\n", SGR.BOLD);
        tg.putString(2,13,"(___)(_)\\_) (__) (___)(_)\\_)  (_)\\_)(__)\\__)(_)\\_)(_)\\_)(_)(_)(_/\\/\\_)(___)", SGR.BOLD);

        try {
            name = UserInput.getUserInput("Wprowadź swoj NICK i naciśnij Enter:");
        } catch (IOException | GameException | InterruptedException ignored) {
        }
    }

    @Override
    public void printMenuPage(int selected) {
        screen.clear();

        printTitle();
        printMenu();

        for (int i = 0; i < menuList.size(); i++) {
            TextGraphics tg = screen.newTextGraphics();

            if (i == selected)
                tg.setForegroundColor(TextColor.ANSI.MAGENTA);

            if (i == 0)
                tg.putString(32, i + 16, menuList.get(i), SGR.BOLD);
            if (i == 1)
                tg.putString(33, i + 16, menuList.get(i), SGR.BOLD);
            if (i == 2)
                tg.putString(36, i + 16, menuList.get(i), SGR.BOLD);
            if (i == 3)
                tg.putString(35, i + 16, menuList.get(i), SGR.BOLD);
            if (i == 4)
                tg.putString(34, i + 16, menuList.get(i), SGR.BOLD);
            if (i == 5)
                tg.putString(35, i + 16, menuList.get(i), SGR.BOLD);

            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printExit() {
        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        printTitle();
        printShipImage();
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(28, 11, "Dziekujemy Za     !!!", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(42, 11, "Gre", SGR.BOLD, SGR.BLINK);

        try {
            screen.refresh();
            Thread.sleep(3000);
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseOption(int selected) throws IOException, GameException, InterruptedException {
        boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printExit();
                        screen.close();
                        System.exit(0);
                        b = false;
                    }
                    case ArrowDown -> {
                        if (selected + 1 < menuList.size())
                            printMenuPage(++selected);
                    }
                    case ArrowUp -> {
                        if (selected - 1 >= 0)
                            printMenuPage(--selected);
                    }
                    case Enter -> {
                        option(selected);
                        b = false;
                    }
                }
        }
    }

    @Override
    public void option(int selected) throws IOException, GameException, InterruptedException {
        for (int i = 0; i < menuList.size(); i++) {
            if (i == selected)
                switch (menuList.get(i)) {
                    case "Rozpocznij Grę" -> {
                        if (name != null) {
                            Game game = new Game(name);
                            game.run();
                        }
                    }
                    case "Symuluj Grę" -> {
                        Game game = new Game();
                        game.run();
                    }
                    case "Sklep" -> {
                        printShop();
                    }
                    case "Ranking" -> {
                        printRanking(0);
                    }
                    case "Zasady Gry" -> {
                        printRules();
                    }
                    case "Wyjście" -> {
                        printExit();
                    }
                }
        }
    }

    @Override
    public void printRules() throws IOException, GameException, InterruptedException {
        printInfoRules(1);

        boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printMenuPage(4);
                        chooseOption(4);
                        b = false;
                    }
                    case ArrowUp -> {
                        printInfoRules(1);
                    }
                    case ArrowDown -> {
                        printInfoRules(2);
                    }
                }
        }
    }

    @Override
    public void printInfoRules(int x) {
        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        if (x == 1) {
            tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
            tg.putString(31, 1, "Jak wygrać?", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(2, 3, "- Każdy gracz ma pole bitwy reprezentowane przez siatkę 10x10 (domyślną),");
            tg.putString(2, 4, "   na której rozmieszcza " + TypesShips.countAllShips() + " statków, ukrytych przed przeciwnikiem.");
            tg.putString(2, 5, "- Celem gry jest zatopienie wszystkich statków przeciwnika!");
            tg.putString(2, 6, "- Np. statek typu " + TypesShips.values()[0] + ", który zajmuje " + TypesShips.values()[0].getShipLength() + " pól,");
            tg.putString(2, 7, "   zostaje zatopiony po dwóch trafieniach.");
            tg.putString(2, 8, "- Wszystkie " + TypesShips.countAllShips() + " statki zajmują łącznie " + TypesShips.lengthAllShips() + " pól,");
            tg.putString(2, 9, "   więc pierwszy gracz, który odnotuje " + TypesShips.lengthAllShips() + " trafień, wygrywa!");

            tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
            for (int i = 3; i < 10; i++)
                tg.putString(2, i, "-", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(31, 11, "Rozgrywka", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(2, 13, "- Postępuj zgodnie z instrukcjami w celu skonfigurowania swoich " + TypesShips.countAllShips() + " statków.");
            tg.putString(2, 14, "- Umieścić statek ustawiajac go poprzez operowanie strzalkami.");
            tg.putString(2, 15, "- Statki nie mogą na siebie nachodzić ani stykać się.");
            tg.putString(2, 16, "- Gdy obaj gracze skonfigurują swoje statki, bitwa się rozpoczyna!");
            tg.putString(2, 17, "- Wystrzel rakiety w statki przeciwnika, zgadując współrzędne.");
            tg.putString(2, 18, "- Zostaniesz poinformowany, czy trafiłeś w statek, czy nie.");
            tg.putString(2, 19, "- Zatop wszystkie " + TypesShips.countAllShips() + " statków komputera, aby wygrać!");

            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            for (int i = 13; i < 20; i++)
                tg.putString(2, i, "-", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        } else {
            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(33, 1, "Legenda", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

            int i = 0;
            for (TypesShips type : TypesShips.values()) {
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(2, 3 + i, "- (");
                tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                tg.putString(6, 3 + i, (TypesField.SHIP.name + " ").repeat(type.getShipLength()), SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(6 + type.getShipLength() * 2, 3 + i, " ) : " + TypesShips.toPolishName(type));
                i++;
            }

            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(2, 7, "- (");
            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(6, 7, (TypesField.SHIP.name + " ").repeat(6), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(18, 7, " ) : LOTNISKOWIEC");
            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(36, 7, "Opcjonalnie", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

            tg.putString(2, 5 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
            tg.putString(7, 5 + i, String.valueOf(TypesField.WATER.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(9, 5 + i, " ) : Woda ");

            tg.putString(2, 6 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(7, 6 + i, String.valueOf(TypesField.SHIP.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(9, 6 + i, " ) : Statek ");


            tg.putString(2, 7 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            tg.putString(7, 7 + i, String.valueOf(TypesField.HIT.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(9, 7 + i, " ) : Trafiony Statek");


            tg.putString(2, 8 + i, "- ( ");
            tg.putString(7, 8 + i, String.valueOf(TypesField.MISS.name), SGR.BOLD);
            tg.putString(9, 8 + i, " ) : Nietrafiony Strzał ");

            tg.putString(2, 9 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
            tg.putString(7, 9 + i, String.valueOf(TypesField.AIM.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(9, 9 + i, " ) : Celownik Gracza ");

            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            for (int j = 3; j < 14; j++)
                if (j != 8)
                    tg.putString(2, j, "-", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        }

        tg.putString(30, 21, "Strona   z 2", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(37, 21, String.valueOf(x), SGR.BOLD);

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printError(String message) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(36, 15, message + "                     ", SGR.BOLD);
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printShot(Player player, Position position, boolean isHit) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (player.getName().equals("Wróg") || player.getName().equals("Enemy2")) {
            tg.putString(50, 15, player.getName() + " strzelił w " + position.toString(position), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
            if (isHit) {
                tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                tg.putString(54, 16, "Trafiony!", SGR.BOLD);
            } else {
                tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
                tg.putString(54, 16, "NieTrafiony!", SGR.BOLD);
            }
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        } else {
            if (player.getName().equals("Enemy"))
                tg.putString(13, 15, player.getName() + " strzelił w " + position.toString(position), SGR.BOLD);
            else
                tg.putString(13, 15, "Strzeliłeś w " + position.toString(position), SGR.BOLD);

            if (isHit) {
                tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                tg.putString(16, 16, "Trafiony!", SGR.BOLD);
            } else {
                tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
                tg.putString(14, 16, "NieTrafiony!", SGR.BOLD);
            }
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printWinner(Player player, Ranking rank) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);

        if (player.isAI())
            tg.putString(30, 15, "✔ " + player.getName() + " Gre !!", SGR.BOLD);
        else
            tg.putString(30, 15, "✔ Wygrałeś Gre !!", SGR.BOLD);


        if (rank != null) {
            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(32, 17, "Twoj Wynik: " + rank.getPoints(), SGR.BOLD);
        }
        try {
            screen.refresh();
            Thread.sleep(3000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printShip(Ship ship) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(2, 15, "→ " + ship.getName() + ": ", SGR.BOLD);

        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(2 + ("→ " + ship.getName() + ": ").length(), 15, ship.toString(), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        screen.refresh();
    }

    @Override
    public void printBoards(Player firstPlayer, Player secondPlayer) {
        BattleField firstBattleField = firstPlayer.getBattleField();
        BattleField secondBattleField;
        try {
            secondBattleField = secondPlayer.getBattleField().getbattleFieldHideShips();
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
//        BattleField secondBattleField = secondPlayer.getBattleField();

        TextGraphics tg = screen.newTextGraphics();
        String letters = "abcdefghij";

        screen.clear();

        tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
        tg.putString(18, 1, firstPlayer.getName(), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(57, 1, secondPlayer.getName(), SGR.BOLD);


        for (int i = 1; i <= 10; i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(5 + i * 3, 2, i + "   ", SGR.BOLD);
        }

        for (int i = 1; i <= 10; i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(43 + i * 3, 2, i + "   ", SGR.BOLD);
        }

        for (int i = 0; i < BattleField.getLength(); i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(5, 3 + i, letters.charAt(i) + " ", SGR.BOLD);

            for (int j = 0; j < BattleField.getLength(); j++) {
                if (firstBattleField.getbattleField()[i][j] == TypesField.WATER.name) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.WATER.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (firstBattleField.getbattleField()[i][j] == TypesField.HIT.name) {
                    tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.HIT.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (firstBattleField.getbattleField()[i][j] == TypesField.MISS.name) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.MISS.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, firstBattleField.getbattleField()[i][j] + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                }
            }

            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(43, 3 + i, letters.charAt(i) + "   ", SGR.BOLD);

            for (int j = 0; j < BattleField.getLength(); j++) {
                if (secondBattleField.getbattleField()[i][j] == TypesField.WATER.name) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, TypesField.WATER.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (secondBattleField.getbattleField()[i][j] == TypesField.HIT.name) {
                    tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, TypesField.HIT.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (secondBattleField.getbattleField()[i][j] == TypesField.MISS.name) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, TypesField.MISS.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, secondBattleField.getbattleField()[i][j] + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                }
            }
        }

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void printBoard(BattleField battleField, ArrayList<Ship> ships) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        String letters = "abcdefghij";

        for (int i = 1; i <= 10; i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(5 + i * 3, 2, i + "   ", SGR.BOLD);
        }

        for (int i = 0; i < BattleField.getLength(); i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(5, 3 + i, letters.charAt(i) + " ", SGR.BOLD);

            for (int j = 0; j < BattleField.getLength(); j++) {
                if (battleField.getbattleField()[i][j] == TypesField.WATER.name) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.WATER.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (battleField.getbattleField()[i][j] == TypesField.HIT.name) {
                    tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.HIT.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (battleField.getbattleField()[i][j] == TypesField.MISS.name) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.MISS.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, battleField.getbattleField()[i][j] + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                }
            }
        }

        screen.refresh();
    }

    @Override
    public void showOptionToManuallyAddShip() throws IOException {
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.BLUE);
        tg.putString(0, 17, "-".repeat(100), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, 19, "-", SGR.BOLD);
        tg.putString(1, 20, "-", SGR.BOLD);
        tg.putString(1, 21, "-", SGR.BOLD);
        tg.putString(1, 22, "-", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(3, 19, "Aby ustawic statek zgodnie z oczekiwaniami nalezy poruszac sie strzalkami.");
        tg.putString(3, 20, "Poprzez klikniecie klawisza \"r\" mozesz ustawic kierunek statku.");
        tg.putString(3, 21, "Nie mozna zmieniac kierunku statku gdy sie znajduje blisko krawedzi mapy.");
        tg.putString(3, 22, "Mozesz zatwierdzic wybor statku na danej pozycji poprzez klikniecie ENTER.");
        screen.refresh();
    }

    @Override
    public void showOptionToPlay() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.BLUE);
        tg.putString(0, 18, "-".repeat(100), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, 20, "-", SGR.BOLD);
        tg.putString(1, 21, "-", SGR.BOLD);
        tg.putString(1, 22, "-", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(3, 20, "Kursor pojawia sie mniej wiecej na srodku planszy.");
        tg.putString(3, 21, "Aby ustawic celownik zgodnie z oczekiwaniami nalezy poruszac sie strzalkami.");
        tg.putString(3, 22, "Mozesz zatwierdzic wybor pola strzalu poprzez klikniecie ENTER.");
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showOptionToSimulatedGame() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.BLUE);
        tg.putString(0, 18, "-".repeat(100), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, 20, "-", SGR.BOLD);
        tg.putString(1, 21, "-", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(3, 20, "W tej grze nie mozesz strzelac, ale mozesz za to obserwowac bitwe!");
        tg.putString(3, 21, "Symulacja skonczy sie po odpowiednim komunikacie.");
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printBoardWithFutureShip(BattleField battleField, Ship ship) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        for (int i = 0; i < BattleField.getLength(); i++)
            for (int j = 0; j < BattleField.getLength(); j++)
                if (battleField.getbattleField()[i][j] == TypesField.WATER.name) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.WATER.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (battleField.getbattleField()[i][j] == TypesField.SHIP.name) {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.putString(8 + j * 3, 3 + i, TypesField.SHIP.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                }

        tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);

        if (ship.getDirection() == TypesDirection.VERTICAL)
            for (int i = 0; i < ship.getLength(); i++)
                tg.putString(ship.getPosition().getRow() * 3 + 8, ship.getPosition().getColumn() + 3 + i, String.valueOf(TypesField.SHIP.name), SGR.BOLD);
        else
            for (int i = 0; i < ship.getLength(); i++)
                tg.putString(ship.getPosition().getRow() * 3 + 8 + i * 3, ship.getPosition().getColumn() + 3, String.valueOf(TypesField.SHIP.name), SGR.BOLD);

        screen.refresh();
    }

    @Override
    public void printAim(Position shoot, BattleField battleField) {
        TextGraphics tg = screen.newTextGraphics();

        for (int i = 0; i < BattleField.getLength(); i++) {
            for (int j = 0; j < BattleField.getLength(); j++)
                if (battleField.getbattleField()[i][j] == TypesField.WATER.name) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, TypesField.WATER.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (battleField.getbattleField()[i][j] == TypesField.HIT.name) {
                    tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, TypesField.HIT.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else if (battleField.getbattleField()[i][j] == TypesField.MISS.name) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, TypesField.MISS.name + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    tg.putString(46 + j * 3, 3 + i, battleField.getbattleField()[i][j] + " ", SGR.BOLD);
                    tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                }
        }

        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(46 + shoot.getColumn() * 3, 3 + shoot.getRow(), String.valueOf(TypesField.AIM.name), SGR.BOLD);

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printRanking(int page) throws IOException, GameException, InterruptedException {
        List<Ranking> rankings = Ranking.getRanking();
        rankings.sort(Collections.reverseOrder(Comparator.comparingInt(Ranking::getPoints)));
        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(33, 3, "RANKING", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
        tg.putString(20, 5, "Pozycja", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(35, 5, "Nick", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
        tg.putString(47, 5, "Punkty", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        int i = 0;
        int itemsPerPage = 10;
        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, rankings.size());

        for (int j = startIndex; j < endIndex; j++) {
            Ranking ranking = rankings.get(j);
            i++;

            if (startIndex == 0)
                if (i == 1)
                    tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                else if (i == 2)
                    tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                else if (i == 3)
                    tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);

            if (i == 1 || i == 2 || i == 3) {
                tg.putString(23, 6 + i, (j + 1) + ".", SGR.BOLD);
                tg.putString(33, 6 + i, ranking.getPlayer().getName(), SGR.BOLD);
                tg.putString(49, 6 + i, String.valueOf(ranking.getPoints()), SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            } else {
                tg.putString(23, 6 + i, (j + 1) + ".");
                tg.putString(33, 6 + i, ranking.getPlayer().getName());
                tg.putString(49, 6 + i, String.valueOf(ranking.getPoints()));
                tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            }
        }

        tg.putString(30, 20, "Strona   z " + ((rankings.size() - 1) / itemsPerPage + 1), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(37, 20, String.valueOf(page + 1), SGR.BOLD);

        screen.refresh();

        boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printMenuPage(3);
                        chooseOption(3);
                        b = false;
                    }
                    case ArrowUp -> {
                        if (page > 0)
                            printRanking(--page);
                    }
                    case ArrowDown -> {
                        if (page < rankings.size() / itemsPerPage && rankings.size() % 10 != 0)
                            printRanking(++page);
                    }
                }
        }

    }

    @Override
    public void printShop() throws IOException, GameException, InterruptedException {
        int selected = 0;
        printItemsInShop(selected);
        boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printMenuPage(2);
                        chooseOption(2);
                        b = false;
                    }
                    case ArrowLeft -> {
                        printItemsInShop(0);
                        selected = 0;
                    }
                    case ArrowRight -> {
                        printItemsInShop(1);
                        selected = 1;
                    }
                    case Enter -> {
                        TextGraphics tg = screen.newTextGraphics();
                        for (int i = 8; i <21; i++)
                            tg.putString(1, i, " ".repeat(100), SGR.BOLD);

                        String s;

                        if (selected == 0)
                            s = Ranking.enoughPoints(name, 500, selected,UserInput.question("Czy chcesz napewno to kupic(y/n)?"));
                        else
                            s = Ranking.enoughPoints(name, 300, selected,UserInput.question("Czy chcesz napewno to kupic(y/n)?"));

                        if (s == null) {
                            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                            tg.putString(20, 15, "Pomyslnie zakupiono wybrana rzecz!", SGR.BOLD);
                        } else {
                            if(s.length() < 28)
                            {
                                tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                                tg.putString(25, 15, s, SGR.BOLD);
                            }
                            else
                            {
                                tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                                tg.putString(20, 15, s, SGR.BOLD);
                            }
                        }

                        screen.refresh();

                        Thread.sleep(2000);
                        printItemsInShop(selected);
                    }
                }
        }
    }

    @Override
    public void printItemsInShop(int x) {
        screen.clear();

        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(3, 0, "                                                                         ", SGR.UNDERLINE, SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(24, 1, "   _____ _    _  ____  _____  \n", SGR.BOLD);
        tg.putString(24, 2, "  / ____| |  | |/ __ \\|  __ \\ \n", SGR.BOLD);
        tg.putString(24, 3, " | (___ | |__| | |  | | |__) |\n", SGR.BOLD);
        tg.putString(24, 4, "  \\___ \\|  __  | |  | |  ___/ \n", SGR.BOLD);
        tg.putString(24, 5, "  ____) | |  | | |__| | |     \n", SGR.BOLD);
        tg.putString(24, 6, " |_____/|_|  |_|\\____/|_|     \n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(3, 7, "                                                                         ", SGR.UNDERLINE, SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);


        if (x == 0)
            tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);

        tg.putString(4, 9, "+~^~^~^~^^~^~^~^~^~^~^~^~~^~^~^~^~+", SGR.BOLD);
        tg.putString(4, 10, "|                   _,:`,:'_=-    |", SGR.BOLD);
        tg.putString(4, 11, "|                ,:',:@,:\"'       |", SGR.BOLD);
        tg.putString(4, 12, "|             ,:',:`,:',?         |", SGR.BOLD);
        tg.putString(4, 13, "|         __||_||_||_||__         |", SGR.BOLD);
        tg.putString(4, 14, "|    ____[\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"]____    |", SGR.BOLD);
        tg.putString(4, 15, "|    \\ \" '''''''''''''''''''' |   |", SGR.BOLD);
        tg.putString(4, 16, "+~^~^~^~^^~^LOTNISKOWIEC^~^~^~^~^~+\n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(4, 18, "             Cena: 500", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (x == 1)
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        tg.putString(45, 8, "        _.,,,,,,,,,._\n", SGR.BOLD);
        tg.putString(45, 9, "     .d''            ``b.\n", SGR.BOLD);
        tg.putString(45, 10, "   .p'     Obiekty     `q.\n", SGR.BOLD);
        tg.putString(45, 11, " .d'     w magicznym     `b.\n", SGR.BOLD);
        tg.putString(45, 12, ".d'  polu moga byc mniej  `b.\n", SGR.BOLD);
        tg.putString(45, 13, " ::  podatne na obrazenia  ::\n", SGR.BOLD);
        tg.putString(45, 14, " `p.  rakietowe (działa  .q'\n", SGR.BOLD);
        tg.putString(45, 15, "  `p.    na pierwsze    .q'\n", SGR.BOLD);
        tg.putString(45, 16, "   `b.    5 strzałów)  .d'\n", SGR.BOLD);
        tg.putString(45, 17, "     `q..            ..,'\n", SGR.BOLD);
        tg.putString(45, 18, "        '',,,,,,,,,,''\n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(45, 20, "           Cena: 300", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);


        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(4, 22, "KUPIONE RZECZY W SKLEPIE OBOWIAZUJĄ TYLKO PRZEZ PIERWSZA ZAGRANA GRE!!!", SGR.BOLD);

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printBarrier(Player defender) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(46, 15, "Wróg nie trafil w ciebie!", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        if (defender.getDurabilityForceField() == 0)
            tg.putString(50, 16, "Poniewaz miales bariere!", SGR.BOLD);
        else {
            tg.putString(33, 16, "Poniewaz masz bariere jeszcze przez: " + defender.getDurabilityForceField(), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            tg.putString(70, 16, String.valueOf(defender.getDurabilityForceField()), SGR.BOLD);
            tg.putString(72, 16, "rund", SGR.BOLD);
        }
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Screen getScreen() {
        return screen;
    }

    public static Terminal getTerminal() {
        return terminal;
    }

    public boolean isRandomShipsArranged() {
        try {
            return UserInput.question("Czy chcesz losowo rozmiescic statki (y/n)?");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addShipsVisually(BattleField battleField, Ship ship, ArrayList<Ship> ships) {
        try {
            printBoard(battleField, ships);
            printShip(ship);
            UserInput.getMovedShipPosition(ship, battleField);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Position getPositionToShot(Player defender, Player attacker) {
        Position shoot = null;
        boolean isAddHit;
        do {
            try {
                shoot = attacker.shoot(defender.getBattleField().getbattleFieldHideShips());
                isAddHit = defender.addShoot(shoot);
            } catch (GameException e) {
                if (!attacker.isAI()) printError(e.getMessage());
                isAddHit = false;
            }
        } while (!isAddHit);

        return shoot;
    }

    @Override
    public void delayForGameplay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
