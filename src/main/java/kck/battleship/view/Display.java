package kck.battleship.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.controller.Game;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.*;
import kck.battleship.model.enum_.ShipT;

import java.io.IOException;
import java.util.*;

public class Display {
    private static Screen screen;

    private static final List<String> menuList = new ArrayList<>(Arrays.asList("Rozpocznij Grę", "Symuluj Grę", "Sklep", "Ranking", "Zasady Gry", "Wyjście"));

    public Display(Screen screen) {
        this.screen = screen;
    }

    public static void printHomePage() {
        TextGraphics tg = screen.newTextGraphics();

        printTitle();

        tg.putString(16, 16, "Jeśli chcesz przejść dalej, kliknij");
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(52, 16, "ENTER", SGR.BLINK);

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printTitle() {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(6, 4, "  ____    _  _____ _____ _     _____   ____  _   _ ___ ____  ____");
        tg.putString(6, 5, " | __ )  / \\|_   _|_   _| |   | ____| / ___|| | | |_ _|  _ \\/ ___|");
        tg.putString(6, 6, " |  _ \\ / _ \\ | |   | | | |   |  _|   \\___ \\| |_| || || |_) \\___ \\ \n");
        tg.putString(6, 7, " | |_) / ___ \\| |   | | | |___| |___   ___) |  _  || ||  __/ ___) |\n");
        tg.putString(6, 8, " |____/_/   \\_\\_|   |_| |_____|_____| |____/|_| |_|___|_|   |____/ \n");
    }

    private static void printMenu() {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(24, 10, " __  __  ____  _  _  __  __ \n");
        tg.putString(24, 11, "(  \\/  )( ___)( \\( )(  )(  )\n");
        tg.putString(24, 12, " )    (  )__)  )  (  )(__)( \n");
        tg.putString(24, 13, "(_/\\/\\_)(____)(_)\\_)(______)\n");
    }

    public static void waitForKeyHomePage(Terminal terminal) throws IOException {
        Boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printExit();
                        screen.stopScreen();
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
        tg.putString(30, 15, "Dziekujemy Za Gre!!!");

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

    public static void chooseOption(Terminal terminal, int selected) throws
            IOException, PositionException, InterruptedException {
        Boolean b = true;
        while (b) {
            KeyStroke k = terminal.pollInput();
            if (k != null)
                switch (k.getKeyType()) {
                    case Escape -> {
                        printExit();
                        screen.stopScreen();
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

    private static void option(int selected, Terminal terminal) throws
            IOException, PositionException, InterruptedException {
        for (int i = 0; i < menuList.size(); i++) {
            if (i == selected)
                switch (menuList.get(i)) {
                    case "Rozpocznij Grę" -> {
                        String name = Input.getUserInput(screen, terminal, "Wprowadź swoj NICK i naciśnij Enter:");
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

    private static void printRules(Terminal terminal) throws IOException, PositionException, InterruptedException {
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
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);
            tg.putString(30, 1, "Jak wygrać?", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 3, "- Każdy gracz ma pole bitwy reprezentowane przez siatkę 10x10 (domyślną),");
            tg.putString(2, 4, "   na której rozmieszcza " + ShipT.countAllShips() + " statków, ukrytych przed przeciwnikiem.");
            tg.putString(2, 5, "- Celem gry jest zatopienie wszystkich statków przeciwnika!");
            tg.putString(2, 6, "- Np. statek typu " + ShipT.values()[0] + ", który zajmuje " + ShipT.values()[0].getShipLength() + " pól,");
            tg.putString(2, 7, "   zostaje zatopiony po dwóch trafieniach.");
            tg.putString(2, 8, "- Wszystkie " + ShipT.countAllShips() + " statki zajmują łącznie " + ShipT.lengthAllShips() + " pól,");
            tg.putString(2, 9, "   więc pierwszy gracz, który odnotuje " + ShipT.lengthAllShips() + " trafień, wygrywa!");

            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(30, 11, "Rozgrywka", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 13, "- Postępuj zgodnie z instrukcjami w celu skonfigurowania swoich " + ShipT.countAllShips() + " statków.");
            tg.putString(2, 14, "- Umieścić statek podając współrzędną początkową (A1-J10) i kierunek (h/v).");
            tg.putString(2, 15, "- Statki nie mogą na siebie nachodzić ani stykać się.");
            tg.putString(2, 16, "- Gdy obaj gracze skonfigurują swoje statki, bitwa się rozpoczyna!");
            tg.putString(2, 17, "- Wystrzel rakiety w statki przeciwnika, zgadując współrzędne.");
            tg.putString(2, 18, "- Zostaniesz poinformowany, czy trafiłeś w statek, czy nie.");
            tg.putString(2, 19, "- Zatop wszystkie " + ShipT.countAllShips() + " statków komputera, aby wygrać!");
        } else {
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(30, 1, "Legenda", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);

            int i = 0;
            for (ShipT type : ShipT.values()) {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(2, 3 + i, "- (");
                tg.setForegroundColor(TextColor.ANSI.YELLOW);
                tg.putString(6, 3 + i, String.valueOf(Board.SHIP));
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(8, 3 + i, "x");
                tg.putString(9, 3 + i, String.valueOf(type.getShipLength()));
                tg.putString(10, 3 + i, " ) : " + ShipT.toPolishName(type));
                i++;
            }


            tg.putString(2, 5 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.BLUE);
            tg.putString(7, 5 + i, String.valueOf(Board.WATER));
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(9, 5 + i, " ) : Woda ");

            tg.putString(2, 6 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(7, 6 + i, String.valueOf(Board.SHIP));
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(9, 6 + i, " ) : Statek ");


            tg.putString(2, 7 + i, "- ( ");
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(7, 7 + i, String.valueOf(Board.HIT));
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(9, 7 + i, " ) : Trafiony Statek ");


            tg.putString(2, 8 + i, "- ( ");
            tg.putString(7, 8 + i, String.valueOf(Board.MISS));
            tg.putString(9, 8 + i, " ) : Nietrafiony Strzał ");

        }
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printError(String message) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.RED);
        tg.putString(2, 22, message);
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printShot(Player player, Position position, boolean isHit) {
        TextGraphics tg = screen.newTextGraphics();

        if (player.getName().equals("COMPUTER")) {
            tg.putString(46, 15, player.getName() + " strzelił w " + position.toStringEncode(position));
            tg.setForegroundColor(TextColor.ANSI.BLUE);
            if (isHit) {
                tg.setForegroundColor(TextColor.ANSI.BLUE);
                tg.putString(50, 16, "Trafiony!");
            } else {
                tg.setForegroundColor(TextColor.ANSI.RED);
                tg.putString(50, 16, "NieTrafiony!");
            }
            tg.setForegroundColor(TextColor.ANSI.WHITE);

        } else {
            tg.putString(6, 15, player.getName() + " strzelił w " + position.toStringEncode(position));
            if (isHit) {
                tg.setForegroundColor(TextColor.ANSI.BLUE);
                tg.putString(10, 16, "Trafiony!");
            } else {
                tg.setForegroundColor(TextColor.ANSI.RED);
                tg.putString(10, 16, "NieTrafiony!");
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
        tg.putString(28, 15, "✔ " + player.getName() + " wygrał(a)!");
        if (rank != null) {
            tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            tg.putString(32, 17, "Twoj Wynik: " + rank.getPoints());
        }
        try {
            screen.refresh();
            Thread.sleep(3000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printShip(Ship ship, int numShipLeft) throws IOException {
        TextGraphics tg = screen.newTextGraphics();

        tg.putString(2, 15, "☛ " + ship.getName());
        tg.putString(2, 16, numShipLeft + " x ");

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(6, 16, ship.toGraphicLength());
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        screen.refresh();
    }

    public static void printBoards(Player firstPlayer, Player secondPlayer) {
        Board firstBoard = firstPlayer.getBoard();
//        Board secondBoard = secondPlayer.getBoard().getBoardHideShips();
        Board secondBoard = secondPlayer.getBoard();

        TextGraphics tg = screen.newTextGraphics();
        String letters = "abcdefghij";

        screen.clear();

        for (int i = 1; i <= 10; i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2 + i * 3, 2, i + "   ");
        }

        for (int i = 1; i <= 10; i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(40 + i * 3, 2, i + "   ");
        }

        for (int i = 0; i < firstBoard.getLength(); i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 3 + i, letters.charAt(i) + " ");

            for (int j = 0; j < firstBoard.getLength(); j++) {
                if (firstBoard.getBoard()[i][j] == Board.WATER) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE);
                    tg.putString(5 + j * 3, 3 + i, Board.WATER + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else if (firstBoard.getBoard()[i][j] == Board.HIT) {
                    tg.setForegroundColor(TextColor.ANSI.RED);
                    tg.putString(5 + j * 3, 3 + i, Board.HIT + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else if (firstBoard.getBoard()[i][j] == Board.MISS) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                    tg.putString(5 + j * 3, 3 + i, Board.MISS + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW);
                    tg.putString(5 + j * 3, 3 + i, firstBoard.getBoard()[i][j] + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                }
            }

            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(40, 3 + i, letters.charAt(i) + "   ");

            for (int j = 0; j < secondBoard.getLength(); j++) {
                if (secondBoard.getBoard()[i][j] == Board.WATER) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE);
                    tg.putString(43 + j * 3, 3 + i, Board.WATER + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else if (secondBoard.getBoard()[i][j] == Board.HIT) {
                    tg.setForegroundColor(TextColor.ANSI.RED);
                    tg.putString(43 + j * 3, 3 + i, Board.HIT + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else if (secondBoard.getBoard()[i][j] == Board.MISS) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                    tg.putString(43 + j * 3, 3 + i, Board.MISS + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW);
                    tg.putString(43 + j * 3, 3 + i, secondBoard.getBoard()[i][j] + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                }
            }
        }

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void printBoard(Board board) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        String letters = "abcdefghij";

        screen.clear();

        for (int i = 1; i <= 10; i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2 + i * 3, 2, i + "   ");
        }

        for (int i = 0; i < board.getLength(); i++) {
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 3 + i, letters.charAt(i) + " ");

            for (int j = 0; j < board.getLength(); j++) {
                if (board.getBoard()[i][j] == Board.WATER) {
                    tg.setForegroundColor(TextColor.ANSI.BLUE);
                    tg.putString(5 + j * 3, 3 + i, Board.WATER + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else if (board.getBoard()[i][j] == Board.HIT) {
                    tg.setForegroundColor(TextColor.ANSI.RED);
                    tg.putString(5 + j * 3, 3 + i, Board.HIT + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else if (board.getBoard()[i][j] == Board.MISS) {
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                    tg.putString(5 + j * 3, 3 + i, Board.MISS + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                } else {
                    tg.setForegroundColor(TextColor.ANSI.YELLOW);
                    tg.putString(5 + j * 3, 3 + i, board.getBoard()[i][j] + " ");
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                }
            }

        }

        screen.refresh();
    }

    public static void printRanking(Terminal terminal, int page) throws
            IOException, PositionException, InterruptedException {

        List<Ranking> rankings = Ranking.getRanking();

        Collections.sort(rankings, Collections.reverseOrder(Comparator.comparingInt(Ranking::getPoints)));

        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(33, 3, "RANKING", SGR.BOLD);
        tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
        tg.putString(20, 5, "Pozycja");
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(35, 5, "Nick");
        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        tg.putString(47, 5, "Punkty");
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        int i = 0;
        int itemsPerPage = 10; // Liczba wyników na stronie
        int startIndex = page * itemsPerPage; // Oblicz indeks początkowy na podstawie strony
        int endIndex = Math.min(startIndex + itemsPerPage, rankings.size()); // Oblicz indeks końcowy, nie przekraczając rozmiaru listy

        for (int j = startIndex; j < endIndex; j++) {
            i++;
            Ranking ranking = rankings.get(j);
            tg.putString(23, 6 + i, (j + 1) + "."); // +1, aby wyświetlać numerację od 1 na stronie
            tg.putString(33, 6 + i, ranking.getPlayer().getName());
            tg.putString(49, 6 + i, String.valueOf(ranking.getPoints()));
        }

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
                        if (page < rankings.size() / 10)
                            printRanking(terminal, ++page);
                    }
                }
        }

    }

    private static void printShop(Terminal terminal) throws IOException, PositionException, InterruptedException {
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
                        String name = Input.getUserInput(screen, terminal, "Podaj swoj nick, aby kupic w sklepie wybrana rzecz!");
                        String s;

                        if (selected == 0)
                            s = Ranking.enoughPoints(name, 500, screen, terminal, selected);
                        else
                            s = Ranking.enoughPoints(name, 300, screen, terminal, selected);


                        TextGraphics tg = screen.newTextGraphics();
                        if (s == null) {
                            tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                            tg.putString(20, 21, "Pomyslnie zakupiono wybrana rzecz!");
                        } else {
                            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.putString(20, 19, s);
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
        tg.putString(24, 1, "   _____ _    _  ____  _____  \n");
        tg.putString(24, 2, "  / ____| |  | |/ __ \\|  __ \\ \n");
        tg.putString(24, 3, " | (___ | |__| | |  | | |__) |\n");
        tg.putString(24, 4, "  \\___ \\|  __  | |  | |  ___/ \n");
        tg.putString(24, 5, "  ____) | |  | | |__| | |     \n");
        tg.putString(24, 6, " |_____/|_|  |_|\\____/|_|     \n");

        if (x == 0)
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);

        tg.putString(1, 9, "+~^~^~^~^^~^~^~^~^~^~^~^~~^~^~^~^~+");
        tg.putString(1, 10, "|                   _,:`,:'_=-    |");
        tg.putString(1, 11, "|                ,:',:@,:\"'       |");
        tg.putString(1, 12, "|             ,:',:`,:',?         |");
        tg.putString(1, 13, "|         __||_||_||_||__         |");
        tg.putString(1, 14, "|    ____[\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"]____    |");
        tg.putString(1, 15, "|    \\ \" '''''''''''''''''''' |   |");
        tg.putString(1, 16, "+~^~^~^~^^~^LOTNISKOWIEC^~^~^~^~^~+\n");
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(1, 18, "             Cena: 500");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        if (x == 1)
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        tg.putString(42, 8, "        _.,,,,,,,,,._\n");
        tg.putString(42, 9, "     .d''            ``b.\n");
        tg.putString(42, 10, "   .p'     Obiekty     `q.\n");
        tg.putString(42, 11, " .d'     w magicznym     `b.\n");
        tg.putString(42, 12, ".d'  polu moga byc mniej  `b.\n");
        tg.putString(42, 13, " ::  podatne na obrazenia  ::\n");
        tg.putString(42, 14, " `p.  rakietowe (działa  .q'\n");
        tg.putString(42, 15, "  `p.   na pierwszych   .q'\n");
        tg.putString(42, 16, "   `b.    5 strzałów   .d'\n");
        tg.putString(42, 17, "     `q..            ..,'\n");
        tg.putString(42, 18, "        '',,,,,,,,,,''\n");
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(42, 20, "           Cena: 300");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);


        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        tg.putString(4, 22, "KUPIONE RZECZY W SKLEPIE OBOWIAZUJĄ TYLKO PRZEZ PIERWSZA ZAGRANA GRE!!!");

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
