package kck.battleship.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.clases.*;
import kck.battleship.model.enum_.ShipT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Display {
    private static Screen screen;

    private final List<String> menuList = new ArrayList<>(Arrays.asList("Rozpocznij Grę", "Symuluj Grę", "Zasady Gry", "Ranking", "Wyjście"));

    public Display(Screen screen) {
        this.screen = screen;
    }

    public void printHomePage() {
        TextGraphics tg = screen.newTextGraphics();

        printTitle();

        tg.putString(16, 16, "Jeśli chcesz przejść dalej, kliknij");
        tg.putString(52, 16, "ENTER", SGR.BLINK);
        tg.putString(58, 16, "...");

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printTitle() {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(6, 4, "  ____    _  _____ _____ _     _____   ____  _   _ ___ ____  ____");
        tg.putString(6, 5, " | __ )  / \\|_   _|_   _| |   | ____| / ___|| | | |_ _|  _ \\/ ___|");
        tg.putString(6, 6, " |  _ \\ / _ \\ | |   | | | |   |  _|   \\___ \\| |_| || || |_) \\___ \\ \n");
        tg.putString(6, 7, " | |_) / ___ \\| |   | | | |___| |___   ___) |  _  || ||  __/ ___) |\n");
        tg.putString(6, 8, " |____/_/   \\_\\_|   |_| |_____|_____| |____/|_| |_|___|_|   |____/ \n");
    }

    private void printMenu() {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(24, 10, " __  __  ____  _  _  __  __ \n");
        tg.putString(24, 11, "(  \\/  )( ___)( \\( )(  )(  )\n");
        tg.putString(24, 12, " )    (  )__)  )  (  )(__)( \n");
        tg.putString(24, 13, "(_/\\/\\_)(____)(_)\\_)(______)\n");
    }

    public void waitForKeyHomePage(Terminal terminal) throws IOException {
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

    private void printMenuPage(int selected) {
        screen.clear();

        printTitle();
        printMenu();

        for (int i = 0; i < menuList.size(); i++) {
            TextGraphics tg = screen.newTextGraphics();

            if (i == selected) {
                tg.setForegroundColor(TextColor.ANSI.MAGENTA);
                if (i != menuList.size() - 1)
                    tg.putString(32 + i, i + 16, menuList.get(i), SGR.BOLD);
                else
                    tg.putString(31 + i, i + 16, menuList.get(i), SGR.BOLD);
            } else {
                if (i != menuList.size() - 1)
                    tg.putString(32 + i, i + 16, menuList.get(i));
                else
                    tg.putString(31 + i, i + 16, menuList.get(i));
            }

            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printExit() {
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

    public void chooseOption(Terminal terminal, int selected) throws IOException {
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

    private void option(int selected, Terminal terminal) throws IOException {
        for (int i = 0; i < menuList.size(); i++) {
            if (i == selected)
                switch (menuList.get(i)) {
                    case "Rozpocznij Grę" -> {

                    }
                    case "Symuluj Grę" -> {

                    }
                    case "Zasady Gry" -> {
                        printRules(terminal);
                    }
                    case "Ranking" -> {

                    }
                    case "Wyjście" -> {
                        printExit();
                    }
                }
        }
    }

    private void printRules(Terminal terminal) throws IOException {
        printInfoRules(1);

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
                    case ArrowUp -> {
                        printInfoRules(1);
                    }
                    case ArrowDown -> {
                        printInfoRules(2);
                    }
                }
        }
    }

    private void printInfoRules(int x) {
        TextGraphics tg = screen.newTextGraphics();
        screen.clear();

        if (x == 1) {
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);
            tg.putString(30, 1, "Jak wygrać?", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 3, "- Każdy gracz ma pole bitwy reprezentowane przez siatkę 10x10 (domyślną),");
            tg.putString(2, 4, "   na której rozmieszcza " + ShipT.sizeAllShips() + " statków, ukrytych przed przeciwnikiem.");
            tg.putString(2, 5, "- Celem gry jest zatopienie wszystkich statków przeciwnika!");
            tg.putString(2, 6, "- Np. statek typu " + ShipT.values()[0] + ", który zajmuje " + ShipT.values()[0].getShipLength() + " pól,");
            tg.putString(2, 7, "   zostaje zatopiony po dwóch trafieniach.");
            tg.putString(2, 8, "- Wszystkie " + ShipT.sizeAllShips() + " statki zajmują łącznie " + ShipT.lengthAllShips() + " pól,");
            tg.putString(2, 9, "   więc pierwszy gracz, który odnotuje " + ShipT.lengthAllShips() + " trafień, wygrywa!");

            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(30, 11, "Rozgrywka", SGR.BOLD, SGR.ITALIC);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(2, 13, "- Postępuj zgodnie z instrukcjami w celu skonfigurowania swoich " + ShipT.sizeAllShips() + " statków.");
            tg.putString(2, 14, "- Umieścić statek podając współrzędną początkową (A1-J10) i kierunek (h/v).");
            tg.putString(2, 15, "- Statki nie mogą na siebie nachodzić ani stykać się.");
            tg.putString(2, 16, "- Gdy obaj gracze skonfigurują swoje statki, bitwa się rozpoczyna!");
            tg.putString(2, 17, "- Wystrzel rakiety w statki przeciwnika, zgadując współrzędne.");
            tg.putString(2, 18, "- Zostaniesz poinformowany, czy trafiłeś w statek, czy nie.");
            tg.putString(2, 19, "- Zatop wszystkie " + ShipT.sizeAllShips() + " statków komputera, aby wygrać!");
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
        System.out.println(DisplayColors.ANSI_RED + message + DisplayColors.ANSI_RESET);
    }

    public static void printShot(Player player, Position position, boolean isHit) {
        System.out.println("- " + player.getName() + " strzelił w " + position.toStringEncode(position) + ": " +
                (isHit ? DisplayColors.ANSI_BLUE + "Trafiony!" + DisplayColors.ANSI_RESET :
                        DisplayColors.ANSI_RED + "Nietrafiony!" + DisplayColors.ANSI_RESET));
    }

    public static void printWinner(Player player, Ranking rank) {
        System.out.println(DisplayColors.ANSI_BLUE + "\n✔ " + player.getName() + " wygrał(a)!" + DisplayColors.ANSI_RESET + "\n");
        System.out.println(DisplayColors.ANSI_YELLOW + "Twoj Wynik: " + rank.getPoints() + DisplayColors.ANSI_RESET + "\n");
        System.out.print("\nNaciśnij dowolny klawisz, aby kontynuować...");
        new Scanner(System.in).nextLine();
    }

    public static void printCurrentShip(Ship ship, int numShipLeft) {
        System.out.println("☛ " + ship.getName() + " (" +
                DisplayColors.ANSI_YELLOW + ship.toGraphicLength() + DisplayColors.ANSI_RESET +
                ") x" + numShipLeft);
    }

    public static void printAdjacentBoard(Player pOne, Player pTwo) throws PositionException {
        System.out.println(toStringAdjacentBoard(pOne, pTwo));
    }

    public static String toStringAdjacentBoard(Player pOne, Player pTwo) throws PositionException {
        Board firstBoard = pOne.getBoard();
        Board secondBoard = pTwo.getBoard().getBoardHideShips();
//        Board secondBoard = pTwo.getBoard();
        String letters = "abcdefghij";
        String s = "\n――――――――――――――――――――――――――――――――――\n";
        s += "\n    ";

        for (int i = 1; i <= 10; i++)
            s += i + "   ";

        s += "      ";

        for (int i = 1; i <= 10; i++)
            s += i + "   ";

        s += "\n";
        for (int i = 0; i < firstBoard.getLength(); i++) {
            s += DisplayColors.ANSI_WHITE;
            s += letters.charAt(i) + "   ";
            s += DisplayColors.ANSI_RESET;

            for (int j = 0; j < firstBoard.getLength(); j++) {
                if (firstBoard.getBoard()[i][j] == Board.WATER)
                    s += DisplayColors.ANSI_BLUE + Board.WATER + "   " + DisplayColors.ANSI_RESET;
                else if (firstBoard.getBoard()[i][j] == Board.HIT)
                    s += DisplayColors.ANSI_RED + Board.HIT + "   " + DisplayColors.ANSI_RESET;
                else if (firstBoard.getBoard()[i][j] == Board.MISS) s += Board.MISS + "   " + DisplayColors.ANSI_RESET;
                else s += DisplayColors.ANSI_YELLOW + firstBoard.getBoard()[i][j] + "   " + DisplayColors.ANSI_RESET;
            }

            s += "   ";

            s += DisplayColors.ANSI_WHITE;
            s += DisplayColors.ANSI_WHITE;
            s += letters.charAt(i) + "   ";
            s += DisplayColors.ANSI_RESET;

            for (int j = 0; j < secondBoard.getLength(); j++) {
                if (secondBoard.getBoard()[i][j] == Board.WATER)
                    s += DisplayColors.ANSI_BLUE + Board.WATER + "   " + DisplayColors.ANSI_RESET;
                else if (secondBoard.getBoard()[i][j] == Board.HIT)
                    s += DisplayColors.ANSI_RED + Board.HIT + "   " + DisplayColors.ANSI_RESET;
                else if (secondBoard.getBoard()[i][j] == Board.MISS) s += Board.MISS + "   " + DisplayColors.ANSI_RESET;
                else s += DisplayColors.ANSI_YELLOW + secondBoard.getBoard()[i][j] + "   " + DisplayColors.ANSI_RESET;
            }

            s += "\n";
        }
        s += "\n――――――――――――――――――――――――――――――――――\n";
        return s;
    }

    public static void printBoard(Board board) {
        System.out.println(toStringBoard(board));
    }

    public static String toStringBoard(Board board) {
        String letters = "abcdefghij";
        String s = "\n    ";

        for (int i = 1; i <= 10; i++)
            s += i + "   ";

        s += "\n";
        for (int i = 0; i < board.getLength(); i++) {
            s += DisplayColors.ANSI_WHITE;
            s += letters.charAt(i) + "   ";

            for (int j = 0; j < board.getLength(); j++) {
                if (board.getBoard()[i][j] == Board.WATER)
                    s += DisplayColors.ANSI_BLUE + Board.WATER + "   " + DisplayColors.ANSI_RESET;
                else if (board.getBoard()[i][j] == Board.HIT)
                    s += DisplayColors.ANSI_RED + Board.HIT + "   " + DisplayColors.ANSI_RESET;
                else if (board.getBoard()[i][j] == Board.MISS)
                    s += DisplayColors.ANSI_WHITE + Board.MISS + "   " + DisplayColors.ANSI_RESET;
                else s += DisplayColors.ANSI_YELLOW + board.getBoard()[i][j] + "   " + DisplayColors.ANSI_RESET;
            }
            s += "\n";
        }
        return s;
    }

    public static void printRanking() {
        String fileName = "/Users/mateusz/Desktop/_BattleShips/src/main/java/kck/battleship/model/data/ranking.txt"; // Nazwa pliku, w którym zapisywane są wyniki

        List<Ranking> rankings = new ArrayList<>();

        try {
            File plik = new File(fileName);
            FileReader fileReader = new FileReader(plik);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linia;
            while ((linia = bufferedReader.readLine()) != null) {
                String[] parts = linia.split(" ");
                if (parts.length == 2) {
                    try {
                        int punkty = Integer.parseInt(parts[0]);
                        String playerName = parts[1];
                        Player player = new Player(playerName);
                        rankings.add(new Ranking(player, punkty));
                    } catch (NumberFormatException e) {
                        System.err.println("Błąd parsowania punktów w linii: " + linia);
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(rankings, Collections.reverseOrder(Comparator.comparingInt(Ranking::getPoints)));
        System.out.println("\n              " + DisplayColors.ANSI_BLUE + "Ranking" + DisplayColors.ANSI_RESET);
        System.out.println(DisplayColors.ANSI_BLUE + "Position" + DisplayColors.ANSI_CYAN + "    Points        " + DisplayColors.ANSI_GREEN + "Name" + DisplayColors.ANSI_RESET);
        int i = 0;
        for (Ranking ranking : rankings) {
            i++;
            System.out.println("    " + i + "          " + ranking.getPoints() + "          " + ranking.getPlayer().getName());
        }


        System.out.print("\nNaciśnij dowolny klawisz, aby kontynuować...");
        new Scanner(System.in).nextLine();
    }
}
