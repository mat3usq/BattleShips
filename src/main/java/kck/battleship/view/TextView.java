package kck.battleship.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.controller.Game;
import kck.battleship.controller.GameException;
import kck.battleship.model.clases.*;
import kck.battleship.model.types.TypesField;
import kck.battleship.model.types.TypesShips;

import java.io.IOException;
import java.util.*;

public class TextView {
    private static Screen screen;

    private static final List<String> menuList = new ArrayList<>(Arrays.asList("Rozpocznij Grę", "Symuluj Grę", "Sklep", "Ranking", "Zasady Gry", "Wyjście"));

    public TextView(Screen screen) {
        this.screen = screen;
    }

    public static void printHomePage() {
        TextGraphics tg = screen.newTextGraphics();

        printTitle();

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(16, 16, "Jeśli chcesz przejść dalej, kliknij", SGR.BOLD, SGR.FRAKTUR);
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(52, 16, "ENTER", SGR.BLINK, SGR.ITALIC, SGR.BOLD);

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTitle() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(3, 3, "                                                                         ", SGR.UNDERLINE);
        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(6, 4, "  ____    _  _____ _____ _     _____   ____  _   _ ___ ____  ____", SGR.BOLD);
        tg.putString(6, 5, " | __ )  / \\|_   _|_   _| |   | ____| / ___|| | | |_ _|  _ \\/ ___|", SGR.BOLD);
        tg.putString(6, 6, " |  _ \\ / _ \\ | |   | | | |   |  _|   \\___ \\| |_| || || |_) \\___ \\ \n", SGR.BOLD);
        tg.putString(6, 7, " | |_) / ___ \\| |   | | | |___| |___   ___) |  _  || ||  __/ ___) |\n", SGR.BOLD);
        tg.putString(6, 8, " |____/_/   \\_\\_|   |_| |_____|_____| |____/|_| |_|___|_|   |____/ \n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(3, 10, "                                                                         ", SGR.CROSSED_OUT);
    }

    private static void printMenu() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(24, 11, " __  __  ____  _  _  __  __ \n", SGR.BOLD);
        tg.putString(24, 12, "(  \\/  )( ___)( \\( )(  )(  )\n", SGR.BOLD);
        tg.putString(24, 13, " )    (  )__)  )  (  )(__)( \n", SGR.BOLD);
        tg.putString(24, 14, "(_/\\/\\_)(____)(_)\\_)(______)\n", SGR.BOLD);
    }

