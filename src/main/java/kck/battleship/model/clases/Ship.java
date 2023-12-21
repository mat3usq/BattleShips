package kck.battleship.model.clases;

import kck.battleship.model.types.TypesDirection;
import kck.battleship.model.types.TypesField;

public class Ship {
    private final String name;
    private final int length;
    private Position position;
    private TypesDirection direction;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
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

    public TypesDirection getDirection() {
        return direction;
    }

    public void setDirection(TypesDirection direction) {
        this.direction = direction;
    }

    public String toString() {
        return (" " + TypesField.SHIP.name).repeat(length);
    }
}
