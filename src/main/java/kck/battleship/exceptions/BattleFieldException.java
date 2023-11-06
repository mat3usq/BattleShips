package kck.battleship.exceptions;

public class BattleFieldException extends Exception{
    String msg;
    public BattleFieldException(String msg){
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}