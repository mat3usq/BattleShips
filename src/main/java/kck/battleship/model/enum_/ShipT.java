package kck.battleship.model.enum_;

public enum ShipT {
    SUBMARINE(3, 2),
    CRUISER(2, 3),
    DESTROYER(2, 4),
    BATTLESHIP(1, 5),
    CARRIER(1, 5);


    private final int numberOfShips;
    private final int shipLength;

    ShipT (int numShips, int shipLength) {
        this.numberOfShips = numShips;
        this.shipLength = shipLength;
    }

    public int getShipLength() {
        return shipLength;
    }
    public int getNumberShips() {
        return numberOfShips;
    }

    public static int lengthAllShips(){
        int sum = 0;
        for (ShipT type: ShipT.values()) sum += type.shipLength * type.numberOfShips;
        return sum;
    }
    public static int sizeAllShips(){
        int sum = 0;
        for (ShipT type: ShipT.values()) sum += type.numberOfShips;
        return sum;
    }

    public static String toEnglishName(ShipT type){
        return switch (type) {
            case BATTLESHIP -> "BATTLESHIP";
            case DESTROYER -> "DESTROYER";
            case CRUISER -> "CRUISER";
            case SUBMARINE -> "SUBMARINE";
            case CARRIER -> "CARRIER";
        };
    }
}
