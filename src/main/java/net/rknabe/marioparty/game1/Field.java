package net.rknabe.marioparty.game1;

public class Field {
    private FieldState state;
    private int row;
    private int col;

    public Field(int row, int col) {
        this.state = FieldState.EMPTY;
        this.row = row;
        this.col = col;
    }

    public FieldState getState() {
        return state;
    }

    public void setState(FieldState fieldState) {
        this.state = fieldState;
    }

    public String drawField() {
        switch (this.state) {
            case EMPTY -> {
                return "L";
            }
            case O -> {
                return "O";
            }
            case X -> {
                return "X";
            }
            default -> {
                return "error";
            }
        }
    }

    public boolean isFree() {
        if (this.state == FieldState.EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
