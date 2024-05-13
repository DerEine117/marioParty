package net.rknabe.marioparty.MainGame;

public class Field {
    private final int fieldNumber;
    private final int x;
    private final int y;

    public Field(int fieldNumber, int x, int y) {
        this.fieldNumber = fieldNumber;
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getFieldNumber() {
        return fieldNumber;
    }

}
