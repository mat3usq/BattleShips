package kck.battleship.model.types;

public enum TypesField {
    HIT('✘'),
    MISS('◉'),
    SHIP ('⎕'),
    WATER ('ℳ');

    public final char name;

    TypesField(char name) {
        this.name = name;
    }
}
