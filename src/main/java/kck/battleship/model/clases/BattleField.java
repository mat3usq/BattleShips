package kck.battleship.model.clases;

import kck.battleship.exceptions.BattleFieldException;
import kck.battleship.exceptions.PositionException;
import kck.battleship.model.enum_.Direction;
import kck.battleship.model.enum_.TypesField;

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
            Arrays.fill(row, TypesField.WATER);
        }
        return matrix;
    }

    public boolean IsSpace(Ship ship) {
        int l = ship.getLength();
        int x = ship.getPosition().getRow();
        int y = ship.getPosition().getColumn();
        if (ship.getDirection() == Direction.HORIZONTAL) return (length - y + 1) > l;
        else return (length - x + 1) > l;
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
            if (!(at(newPosition) == TypesField.MISS) && !(at(newPosition) == TypesField.HIT))
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
            if (at(position) == TypesField.SHIP) return true;
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

    public BattleField getbattleFieldHideShips() throws PositionException {
        char[][] matrix = new char[length][length];
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                if (!(at(new Position(i, j)) == TypesField.SHIP))
                    matrix[i][j] = at(new Position(i, j));
                else matrix[i][j] = TypesField.WATER;

        return new BattleField(matrix);
    }

    public boolean addShip(Ship ship) throws BattleFieldException, PositionException {
        int row = ship.getPosition().getRow();
        int column = ship.getPosition().getColumn();

        if (at(ship.getPosition()) == TypesField.SHIP) throw new BattleFieldException("Błąd, istnieje już statek na tej pozycji");

        if (IsSpace(ship)) {
            if (!isNearShip(ship)) {
                int k = (ship.getDirection() == Direction.HORIZONTAL) ? column : row;
                for (int i = 0; i < ship.getLength() && k + i < length; i++) {
                    if (ship.getDirection() == Direction.HORIZONTAL) {
                        if (i == 0) k = column;
                        battleField[row][column + i] = TypesField.SHIP;
                    } else if (ship.getDirection() == Direction.VERTICAL) {
                        if (i == 0) k = row;
                        battleField[row + i][column] = TypesField.SHIP;
                    }
                    numberShips++;
                }
                return true;
            } else throw new BattleFieldException("Błąd, inny statek znajduje się w pobliżu");
        } else throw new BattleFieldException("Błąd, inny statek znajduje się w pobliżu");
    }

    public boolean addHit(Position position) throws BattleFieldException {
        if (at(position) == TypesField.SHIP) {
            numberShips--;
            return set(TypesField.HIT, position);
        } else if (at(position) == TypesField.WATER) return set(TypesField.MISS, position);
        else throw new BattleFieldException("Błąd, już strzelałeś w tą pozycję");
    }

    public void reset() {
        battleField = fillWater();
        numberShips = 0;
    }
}
