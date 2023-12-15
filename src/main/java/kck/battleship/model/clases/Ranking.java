package kck.battleship.model.clases;

import kck.battleship.view.UserInput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ranking {
    public Player player;
    private int points = 0;

    public Ranking(Player player, int points) {
        this.player = player;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Player getPlayer() {
        return player;
    }

    public void save() {
        List<Ranking> rankings = getRanking();
        boolean isPlayer = false;
        for (Ranking r : rankings)
            if (r.player.getName().equals(player.getName())) {
                r.setPoints(r.getPoints() + points);
                isPlayer = true;
                break;
            }

        if (!isPlayer)
            rankings.add(new Ranking(player, points));

        saveRanking(rankings);
    }

    private static void saveRanking(List<Ranking> rankings) {
        String fileName = "src/main/java/kck/battleship/model/data/ranking.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Ranking ranking : rankings) {
                String line = ranking.points + " " + ranking.player.getName();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ranking> getRanking() {
        String fileName = "src/main/java/kck/battleship/model/data/ranking.txt";

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

        return rankings;
    }

    private static void saveBuyer(String name, int x) {
        try {
            File plik = new File("src/main/java/kck/battleship/model/data/shop.txt");

            if (!plik.exists()) {
                plik.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(plik, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(name + " " + x);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String enoughPoints(String name, int price, int x) throws IOException, InterruptedException {
        List<Ranking> rankings = getRanking();

        for (Ranking r : rankings)
            if (name.equals(r.player.getName())) {
                if (r.getPoints() >= price) {
                    boolean response = UserInput.question("Czy chcesz napewno to kupic(y/n)?");

                    if (response) {
                        r.setPoints(r.getPoints() - price);
                        if (r.getPoints() == 0)
                            rankings.remove(r);

                        saveRanking(rankings);
                        saveBuyer(name, x);

                        return null;
                    } else return "Do Zobaczenia innym razem!";
                } else return "Masz za malo punktow, aby to kupic!";
            }
        return "Nie znaleziono gracza o takim Nick'u!";
    }
}
