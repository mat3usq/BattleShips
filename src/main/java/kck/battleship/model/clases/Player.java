package kck.battleship.model.clases;

import kck.battleship.exceptions.BoardException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.enum_.Direction;
import kck.battleship.model.enum_.ShipT;
import kck.battleship.view.Display;
import kck.battleship.view.Input;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private String name;
    private final Board board = new Board(10);
    private final ArrayList<Position> shoots = new ArrayList<>();
    private ArrayList<Position> nextTargets = new ArrayList<>();
    private boolean isAI;
    private Date lastShootTime;


    public Player(String name) {
        this.name = name;
        isAI = false;
        lastShootTime = new Date();
    }

    public Player(String name, boolean isAI) {
        this.name = name;
        this.isAI = isAI;
    }

    private ArrayList<Ship> initShips() {
        ArrayList<Ship> list = new ArrayList<>();
        for (ShipT type : ShipT.values()) {
            for (int i = 0; i < type.getNumberShips(); i++) {
                list.add(new Ship(ShipT.toEnglishName(type), type.getShipLength()));
            }
        }
        return list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Position> getShoots() {
        return shoots;
    }

    public void setAI(boolean AI) {
        isAI = AI;
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

    public void addAllShips() {
        if (!isAI) {
            boolean isAdded;
            Position position;
            Direction direction;
            String messageInputPosition = "- Wprowadź współrzędną (np. A1): ";
            String messageInputDirection = "- Wprowadź kierunek (h/v): ";
            Scanner sc = new Scanner(System.in);
            ArrayList<Ship> list = initShips();
            for (int i = 0; i < list.size(); i++) {
                Ship ship = list.get(i);
                do {
                    Display.printBoard(board);
                    Display.printCurrentShip(ship, countShip(list, ship.getLength()));

                    position = Input.readPosition(sc, board, messageInputPosition);
                    direction = Input.readDirection(sc, messageInputDirection);
                    ship.setPosition(position);
                    ship.setDirection(direction);

                    try {
                        isAdded = board.addShip(ship);
                    } catch (BoardException | PositionException e) {
                        Display.printError(e.toString());
                        isAdded = false;
                    }
                } while (!isAdded);
                list.remove(i);
                i--;
            }
            Display.printBoard(board);
        } else randAddAllShips();
    }

    public void randAddAllShips() {
        Random random = new Random();
        ArrayList<Ship> list = initShips();

        boolean isAdded;
        Position position;
        Direction direction;
        int deadlock = 0, limit = 1000;

        for (int i = 0; i < list.size(); i++) {
            Ship ship = list.get(i);
            deadlock = 0;
            do {
                try {
                    position = new Position(random.nextInt(board.getLength()), random.nextInt(board.getLength()));
                    direction = random.nextBoolean() ? Direction.VERTICAL : Direction.HORIZONTAL;
                    ship.setPosition(position);
                    ship.setDirection(direction);
                    isAdded = board.addShip(ship);
                } catch (BoardException | PositionException e) {
                    isAdded = false;
                }
                if (!isAdded) deadlock++;
                if (deadlock > limit) {
                    reset();
                    i = -1;
                    break;
                }
            } while (!isAdded);
        }
    }

    public boolean hasShipsLive() {
        return board.getNumberShips() > 0;
    }

    private int countShip(ArrayList<Ship> ships, int length) {
        int count = 0;
        for (Ship ship : ships) {
            if (ship.getLength() == length) count++;
        }
        return count;
    }

    public int shipsLeft() {
        return board.getNumberShips();
    }

    private Position randPosition() throws PositionException {
        Random random = new Random();
        int x = random.nextInt(board.getLength());
        int y = random.nextInt(board.getLength());
        return new Position(x, y);
    }

    public boolean addShoot(Position pos) throws BoardException {
        return board.addHit(pos);
    }

    public Position shootAI(Board boardEnemy) throws PositionException {
        Position lastPos, nextPos;
        if (shoots.isEmpty()) return randPosition();
        else {
            lastPos = getLastShoot(); //last hit
            nextTargets.addAll(boardEnemy.getPossibleTarget(lastPos));
            if (nextTargets.isEmpty()) return randPosition();
            nextPos = nextTargets.get(0);
            nextTargets.remove(0);
            return nextPos;
        }
    }

    public Position shoot(Board boardEnemy) throws PositionException {
        if (isAI) return shootAI(boardEnemy);
        else {
            Scanner sc = new Scanner(System.in);
            return Input.readPosition(sc, board, "- " + name + ", gdzie chcesz strzelić? ");
        }
    }

    public void registerShoot(Position position) {
        shoots.add(position);
    }

    public Position getLastShoot() {
        if (shoots.isEmpty()) return null;
        return shoots.get(shoots.size() - 1);
    }

    private void reset() {
        board.reset();
    }
}
