package net.rknabe.marioparty.game5;

public class Board {
    private Ship[][] ships;
    private boolean[][] shots;

    // Constructor for the Board class, initializes the ships and shots arrays
    public Board() {
        this.ships = new Ship[10][10];
        this.shots = new boolean[10][10];
    }

    // Returns the ship at the given coordinates
    public Ship getShipAt(int x, int y) {
        return ships[x][y];
    }

    // Places a ship on the board at the ship's position
    public void placeShip(Ship ship) {
        int x = ship.position[0];
        int y = ship.position[1];

        if (ship.orientation.equals("horizontal")) {
            for (int i = 0; i < ship.length; i++) {
                ships[x + i][y] = ship;
            }
        } else {
            for (int i = 0; i < ship.length; i++) {
                ships[x][y + i] = ship;
            }
        }
    }

    // Checks if a ship is present at the given coordinates
    public boolean isOccupied(int x, int y) {
        return ships[x][y] != null;
    }

    // Checks if the hit is part of a horizontally placed ship
    public boolean isHorizontalHit(int x, int y) {
        if (x < 9 && ships[x + 1][y] != null) {
            return true;
        }
        if (x > 0 && ships[x - 1][y] != null) {
            return true;
        }
        return false;
    }

    // Checks if the hit is part of a vertically placed ship
    public boolean isVerticalHit(int x, int y) {
        if (y < 9 && ships[x][y + 1] != null) {
            return true;
        }

        if (y > 0 && ships[x][y - 1] != null) {
            return true;
        }
        return false;
    }

}