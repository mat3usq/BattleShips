package kck.battleship.model.types;

public enum TypesShips {
    SUBMARINE(1, 2),
    CRUISER(2, 3),
    DESTROYER(2, 4),
    BATTLESHIP(1, 5);

    private final int numberOfShips;
    private final int shipLength;

    TypesShips(int numShips, int shipLength) {
        this.numberOfShips = numShips;
        this.shipLength = shipLength;
    }

    public int getShipLength() {
        return shipLength;
    }

    public int getNumberShips() {
        return numberOfShips;
    }

    public static int lengthAllShips() {
        int sum = 0;
        for (TypesShips type : TypesShips.values()) sum += type.shipLength * type.numberOfShips;
        return sum;
    }

    public static int countAllShips() {
        int sum = 0;
        for (TypesShips type : TypesShips.values()) sum += type.numberOfShips;
        return sum;
    }

    public static String toPolishName(TypesShips type) {
        return switch (type) {
            case BATTLESHIP -> "OKRĘT WOJENNY";
            case DESTROYER -> "NISZCZYCIEL";
            case CRUISER -> "KRĄŻOWNIK";
            case SUBMARINE -> "ŁÓDŹ PODWODNA";
        };
    }
}
