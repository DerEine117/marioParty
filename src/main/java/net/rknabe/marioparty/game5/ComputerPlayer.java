package net.rknabe.marioparty.game5;

import java.util.Random;

public class ComputerPlayer {
    private Board opponentBoard;
    private Random random;

    public ComputerPlayer(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
        this.random = new Random();
    }

    public int[] takeTurn() {
        int x, y;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (opponentBoard.isHit(x, y) || opponentBoard.wasShot(x, y));

        opponentBoard.shoot(x, y);
        return new int[]{x, y};
    }
}
