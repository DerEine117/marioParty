package net.rknabe.marioparty.game2;

public class Player {
    private static int score;

    public Player() {
        score = 0;
    }

    public static void increaseScore() {
        score+=50;
    }

    public static int getScore() {
        return score;
    }
}
