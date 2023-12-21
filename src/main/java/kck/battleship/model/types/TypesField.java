package kck.battleship.model.types;

public enum TypesField {
    HIT('✘'),
    MISS('◉'),
    SHIP ('⎕'),
    WATER ('ℳ'),
    AIM ('✜');

    public final char name;

    TypesField(char name) {
        this.name = name;
    }
}
