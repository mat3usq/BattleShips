package kck.battleship.model.clases;

import kck.battleship.controller.GameException;
import kck.battleship.controller.ViewController;
import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.types.TypesShips;
import kck.battleship.view.textView.UserInput;
import kck.battleship.view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Player {
    private String name;
    private final boolean isAI;
    public boolean hasAirCrafter = false;
    private int durabilityForceField = 0;
    private Date lastShootTime;
    private final BattleField battleField = new BattleField();
    private final ArrayList<Position> shoots = new ArrayList<>();
    private final ArrayList<Position> nextShoots = new ArrayList<>();
    private final ArrayList<Ship> ships = createShips();
    private static final View view = ViewController.getInstance();

    public Player(String name) {
        this.name = name;
        isAI = false;
        lastShootTime = new Date();
    }

    public Player(String name, boolean isAI) {
        this.name = name;
        this.isAI = isAI;
    }

    public String getName() {
        return name;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public boolean isAI() {
        return isAI;
    }

    public Date getLastShootTime() {
        return lastShootTime;
    }

    public void setLastShootTime(Date lastShootTime) {
        this.lastShootTime = lastShootTime;
    }

    public int getDurabilityForceField() {
        return durabilityForceField;
    }

    public void setDurabilityForceField(int durabilityForceField) {
        this.durabilityForceField = durabilityForceField;
    }

    public void addShips() throws IOException{
        if (!isAI) {
            if (hasAirCrafter)
                ships.add(new Ship("LOTNISKOWIEC", 5));

            addShipManually();

            view.printBoard(battleField, ships);
        } else randAddShips();
    }

    private void addShipManually() throws IOException {
        view.showOptionToManuallyAddShip();
        ViewController.addShipManually(battleField, ships);
    }

    public void randAddShips() {
        Random random = new Random();
        if (hasAirCrafter)
            ships.add(new Ship("LOTNISKOWIEC", 5));

        for (Ship ship : ships)
            addShipRandomly(random, ship);
    }

    private void addShipRandomly(Random random, Ship ship) {
        boolean addedSuccessfully = false;
        int failedAttempts = 0;
        int limit = 10000;

        while (failedAttempts <= limit) {
            try {
                ship.setPosition(Position.randPosition());
                ship.setDirection(random.nextBoolean() ? TypesDirection.VERTICAL : TypesDirection.HORIZONTAL);
                addedSuccessfully = battleField.addShip(ship);
            } catch (GameException ignored) {
            }

            if (addedSuccessfully)
                break;

            failedAttempts++;
        }

        if (failedAttempts > limit) {
            reset();
            randAddShips();
        }
    }

    private ArrayList<Ship> createShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        for (TypesShips type : TypesShips.values())
            for (int i = 0; i < type.getNumberShips(); i++)
                ships.add(new Ship(TypesShips.toPolishName(type), type.getShipLength()));
        return ships;
    }

    public boolean areShipsStillSailing() {
        return battleField.getNumberShips() > 0;
    }

    public int shipsLeft() {
        return battleField.getNumberShips();
    }

    public boolean addShoot(Position shoot) throws GameException {
        return battleField.addHit(shoot);
    }

    public Position ComputerShoot(BattleField defenderBattleField) throws GameException {
        if (shoots.isEmpty()) return Position.randPosition();
        else {
            nextShoots.addAll(defenderBattleField.getAdjacentValidPositions(getLastShoot()));

            if (nextShoots.isEmpty())
                return Position.randPosition();

            Position nextPos = nextShoots.get(0);
            nextShoots.remove(0);
            return nextPos;
        }
    }

    public Position shoot(BattleField defenderBattleField) throws GameException {
        if (isAI) return ComputerShoot(defenderBattleField);
        else return UserInput.readPositionToShot(defenderBattleField);
    }

    public void registerShoot(Position position) {
        shoots.add(position);
    }

    public Position getLastShoot() {
        if (shoots.isEmpty()) return null;
        return shoots.get(shoots.size() - 1);
    }

    private void reset() {
        battleField.reset();
    }

    public void getShop() {
        String fileName = "src/main/java/kck/battleship/model/data/shop.txt";

        try {
            File plik = new File(fileName);
            FileReader fileReader = new FileReader(plik);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linia;
            while ((linia = bufferedReader.readLine()) != null) {
                String[] parts = linia.split(" ");
                if (parts.length == 2) {
                    try {
                        String playerName = parts[0];
                        int shopOption = Integer.parseInt(parts[1]);

                        if (playerName.equals(name))
                            if (shopOption == 0)
                                hasAirCrafter = true;
                            else if (shopOption == 1) {
                                durabilityForceField = 5;
                            }
                    } catch (NumberFormatException e) {
                        System.err.println("Błąd parsowania punktów w linii: " + linia);
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        removeShopEntries();
    }

    private void removeShopEntries() {
        String fileName = "src/main/java/kck/battleship/model/data/shop.txt";

        try {
            File inputFile = new File(fileName);
            File tempFile = new File(fileName + "_temp");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] parts = linia.split(" ");
                if (parts.length == 2) {
                    try {
                        String playerName = parts[0];
                        int shopOption = Integer.parseInt(parts[1]);

                        if (!(playerName.equals(name) && (shopOption == 0 || shopOption == 1))) {
                            writer.write(linia + System.lineSeparator());
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Błąd parsowania punktów w linii: " + linia);
                    }
                }
            }

            reader.close();
            writer.close();

            if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                System.out.println("Usunięto wiersze spełniające warunek.");
            } else {
                System.err.println("Błąd usuwania wierszy.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}