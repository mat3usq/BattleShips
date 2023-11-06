package kck.battleship.model.enum_;

import kck.battleship.exceptions.DirectionException;

public enum Direction {
    HORIZONTAL,
    VERTICAL;

    public static Direction decode(char _char) throws DirectionException {
        if (_char == 'h' || _char == 'H') return HORIZONTAL;
        else if (_char == 'v' || _char == 'V') return VERTICAL;
        else throw new DirectionException("Podany znak '" + _char + "' nie jest poprawny");
    }
}
