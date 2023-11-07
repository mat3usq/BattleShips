package kck.battleship.model.clases;

import kck.battleship.controller.GameException;
import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.types.TypesField;

import java.util.ArrayList;
import java.util.Arrays;

public class BattleField {
    private char[][] battleField;
    private static final int length = 10;
    private int numberShips = 0;

    public BattleField() {
        battleField = fillWater();
    }

    public BattleField(char[][] matrix) {
        battleField = matrix;
    }

    public static int getLength() {
        return length;
    }

    public int getNumberShips() {
        return numberShips;
    }

    public char[][] getbattleField() {
        return battleField;
    }

    public boolean set(char status, Position position) {
        battleField[position.getRow()][position.getColumn()] = status;
        return true;
    }

    public char at(Position position) {
        return battleField[position.getRow()][position.getColumn()];
    }

    private char[][] fillWater() {
        char[][] matrix = new char[length][length];
        for (char[] row : matrix) {
            Arrays.fill(row, TypesField.WATER.name);
        }
        return matrix;
    }

    public boolean IsSpace(Ship ship) {
        int l = ship.getLength();
        int x = ship.getPosition().getRow();
        int y = ship.getPosition().getColumn();
        if (ship.getDirection() == TypesDirection.HORIZONTAL) return (length - y + 1) > l;
        else return (length - x + 1) > l;
    }

    public ArrayList<Position> getAdjacentValidPositions(Position position) throws GameException {
        int row = position.getRow(), column = position.getColumn();
        ArrayList<Position> adjacentPositions = new ArrayList<>();

        addIfValidAndNotMissOrHit(adjacentPositions, row - 1, column);
        addIfValidAndNotMissOrHit(adjacentPositions, row, column - 1);
        addIfValidAndNotMissOrHit(adjacentPositions, row + 1, column);
        addIfValidAndNotMissOrHit(adjacentPositions, row, column + 1);

        return adjacentPositions;
    }

    private void addIfValidAndNotMissOrHit(ArrayList<Position> list, int row, int column) throws GameException {
        if (row >= 0 && row < length && column >= 0 && column < length) {
            Position newPosition = new Position(row, column);
            if (!(at(newPosition) == TypesField.MISS.name) && !(at(newPosition) == TypesField.HIT.name))
                list.add(newPosition);
        }
    }

    public ArrayList<Position> getAdjacentPositions(int row, int column) throws GameException {
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

    private void addIfValid(ArrayList<Position> list, int row, int column) throws GameException {
        if (row >= 0 && row < length && column >= 0 && column < length)
            list.add(new Position(row, column));
    }

    private boolean isShipAround(int row, int column) throws GameException {
        ArrayList<Position> list = getAdjacentPositions(row, column);
        for (Position position : list)
            if (at(position) == TypesField.SHIP.name) return true;
        return false;
    }

    public boolean isNearShip(Ship ship) throws GameException {
        int row = ship.getPosition().getRow();
        int column = ship.getPosition().getColumn();

        int k = (ship.getDirection() == TypesDirection.HORIZONTAL) ? column : row;

        for (int i = 0; i < ship.getLength() && k + i < length - 1; i++) {
            if (isShipAround(row, column)) {
                return true;
            }

            if (ship.getDirection() == TypesDirection.HORIZONTAL)
                column++;
            else if (ship.getDirection() == TypesDirection.VERTICAL)
                row++;
        }
        return false;
    }

    public BattleField getbattleFieldHideShips() throws GameException {
        char[][] matrix = new char[length][length];
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                if (!(at(new Position(i, j)) == TypesField.SHIP.name))
                    matrix[i][j] = at(new Position(i, j));
                else matrix[i][j] = TypesField.WATER.name;

        return new BattleField(matrix);
    }

    private void checkShipPosition(Ship ship) throws GameException {
        if (at(ship.getPosition()) == TypesField.SHIP.name)
            throw new GameException("Błąd: istnieje już statek na tej pozycji");

        if (!IsSpace(ship))
            throw new GameException("Błąd: twoj statek wystaje poza pole bitwy");

        if (isNearShip(ship))
            throw new GameException("Błąd: inny statek znajduje się w pobliżu");
    }

    public boolean addShip(Ship ship) throws GameException {
        int row = ship.getPosition().getRow();
        int column = ship.getPosition().getColumn();

        checkShipPosition(ship);

        int k = (ship.getDirection() == TypesDirection.HORIZONTAL) ? column : row;
        for (int i = 0; i < ship.getLength() && k + i < length; i++) {
            if (ship.getDirection() == TypesDirection.HORIZONTAL) {
                if (i == 0) k = column;
                battleField[row][column + i] = TypesField.SHIP.name;
            } else if (ship.getDirection() == TypesDirection.VERTICAL) {
                if (i == 0) k = row;
                battleField[row + i][column] = TypesField.SHIP.name;
            }
            numberShips++;
        }
        return true;
    }

    public boolean addHit(Position position) throws GameException {
        if (at(position) == TypesField.SHIP.name) {
            numberShips--;
            return set(TypesField.HIT.name, position);
        } else if (at(position) == TypesField.WATER.name) return set(TypesField.MISS.name, position);
        else throw new GameException("Błąd: już strzelałeś w tą pozycję");
    }

    public void reset() {
        battleField = fillWater();
        numberShips = 0;
    }
}
