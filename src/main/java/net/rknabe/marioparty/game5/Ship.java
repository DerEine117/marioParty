package net.rknabe.marioparty.game5;

public class Ship {

    public int length;
    public int[] position = new int[2];
    public String orientation;
    public Board board;
    public int hits = 0;
    private boolean hit;

    private Game5Controller gameController;

    // Constructor for the Ship class, initializes the length, orientation, and gameController
    public Ship(int length, String orientation, Game5Controller gameController) {
        this.length = length;
        this.orientation = orientation;
        this.gameController = gameController;
    }

    // Method to mark a ship as hit and increment the hit count
    public void hit() {
        this.hit = true;
        this.hits++;

        // Check if the ship is sunk after being hit
        if (isSunk()) {
            gameController.shipSunk(this);
        }
    }

    // Method to check if a ship is sunk (all parts of the ship have been hit)
    public boolean isSunk() {
        return hits == length;
    }


}