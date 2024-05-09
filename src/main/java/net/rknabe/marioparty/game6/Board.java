package net.rknabe.marioparty.game6;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends Pane {
    private int totalBombs; // Add this line

    static final int TILE_SIZE = 60;
    private static final int W = 500;
    private static final int H = 500;
    public double difficulty = 0.1;
    private static final Random RANDOM = new Random();


    public static final int X_TILES = W/TILE_SIZE;
    public static final int Y_TILES = H/TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];

    public Board() {
        setPrefSize(W, H);
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
        setTotalBombs();
    }
    public void setTotalBombs() {
        if (difficulty <= 0.10) { // easy
            totalBombs = RANDOM.nextInt(3) + 2; // 2 to 4 bombs
        } else if (difficulty >= 0.35) { // hard
            totalBombs = RANDOM.nextInt(6) + 5; // 8 to 11 bombs
        } else { // middle
            totalBombs = RANDOM.nextInt(3) + 5; // 5 to 7 bombs
        }
    }


    public void startGame() {
        // Reset totalBombs at the start of each game
        setTotalBombs();

        System.out.println("Board's startGame was called"); // Debug output
        getChildren().clear();

        // Create a list to hold all tiles
        List<Tile> allTiles = new ArrayList<>();

        for (int y = 2; y < Y_TILES; y++) {
            for (int x = 2; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, false);
                grid[x][y] = tile;
                getChildren().add(tile);
                allTiles.add(tile);
            }
        }

        // Randomly assign bombs to tiles
        for (int i = 0; i < totalBombs; i++) {
            int index = RANDOM.nextInt(allTiles.size());
            Tile randomTile = allTiles.remove(index);
            randomTile.setBomb(true);
        }

        for (int y = 2; y < Y_TILES; y++) {
            for (int x = 2; x < X_TILES; x++) {
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
        int markedCount = 0;
        for (int y = 2; y < Y_TILES; y++) {
            for (int x = 2; x < X_TILES; x++) {
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

        for (int i = 2; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getX() + dx;
            int newY = tile.getY() + dy;

            if (newX >= 2 && newX < X_TILES && newY >= 2 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }
    public boolean allTilesRevealedOrMarked() {
        for (int y = 2; y < Y_TILES; y++) {
            for (int x = 2; x < X_TILES; x++) {
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
        for (int y = 2; y < Y_TILES; y++) {
            for (int x = 2; x < X_TILES; x++) {
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