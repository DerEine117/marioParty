package net.rknabe.marioparty.game2;

public class GameState {
    private boolean end;

    protected GameState() {
        this.end = false;
    }

    protected boolean isEnd() {
        return end;
    }

    protected void setEnd(boolean end) {
        this.end = end;
    }
}