    public static void waitForKeyHomePage(Terminal terminal) throws IOException {
        Boolean b = true;
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
                        printMenuPage(0);
                        b = false;
                    }
                }
        }
    }

    public static void printMenuPage(int selected) {
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

    private static void printExit() {
        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        printTitle();
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(30, 15, "Dziekujemy Za     !!!", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(44, 15, "Gre", SGR.BOLD, SGR.BLINK);

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

    public static void chooseOption(Terminal terminal, int selected) throws IOException, GameException, InterruptedException {
        Boolean b = true;
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
                        option(selected, terminal);
                        b = false;
                    }
                }
        }
    }

    private static void option(int selected, Terminal terminal) throws IOException, GameException, InterruptedException {
        for (int i = 0; i < menuList.size(); i++) {
            if (i == selected)
                switch (menuList.get(i)) {
                    case "Rozpocznij Grę" -> {
                        String name = UserInput.getUserInput(screen, terminal, "Wprowadź swoj NICK i naciśnij Enter:");
                        if (name != null) {
                            Game game = new Game(name);
                            game.run(screen, terminal);
                        }
                    }
                    case "Symuluj Grę" -> {
                        Game game = new Game();
                        game.run(screen, terminal);
                    }
                    case "Sklep" -> {
                        printShop(terminal);
                    }
                    case "Ranking" -> {
                        printRanking(terminal, 0);
                    }
                    case "Zasady Gry" -> {
                        printRules(terminal);
                    }
                    case "Wyjście" -> {
                        printExit();
                    }
                }
        }
    }

    private static void printRules(Terminal terminal) throws IOException, GameException, InterruptedException {
        printInfoRules(1);

        Boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printMenuPage(4);
                        chooseOption(terminal, 4);
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

    private static void printInfoRules(int x) {
        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        if (x == 1) {
            tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
            tg.putString(31, 1, "Jak wygrać?", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 3, "- Każdy gracz ma pole bitwy reprezentowane przez siatkę 10x10 (domyślną),", SGR.BOLD);
            tg.putString(2, 4, "   na której rozmieszcza " + TypesShips.countAllShips() + " statków, ukrytych przed przeciwnikiem.", SGR.BOLD);
            tg.putString(2, 5, "- Celem gry jest zatopienie wszystkich statków przeciwnika!", SGR.BOLD);
            tg.putString(2, 6, "- Np. statek typu " + TypesShips.values()[0] + ", który zajmuje " + TypesShips.values()[0].getShipLength() + " pól,", SGR.BOLD);
            tg.putString(2, 7, "   zostaje zatopiony po dwóch trafieniach.", SGR.BOLD);
            tg.putString(2, 8, "- Wszystkie " + TypesShips.countAllShips() + " statki zajmują łącznie " + TypesShips.lengthAllShips() + " pól,", SGR.BOLD);
            tg.putString(2, 9, "   więc pierwszy gracz, który odnotuje " + TypesShips.lengthAllShips() + " trafień, wygrywa!", SGR.BOLD, SGR.BOLD);

            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(31, 11, "Rozgrywka", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 13, "- Postępuj zgodnie z instrukcjami w celu skonfigurowania swoich " + TypesShips.countAllShips() + " statków.", SGR.BOLD);
            tg.putString(2, 14, "- Umieścić statek podając współrzędną początkową (A1-J10) i kierunek (h/v).", SGR.BOLD);
            tg.putString(2, 15, "- Statki nie mogą na siebie nachodzić ani stykać się.", SGR.BOLD);
            tg.putString(2, 16, "- Gdy obaj gracze skonfigurują swoje statki, bitwa się rozpoczyna!", SGR.BOLD);
            tg.putString(2, 17, "- Wystrzel rakiety w statki przeciwnika, zgadując współrzędne.", SGR.BOLD);
            tg.putString(2, 18, "- Zostaniesz poinformowany, czy trafiłeś w statek, czy nie.", SGR.BOLD);
            tg.putString(2, 19, "- Zatop wszystkie " + TypesShips.countAllShips() + " statków komputera, aby wygrać!", SGR.BOLD);
        } else {
            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            tg.putString(33, 1, "Legenda", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);

            int i = 0;
            for (TypesShips type : TypesShips.values()) {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(2, 3 + i, "- (", SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                tg.putString(6, 3 + i, String.valueOf(TypesField.SHIP.name), SGR.BOLD);
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(8, 3 + i, "x", SGR.BOLD);
                tg.putString(9, 3 + i, String.valueOf(type.getShipLength()), SGR.BOLD);
                tg.putString(10, 3 + i, " ) : " + TypesShips.toPolishName(type), SGR.BOLD);
                i++;
            }


            tg.putString(2, 5 + i, "- ( ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
            tg.putString(7, 5 + i, String.valueOf(TypesField.WATER.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(9, 5 + i, " ) : Woda ", SGR.BOLD);

            tg.putString(2, 6 + i, "- ( ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(7, 6 + i, String.valueOf(TypesField.SHIP.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(9, 6 + i, " ) : Statek ", SGR.BOLD);


            tg.putString(2, 7 + i, "- ( ", SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            tg.putString(7, 7 + i, String.valueOf(TypesField.HIT.name), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(9, 7 + i, " ) : Trafiony Statek ", SGR.BOLD);


            tg.putString(2, 8 + i, "- ( ", SGR.BOLD);
            tg.putString(7, 8 + i, String.valueOf(TypesField.MISS.name), SGR.BOLD);
            tg.putString(9, 8 + i, " ) : Nietrafiony Strzał ", SGR.BOLD);

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

    public static void printError(String message) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(36, 15, message + "                     ", SGR.BOLD);
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printShot(Player player, Position position, boolean isHit) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (player.getName().equals("Wróg") || player.getName().equals("Enemy2")) {
            tg.putString(48, 15, player.getName() + " strzelił w " + position.toString(position), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
            if (isHit) {
                tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                tg.putString(52, 16, "Trafiony!", SGR.BOLD);
            } else {
                tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
                tg.putString(52, 16, "NieTrafiony!", SGR.BOLD);
            }
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        } else {
            tg.putString(11, 15, player.getName() + " strzelił w " + position.toString(position), SGR.BOLD);
            if (isHit) {
                tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                tg.putString(15, 16, "Trafiony!", SGR.BOLD);
            } else {
                tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
                tg.putString(15, 16, "NieTrafiony!", SGR.BOLD);
            }
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        }

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printWinner(Player player, Ranking rank) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(32, 15, "✔ " + player.getName() + " wygrał(a)!", SGR.BOLD);
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

    public static void printShip(Ship ship) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(2, 15, "→ " + ship.getName(), SGR.BOLD);

        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(4, 16, ship.toString(), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        screen.refresh();
    }

    public static void printBoards(Player firstPlayer, Player secondPlayer) {
        BattleField firstBattleField = firstPlayer.getBattleField();
//        BattleField secondBattleField = secondPlayer.getBoard().getbattleFieldHideShips();
        BattleField secondBattleField = secondPlayer.getBattleField();

        TextGraphics tg = screen.newTextGraphics();
        String letters = "abcdefghij";

        screen.clear();

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

    public static void printBoard(BattleField battleField) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        String letters = "abcdefghij";

        screen.clear();

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

    public static void printRanking(Terminal terminal, int page) throws IOException, GameException, InterruptedException {
        List<Ranking> rankings = Ranking.getRanking();
        Collections.sort(rankings, Collections.reverseOrder(Comparator.comparingInt(Ranking::getPoints)));
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

            tg.putString(23, 6 + i, (j + 1) + ".", SGR.BOLD);
            tg.putString(33, 6 + i, ranking.getPlayer().getName(), SGR.BOLD);
            tg.putString(49, 6 + i, String.valueOf(ranking.getPoints()), SGR.BOLD);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        }

        tg.putString(30, 20, "Strona   z " + (rankings.size() / itemsPerPage + 1), SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(37, 20, String.valueOf(page + 1), SGR.BOLD);

        screen.refresh();

        Boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printMenuPage(3);
                        chooseOption(terminal, 3);
                        b = false;
                    }
                    case ArrowUp -> {
                        if (page > 0)
                            printRanking(terminal, --page);
                    }
                    case ArrowDown -> {
                        if (page < rankings.size() / itemsPerPage)
                            printRanking(terminal, ++page);
                    }
                }
        }

    }

    private static void printShop(Terminal terminal) throws IOException, GameException, InterruptedException {
        int selected = 0;
        printItemsInShop(selected);
        Boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printMenuPage(2);
                        chooseOption(terminal, 2);
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
                        String name = UserInput.getUserInput(screen, terminal, "Podaj swoj nick, aby kupic w sklepie wybrana rzecz!");
                        String s;

                        if (selected == 0)
                            s = Ranking.enoughPoints(name, 500, screen, terminal, selected);
                        else
                            s = Ranking.enoughPoints(name, 300, screen, terminal, selected);

                        TextGraphics tg = screen.newTextGraphics();
                        if (s == null) {
                            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                            tg.putString(20, 21, "Pomyslnie zakupiono wybrana rzecz!", SGR.BOLD);
                        } else {
                            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.putString(20, 19, s, SGR.BOLD);
                        }

                        screen.refresh();

                        Thread.sleep(2000);
                        printItemsInShop(selected);
                    }
                }
        }
    }

    private static void printItemsInShop(int x) {
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

        tg.putString(1, 9, "+~^~^~^~^^~^~^~^~^~^~^~^~~^~^~^~^~+", SGR.BOLD);
        tg.putString(1, 10, "|                   _,:`,:'_=-    |", SGR.BOLD);
        tg.putString(1, 11, "|                ,:',:@,:\"'       |", SGR.BOLD);
        tg.putString(1, 12, "|             ,:',:`,:',?         |", SGR.BOLD);
        tg.putString(1, 13, "|         __||_||_||_||__         |", SGR.BOLD);
        tg.putString(1, 14, "|    ____[\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"]____    |", SGR.BOLD);
        tg.putString(1, 15, "|    \\ \" '''''''''''''''''''' |   |", SGR.BOLD);
        tg.putString(1, 16, "+~^~^~^~^^~^LOTNISKOWIEC^~^~^~^~^~+\n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(1, 18, "             Cena: 500", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (x == 1)
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        tg.putString(42, 8, "        _.,,,,,,,,,._\n", SGR.BOLD);
        tg.putString(42, 9, "     .d''            ``b.\n", SGR.BOLD);
        tg.putString(42, 10, "   .p'     Obiekty     `q.\n", SGR.BOLD);
        tg.putString(42, 11, " .d'     w magicznym     `b.\n", SGR.BOLD);
        tg.putString(42, 12, ".d'  polu moga byc mniej  `b.\n", SGR.BOLD);
        tg.putString(42, 13, " ::  podatne na obrazenia  ::\n", SGR.BOLD);
        tg.putString(42, 14, " `p.  rakietowe (działa  .q'\n", SGR.BOLD);
        tg.putString(42, 15, "  `p.    na pierwsze    .q'\n", SGR.BOLD);
        tg.putString(42, 16, "   `b.    5 strzałów)  .d'\n", SGR.BOLD);
        tg.putString(42, 17, "     `q..            ..,'\n", SGR.BOLD);
        tg.putString(42, 18, "        '',,,,,,,,,,''\n", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(42, 20, "           Cena: 300", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);


        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        tg.putString(4, 22, "KUPIONE RZECZY W SKLEPIE OBOWIAZUJĄ TYLKO PRZEZ PIERWSZA ZAGRANA GRE!!!", SGR.BOLD);

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
