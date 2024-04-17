package net.rknabe.marioparty.game1;

public class Field {
    private FieldState state;
    public Field() {
        this.state = FieldState.EMPTY;
    }

    public FieldState getState() {
        return state;
    }

    public void setState(FieldState fieldState) {
        this.state = state;
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
}
