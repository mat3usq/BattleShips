package kck.battleship.model.types;

import kck.battleship.controller.ViewController;
import kck.battleship.view.graphicView.GraphicView;

public enum TypesShips {
    SUBMARINE(1, 1),
    CRUISER(2, 2),
    DESTROYER(2, 3),
    BATTLESHIP(1, 4);

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

    public static String getShipName(TypesShips type){
        if (ViewController.getInstance() instanceof GraphicView)
            return toPirateName(type);
        else
            return toMilitaryName(type);
    }

    private static String toMilitaryName(TypesShips type) {
        return switch (type) {
            case BATTLESHIP -> "OKRĘT WOJENNY";
            case DESTROYER -> "NISZCZYCIEL";
            case CRUISER -> "KRĄŻOWNIK";
            case SUBMARINE -> "ŁÓDŹ PODWODNA";
        };
    }

    private static String toPirateName(TypesShips type) {
        return switch (type) {
            case BATTLESHIP -> "MORSKI REKIN";
            case DESTROYER -> "SZTORMOWY ŁOWCA";
            case CRUISER -> "PŁYWAJĄCY FORT";
            case SUBMARINE -> "GŁĘBINOWY DUCH";
        };
    }

    public static String getNameExtraShip() {
        if (ViewController.getInstance() instanceof GraphicView)
            return "CZARNA PERŁA";
        else
            return "LOTNISKOWIEC";
    }
}
