package net.rknabe.marioparty.game5;

import java.util.Arrays;
import java.util.Random;

public class Ship {

    int length;
    int[] position = new int[2];
    String orientation;
    Board board;
    int hits = 0;
    private boolean hit;


    public Ship(int length, Board board) {
        this.length = length;
        this.board = board;
        this.orientation = getRandomOrientation();
        this.position = getRandomPosition();
    }


    public int[] getRandomPosition() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(10 - (orientation.equals("horizontal") ? length - 1 : 0));
            y = random.nextInt(10 - (orientation.equals("vertical") ? length - 1 : 0));
        } while (!board.canPlaceShip(this));
        return new int[]{x, y};
    }
    public String getRandomOrientation() {
        Random random = new Random();
        return random.nextBoolean() ? "horizontal" : "vertical";
    }

    private boolean arePositionsFree(int[][] positions) {
        for (int[] position : positions) {
            if (board.isOccupied(position[0], position[1])) {
                return false;
            }
        }
        return true;
    }


    public void hit() {
        this.hit = true;
        this.hits++;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isSunk() {
        return hits == length;
    }

}