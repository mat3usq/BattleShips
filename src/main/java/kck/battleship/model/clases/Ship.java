package kck.battleship.model.clases;

import kck.battleship.model.enum_.Direction;

public class Ship {
    private final String name;
    private final int length;
    private Position position;
    private Direction direction;

    public Ship(String name, int length){
        this.name = name;
        this.length = length;
        this.position = null;
        this.direction = null;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String toGraphicLength() {
        return ("" + Board.SHIP).repeat(length);
    }
}
