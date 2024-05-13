package net.rknabe.marioparty.game5;

import java.util.*;

public class ComputerPlayer {
    private Board opponentBoard;
    private Set<String> visitedFields;
    private Random random;
    private int lastHitX;
    private int lastHitY;
    private boolean isHorizontal;
    private boolean isVertical;

    public ComputerPlayer(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
        this.visitedFields = new HashSet<>();
        this.random = new Random();
        this.lastHitX = -1; // Initialize the x-coordinate of the last hit to -1 (no hit yet)
        this.lastHitY = -1; // Initialize the y-coordinate of the last hit to -1 (no hit yet)
        this.isHorizontal = false;
        this.isVertical = false;
    }

    public int[] takeTurn() {
        // nextShot will hold the coordinates of the next shot
        int[] nextShot = new int[2];
        // If the last shot hit a ship, continue shooting in the same direction
        if (lastHitX != -1 && lastHitY != -1) {

            // Horizontal search logic
            if (isHorizontal) {
                // Keep the same Y coordinate
                nextShot[1] = lastHitY;
                // Start by looking to the right
                int direction = 1;
                while (true) {
                    // Update the x-coordinate of the next shot
                    nextShot[0] = lastHitX + direction;
                    if (nextShot[0] < 0 || nextShot[0] > 9 || this.isMiss(nextShot[0], nextShot[1]) || isFieldVisited(nextShot)) {
                        // If the field to the right is outside the board, a miss, or already visited, stop searching in this direction
                        break;
                    }
                    // Check if the next shot hits a ship
                    if (opponentBoard.isOccupied(nextShot[0], nextShot[1])) {
                        // If it hits, mark the hit, add the coordinates to the visited fields, and return the shot coordinates
                        markHit(nextShot[0], nextShot[1]);
                        visitedFields.add(nextShot[0] + "," + nextShot[1]);
                        return nextShot;
                    }
                    direction++;
                }

                // Vertical search logic
            } else if (isVertical) {
                // Keep the same X coordinate
                nextShot[0] = lastHitX;
                // Start by looking downwards
                int direction = 1;
                while (true) {
                    // Update the y-coordinate of the next shot
                    nextShot[1] = lastHitY + direction;
                    if (nextShot[1] < 0 || nextShot[1] > 9 || this.isMiss(nextShot[0], nextShot[1]) || isFieldVisited(nextShot)) {
                        // If the field below is outside the board, a miss, or already visited, stop searching in this direction
                        break;
                    }
                    if (opponentBoard.isOccupied(nextShot[0], nextShot[1])) {
                        // If it hits, mark the hit, add the coordinates to the visited fields, and return the shot coordinates
                        markHit(nextShot[0], nextShot[1]);
                        visitedFields.add(nextShot[0] + "," + nextShot[1]);
                        return nextShot;
                    }
                    direction++;
                }
            }
        }

        // If no previous hit or search direction found, shoot randomly
        do {
            nextShot = new int[]{random.nextInt(10), random.nextInt(10)};
        } while (isFieldVisited(nextShot));

        // Check if the next shot hits a ship
        if (opponentBoard.isOccupied(nextShot[0], nextShot[1])) {
            markHit(nextShot[0], nextShot[1]);
        }
        // Add the coordinates to the visited fields and return the shot coordinates
        visitedFields.add(nextShot[0] + "," + nextShot[1]);
        return nextShot;
    }

    // Method to check if a field has been visited
    private boolean isFieldVisited(int[] shot) {
        return visitedFields.contains(shot[0] + "," + shot[1]);
    }


    public boolean isMiss(int x, int y) {
        // Loop through all the fields that have been visited
        for (String field : visitedFields) {
            // Split the string to get the x and y coordinates
            String[] coordinates = field.split(",");
            // Convert the x and y coordinates to integers
            int visitedX = Integer.parseInt(coordinates[0]);
            int visitedY = Integer.parseInt(coordinates[1]);

            // Check if the current field matches the input coordinates
            if (visitedX == x && visitedY == y) {
                // check if there is a ship on the field
                if (!opponentBoard.isOccupied(x, y)) {
                    // There is no ship on the field, so it is a "miss"
                    return true;
                }
            }
        }
        // The field has not been visited yet or there is a ship on the field
        return false;
    }

    public void markHit(int x, int y) {
        lastHitX = x;
        lastHitY = y;
        isHorizontal = opponentBoard.isHorizontalHit(x, y);
        isVertical = opponentBoard.isVerticalHit(x, y);
    }
}

