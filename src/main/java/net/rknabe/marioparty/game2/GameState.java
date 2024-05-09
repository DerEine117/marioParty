package net.rknabe.marioparty.game2;

public class GameState {
    private boolean end;

    public GameState() {
        this.end = false;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
