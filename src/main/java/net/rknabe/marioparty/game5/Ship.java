package net.rknabe.marioparty.game5;

import java.util.Random;

public class Ship {

    int length;
    int[] position = new int[2];
    String orientation;
    Board board;
    int hits = 0;


    public Ship(int length, Board board) {
        this.length = length;
        this.board = board;
        do {
            this.position = getRandomPosition();
            this.orientation = getRandomOrientation();
        } while (!board.canPlaceShip(this));
    }


    private int[] getRandomPosition() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (board.isOccupied(x, y));
        return new int[]{x, y};
    }

    private String getRandomOrientation() {
        return new Random().nextBoolean() ? "horizontal" : "vertical";
    }
    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits == length;
    }

}