package net.rknabe.marioparty.game6;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Board extends Pane {
    private int totalBombs; // Add this line

    static final int TILE_SIZE = 70;
    private static final int W = 500;
    private static final int H = 500;

    public static final int X_TILES = W/TILE_SIZE;
    public static final int Y_TILES = H/TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];

    public Board() {
        setPrefSize(W, H);
    }

    public void startGame() {
        totalBombs = 0; // Reset totalBombs at the start of each game

        System.out.println("Board's startGame was called"); // Debug output
        getChildren().clear();
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.1);
                grid[x][y] = tile;
                getChildren().add(tile);
                if (tile.hasBomb()) {
                    totalBombs++; // Increment totalBombs for each bomb
                }
            }
        }
        setPrefWidth(X_TILES * TILE_SIZE);
        setPrefHeight(Y_TILES * TILE_SIZE);

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                if (tile.hasBomb())
                    continue;
                long bombs = getNeighbors(tile).stream().filter(Tile::hasBomb).count();
                if (bombs > 0)
                    tile.setAdjacentBombs((int) bombs);
            }
        }
        checkWin();
    }
    public void checkWin() {
        System.out.println(totalBombs+" bombs");
        int markedCount = 0;
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                if (tile.hasBomb() && !tile.isMarked()) {
                    return; // A bomb is not marked, so the player cannot win
                }
                if (!tile.hasBomb() && !tile.isRevealed()) {
                    return; // A safe field is not revealed, so the player cannot win
                }
                if (tile.isMarked()) {
                    markedCount++;
                    if (!tile.hasBomb()) {
                        System.out.println("You lose! A safe field is marked.");
                        Game6Controller.getInstance().setGameOver(true, "You lose!");
                        return; // A safe field is marked, so the player loses
                    }
                }
            }
        }

        // If we get here, it means that all bombs are marked and all safe fields are revealed
        if (markedCount > totalBombs) {
            System.out.println("You lose! Too many fields are marked.");
            Game6Controller.getInstance().setGameOver(true, "You lose! Too many fields are marked.");
        } else {
            System.out.println("You win!");
            Game6Controller.getInstance().setGameOver(false, "You win!");
            Game6Controller.getInstance().getGameTimer().stop(); // Stop the timer when the game is won
        }
    }


    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        int[] points = {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getX() + dx;
            int newY = tile.getY() + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }
    public boolean allTilesRevealedOrMarked() {
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                if (!tile.isRevealed() && !tile.isMarked()) {
                    return false; // A tile is neither revealed nor marked, so not all tiles are processed
                }
            }
        }
        return true; // All tiles are either revealed or marked
    }

    public void checkTooManyMarks() {
        int markedCount = 0;
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                if (tile.isMarked()) {
                    markedCount++;
                }
            }
        }

        if (markedCount > totalBombs) {
            System.out.println("You lose! Too many fields are marked.");
            Game6Controller.getInstance().setGameOver(true, "You lose! Too many fields are marked.");
        }
    }
    public int getTotalBombs() {
        return totalBombs;
    }
}