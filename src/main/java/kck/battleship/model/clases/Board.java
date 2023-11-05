package kck.battleship.model.clases;

import kck.battleship.exceptions.BoardException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.enum_.Direction;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int length;
    private char[][] board;
    private int numberShips = 0;
    public static final char HIT = '✘';
    public static final char MISS = '◉';
    public static final char SHIP = '⎕';
    public static final char WATER = 'ℳ';

    public Board(int length) {
        this.length = length;
        board = fillWater();
    }

    public Board(char[][] matrix) {
        this.length = matrix.length;
        board = matrix;
    }

    public int getLength() {
        return length;
    }

    public int getNumberShips() {
        return numberShips;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean set(char status, Position position) {
        board[position.getRow()][position.getColumn()] = status;
        return true;
    }

    public char at(Position position) {
        return board[position.getRow()][position.getColumn()];
    }

    public boolean IsShip(Position position) {
        return at(position) == SHIP;
    }

    public boolean IsWater(Position position) {
        return at(position) == WATER;
    }

    public boolean IsMiss(Position position) {
        return at(position) == MISS;
    }

    public boolean IsHit(Position position) {
        return at(position) == HIT;
    }

    public boolean IsSpace(Ship ship) {
        int l = ship.getLength();
        int x = ship.getPosition().getRow();
        int y = ship.getPosition().getColumn();
        if (ship.getDirection() == Direction.HORIZONTAL) return (length - y + 1) > l;
        else return (length - x + 1) > l;
    }

    private char[][] fillWater() {
        char[][] matrix = new char[length][length];
        for (char[] row : matrix) {
            Arrays.fill(row, WATER);
        }
        return matrix;
    }

    public ArrayList<Position> getAdjacentValidPositions(Position position) throws PositionException {
        int row = position.getRow(), column = position.getColumn();
        ArrayList<Position> adjacentPositions = new ArrayList<>();

        // Sprawdź północ
        addIfValidAndNotMissOrHit(adjacentPositions, row - 1, column);
        // Sprawdź zachód
        addIfValidAndNotMissOrHit(adjacentPositions, row, column - 1);
        // Sprawdź południe
        addIfValidAndNotMissOrHit(adjacentPositions, row + 1, column);
        // Sprawdź wschód
        addIfValidAndNotMissOrHit(adjacentPositions, row, column + 1);

        return adjacentPositions;
    }

    private void addIfValidAndNotMissOrHit(ArrayList<Position> list, int row, int column) throws PositionException {
        if (row >= 0 && row < length && column >= 0 && column < length) {
            Position newPosition = new Position(row, column);
            if (!IsMiss(newPosition) && !IsHit(newPosition))
                list.add(newPosition);
        }
    }

    public ArrayList<Position> getAdjacentPositions(int row, int column) throws PositionException {
        ArrayList<Position> adjacentPositions = new ArrayList<>();

        addIfValid(adjacentPositions, row - 1, column); // Północ
        addIfValid(adjacentPositions, row + 1, column); // Południe
        addIfValid(adjacentPositions, row, column + 1); // Wschód
        addIfValid(adjacentPositions, row, column - 1); // Zachód
        addIfValid(adjacentPositions, row - 1, column + 1); // Północ-Wschód
        addIfValid(adjacentPositions, row - 1, column - 1); // Północ-Zachód
        addIfValid(adjacentPositions, row + 1, column + 1); // Południe-Wschód
        addIfValid(adjacentPositions, row + 1, column - 1); // Południe-Zachód

        return adjacentPositions;
    }

    private void addIfValid(ArrayList<Position> list, int row, int column) throws PositionException {
        if (row >= 0 && row < length && column >= 0 && column < length)
            list.add(new Position(row, column));
    }

    private boolean isShipAround(int row, int column) throws PositionException {
        ArrayList<Position> list = getAdjacentPositions(row, column);
        for (Position position : list)
            if (at(position) == SHIP) return true;
        return false;
    }

    public boolean isNearShip(Ship ship) throws PositionException {
        int row = ship.getPosition().getRow();
        int column = ship.getPosition().getColumn();

        int k = (ship.getDirection() == Direction.HORIZONTAL) ? column : row;

        for (int i = 0; i < ship.getLength() && k + i < length - 1; i++) {
            if (isShipAround(row, column)) {
                return true;
            }

            if (ship.getDirection() == Direction.HORIZONTAL)
                column++;
            else if (ship.getDirection() == Direction.VERTICAL)
                row++;
        }
        return false;
    }

    public boolean addShip(Ship ship) throws BoardException, PositionException {
        int row = ship.getPosition().getRow();
        int column = ship.getPosition().getColumn();

        if (IsShip(ship.getPosition())) throw new BoardException("Błąd, istnieje już statek na tej pozycji");

        if (IsSpace(ship)) {
            if (!isNearShip(ship)) {
                int k = (ship.getDirection() == Direction.HORIZONTAL) ? column : row;
                for (int i = 0; i < ship.getLength() && k + i < length; i++) {
                    if (ship.getDirection() == Direction.HORIZONTAL) {
                        if (i == 0) k = column;
                        board[row][column + i] = SHIP;
                    } else if (ship.getDirection() == Direction.VERTICAL) {
                        if (i == 0) k = row;
                        board[row + i][column] = SHIP;
                    }
                    numberShips++;
                }
                return true;
            } else throw new BoardException("Błąd, inny statek znajduje się w pobliżu");
        } else throw new BoardException("Błąd, inny statek znajduje się w pobliżu");
    }

    public boolean addHit(Position position) throws BoardException {
        if (IsShip(position)) {
            numberShips--;
            return set(HIT, position);
        } else if (IsWater(position)) return set(MISS, position);
        else throw new BoardException("Błąd, już strzelałeś w tą pozycję");
    }

    public Board getBoardHideShips() throws PositionException {
        char[][] matrix = new char[length][length];
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                if (!IsShip(new Position(i, j)))
                    matrix[i][j] = at(new Position(i, j));
                else matrix[i][j] = WATER;

        return new Board(matrix);
    }

    public void reset() {
        board = fillWater();
        numberShips = 0;
    }
}
