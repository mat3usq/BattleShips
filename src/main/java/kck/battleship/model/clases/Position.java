package kck.battleship.model.clases;

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
        this.row = convertRowToIndex(row);
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static int convertRowToIndex(char row) {
        return row - 'a';
    }

    public static char convertIndexToRow(int row) {
        return (char) ('a' + row);
    }

    public static void isInRange(char row, int column, Board board) throws PositionException {
        int decodeRow = convertRowToIndex(row);
        if (decodeRow >= board.getLength() || column > board.getLength() || decodeRow < 0 || column < 0)
            throw new PositionException("Błąd, dozwolone wartości od A1 do " + Position.convertIndexToRow(board.getLength() - 1) + board.getLength());
    }

    public String toStringEncode(Position position) {
        return "(" + (char)('a' + position.getRow()) + "," + (position.getColumn() + 1) + ")";
    }
}
