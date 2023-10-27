//package kck.battleship.model;
//
//import java.util.Arrays;
//
//public class Board {
//    private final int length; //one variable for rows = columns = 10 [10x10 matrix]
//    private char[][] board;
//    private int numberShips = 0;
//    public static final char HIT = '☒';
//    public static final char MISS = '☸';
//    public static final char SHIP = '☐';
//    public static final char WATER = '~';
//
//    public Board(int length){
//        this.length = length;
//        board = initBoard();
//    }
//
//    public Board(char[][] matrix){
//        this.length = matrix.length;
//        board = matrix;
//    }
//
//    private char[][] initBoard(){
//        char[][] matrix = new char[length][length];
//        for (char[] row: matrix){
//            Arrays.fill(row, WATER);
//        }
//        return matrix;
//    }
//
//    public int getLength() {
//        return length;
//    }
//
//    public int getnNumberShips() {
//        return numberShips;
//    }
//
//    public char at(Position position) {
//        return board[position.getRow()][position.getColumn()];
//    }
//
//    public boolean set(char status, Position position) {
//        board[position.getRow()][position.getColumn()] = status;
//        return true;
//    }
//
//    public boolean thereIsShip(Position position) {
//        return at(position) == SHIP;
//    }
//
//    public boolean thereIsWater(Position position) {
//        return at(position) == WATER;
//    }
//
//    public boolean thereIsMiss(Position position){
//        return at(position) == MISS;
//    }
//
//    public boolean thereIsHit(Position position){
//        return at(position) == HIT;
//    }
//
//
//    public boolean thereIsSpace(Ship ship) {
//        int l = ship.getLength();
//        int x = ship.getPosition().getRow();
//        int y = ship.getPosition().getColumn();
//        if (ship.getDirection() == Direction.HORIZONTAL) return (length - y + 1) > l;
//        else return (length - x + 1) > l;
//    }
//}
