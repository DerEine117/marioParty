package net.rknabe.marioparty.game6;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Board extends Pane {
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
        System.out.println("Board's startGame was called"); // Debug output
        getChildren().clear();
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.1);
                grid[x][y] = tile;
                getChildren().add(tile);
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
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                if (tile.hasBomb() && !tile.isMarked()) {
                    return;
                }
                if (!tile.hasBomb() && !tile.isRevealed()) {
                    return;
                }
            }
        }

        System.out.println("You win!");
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
}