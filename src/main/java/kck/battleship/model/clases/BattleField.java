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

    private char[][] fillWater() {
        char[][] matrix = new char[length][length];
        for (char[] row : matrix) {
            Arrays.fill(row, TypesField.WATER.name);
        }
        return matrix;
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

    private void addIfValid(ArrayList<Position> list, int row, int column) throws GameException {
        if (row >= 0 && row < length && column >= 0 && column < length)
            list.add(new Position(row, column));
    }

    public ArrayList<Position> getAdjacentPositions(int row, int column) throws GameException {
        ArrayList<Position> adjacentPositions = new ArrayList<>();

        addIfValid(adjacentPositions, row - 1, column);
        addIfValid(adjacentPositions, row + 1, column);
        addIfValid(adjacentPositions, row, column + 1);
        addIfValid(adjacentPositions, row, column - 1);
        addIfValid(adjacentPositions, row - 1, column + 1);
        addIfValid(adjacentPositions, row - 1, column - 1);
        addIfValid(adjacentPositions, row + 1, column + 1);
        addIfValid(adjacentPositions, row + 1, column - 1);

        return adjacentPositions;
    }

    private boolean isShipAround(int row, int column) throws GameException {
        ArrayList<Position> list = getAdjacentPositions(row, column);
        for (Position position : list)
            if (at(position) == TypesField.SHIP.name) return true;
        return false;
    }

    private void checkShipPosition(Ship ship) throws GameException {
        boolean constraint;
        int row = ship.getPosition().getRow();
        int column = ship.getPosition().getColumn();
        int k = (ship.getDirection() == TypesDirection.HORIZONTAL) ? column : row;

        if (ship.getDirection() == TypesDirection.HORIZONTAL)
            constraint = (length - column + 1) > ship.getLength();
        else constraint = (length - row + 1) > ship.getLength();

        if (!constraint)
            throw new GameException("Błąd: twoj statek wystaje poza pole bitwy");

        if (at(ship.getPosition()) == TypesField.SHIP.name)
            throw new GameException("Błąd: istnieje już statek na tej pozycji");

        for (int i = 0; i < ship.getLength() && k + i < length - 1; i++) {
            if (isShipAround(row, column))
                throw new GameException("Błąd: inny statek znajduje się w pobliżu");

            if (ship.getDirection() == TypesDirection.HORIZONTAL)
                column++;
            else if (ship.getDirection() == TypesDirection.VERTICAL)
                row++;
        }
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

    public char at(Position position) {
        return battleField[position.getRow()][position.getColumn()];
    }

    public boolean addHit(Position position) throws GameException {
        if (at(position) == TypesField.SHIP.name) {
            numberShips--;
            return set(TypesField.HIT.name, position);
        } else if (at(position) == TypesField.WATER.name) return set(TypesField.MISS.name, position);
        else throw new GameException("Błąd: już strzelałeś w tą pozycję");
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

    public void reset() {
        battleField = fillWater();
        numberShips = 0;
    }
}
