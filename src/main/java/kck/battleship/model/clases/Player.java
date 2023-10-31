package kck.battleship.model.clases;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import kck.battleship.exceptions.BoardException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.enum_.Direction;
import kck.battleship.model.enum_.ShipT;
import kck.battleship.view.Display;
import kck.battleship.view.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Player {
    private String name;
    private final Board board = new Board(10);
    private final ArrayList<Position> shoots = new ArrayList<>();
    private final ArrayList<Position> nextShots = new ArrayList<>();
    private final boolean isAI;
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
        for (ShipT type : ShipT.values())
            for (int i = 0; i < type.getNumberShips(); i++)
                list.add(new Ship(ShipT.toPolishName(type), type.getShipLength()));
        return list;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
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

    public void addShips(Screen screen, Terminal terminal) throws IOException, InterruptedException {
        if (!isAI) {
            boolean isAdded;
            Position position;
            Direction direction;
            String messageInputPosition = "Wprowadź współrzędną (np. A1): ";
            String messageInputDirection = "Wprowadź kierunek (h/v): ";
            ArrayList<Ship> list = initShips();
            for (int i = 0; i < list.size(); i++) {
                Ship ship = list.get(i);
                do {
                    Display.printBoard(board);
                    Display.printShip(ship, countShip(list, ship.getLength()));

                    position = Input.readPosition(screen, terminal, board, messageInputPosition);
                    direction = Input.readDirection(screen, terminal, messageInputDirection);
                    ship.setPosition(position);
                    ship.setDirection(direction);

                    try {
                        isAdded = board.addShip(ship);
                    } catch (BoardException | PositionException e) {
                        Display.printError(e.toString());
                        isAdded = false;
                        Thread.sleep(2000);
                    }
                } while (!isAdded);
                list.remove(i);
                i--;
            }
            Display.printBoard(board);
        } else randAddShips();
    }

    public void randAddShips() {
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
        for (Ship ship : ships)
            if (ship.getLength() == length) count++;
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
            lastPos = getLastShoot();
            nextShots.addAll(boardEnemy.getPossiblePositions(lastPos));
            if (nextShots.isEmpty()) return randPosition();
            nextPos = nextShots.get(0);
            nextShots.remove(0);
            return nextPos;
        }
    }

    public Position shoot(Screen screen, Terminal terminal, Board boardEnemy) throws PositionException {
        if (isAI) return shootAI(boardEnemy);
        else return Input.readPosition(screen, terminal, board, name + ", gdzie chcesz strzelić? ");
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