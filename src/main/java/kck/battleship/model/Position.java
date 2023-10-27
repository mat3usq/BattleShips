package kck.battleship.model;

import kck.battleship.exceptions.PositionException;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) throws PositionException {
        if (row < 0 || column < 0)
            throw new PositionException("Wprowadzaj dane w formacie [rząd][kolumna]");
        this.row = row;
        this.column = column;
    }

    public Position(char row, int column) throws PositionException {
        if (row < 'a' || column < 0)
            throw new PositionException("Wprowadzaj dane w formacie [rząd][kolumna]");
        this.row = decode(row);
        this.column = column;
    }

    public static int decode(char row) {
        return row - 'a';
    }

    public static char encode(int row) {
        return (char) ('a' + row);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String toStringEncode(Position position) {
        return "(" + (char)('a' + position.getRow()) + "," + (position.getColumn() + 1) + ")";
    }
}
