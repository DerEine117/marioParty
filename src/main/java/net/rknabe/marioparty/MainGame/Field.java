package net.rknabe.marioparty.MainGame;

public class Field {
    private int fieldNumber;
    private final int x;
    private final int y;
    private int state;
    private boolean hasPlayer;

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
    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber =fieldNumber;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

}
