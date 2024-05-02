package net.rknabe.marioparty.game5;

public class Board {
    private Ship[][] ships;
    private boolean[][] shots;

    public Board() {
        this.ships = new Ship[10][10];
        this.shots = new boolean[10][10];
    }

    public boolean shoot(int x, int y) {
        if (ships[x][y] != null) {
            ships[x][y].hit();
            return true;
        }
        shots[x][y] = true;
        return false;
    }

    public boolean wasShot(int x, int y) {
        return shots[x][y];
    }

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

    public boolean canPlaceShip(Ship ship) {
        int x = ship.position[0];
        int y = ship.position[1];

        if (ship.orientation.equals("horizontal")) {
            if (x + ship.length > 10) {
                return false;
            }
            for (int i = 0; i < ship.length; i++) {
                if (isOccupied(x + i, y)) {
                    return false;
                }
            }
        } else {
            if (y + ship.length > 10) {
                return false;
            }
            for (int i = 0; i < ship.length; i++) {
                if (isOccupied(x, y + i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Ship getShipAt(int x, int y) {
        return ships[x][y];
    }

    public boolean isOccupied(int x, int y) {
        return ships[x][y] != null;
    }

    public boolean isHit(int x, int y) {
        return ships[x][y] != null && ships[x][y].isHit();
    }


    public boolean isSunk(int x, int y) {
        return ships[x][y] != null && ships[x][y].isSunk();
    }
}
