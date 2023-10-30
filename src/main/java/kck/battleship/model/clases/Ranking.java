package kck.battleship.model.clases;

import java.io.*;

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

    public void setPoints(int points) {
        this.points += points;
    }

    public Player getPlayer() {
        return player;
    }

    public void save() {
        try {
            File plik = new File("src/main/java/kck/battleship/model/data/ranking.txt");

            // Tworzenie pliku, jeśli nie istnieje
            if (!plik.exists()) {
                plik.createNewFile();
            }

            // Otwarcie pliku do dopisywania
            FileWriter fileWriter = new FileWriter(plik, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Dopisanie tekstu do pliku
            bufferedWriter.write(points + " " + player.getName());
            bufferedWriter.newLine(); // Dodanie nowej linii, jeśli to konieczne

            // Zamknięcie strumieni
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
