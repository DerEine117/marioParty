package net.rknabe.marioparty.game6;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends Pane {

    private int totalBombs;
    static final int TILE_SIZE = 60;
    private static final int W = 500;
    private static final int H = 500;
    private static final Random RANDOM = new Random();
    public static final int X_TILES = W / TILE_SIZE;
    public static final int Y_TILES = H / TILE_SIZE;
    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    public String difficulty = "easy";

    public Board() {
        setPrefSize(W, H);
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
        // Set the number of adjacent bombs for each tile
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

    // get the neighbors of a tile
    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        // 8 directions
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

    //set the difficulty of the game
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        setTotalBombs();
    }

    //here we set the total number of bombs with the range of bombs depending on the difficulty
    public void setTotalBombs() {
        if (difficulty == "easy") {
            totalBombs = RANDOM.nextInt(3) + 2;
        } else if (difficulty == "hard") {
            totalBombs = RANDOM.nextInt(6) + 5;
        }
    }
    public int getTotalBombs() {
        return totalBombs;
    }
    //here we check if the player has marked too many fields
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
            Game6Controller.getInstance().showGameOverMessage("You lose! Too many fields are marked.");
            Game6Controller.getInstance().setGameOver(true, "You lose! Too many fields are marked.");
        }
    }
    //here we check if the player has won the game
    public void checkWin() {
        Platform.runLater(() -> {
            boolean bombNotMarked = false;
        boolean safeFieldNotRevealed = false;
        int markedCount = 0;

        for (int y = 2; y < Y_TILES; y++) {
            for (int x = 2; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                // Check if a bomb is not marked
                if (tile.hasBomb() && !tile.isMarked()) {
                    bombNotMarked = true;
                }
                // Check if a safe field is not revealed
                if (!tile.hasBomb() && !tile.isRevealed()) {
                    safeFieldNotRevealed = true;
                }
                // Count marked tiles
                if (tile.isMarked()) {
                    markedCount++;
                    if (!tile.hasBomb()) {
                        Game6Controller.getInstance().showGameOverMessage("You lose! There was no bomb!");

                        Game6Controller.getInstance().setGameOver(true, "You lose! There was no bomb!");
                        return;
                    }
                }
            }
        }
        // Check if all bombs are marked and all safe fields are revealed
        if (!bombNotMarked && !safeFieldNotRevealed) {
            if (markedCount == totalBombs) {
                Game6Controller.getInstance().showGameOverMessage("You Win!");
                Game6Controller.getInstance().setGameOver(false, "You win!");
                Game6Controller.getInstance().getGameTimer().interrupt(); // Stop the timer when the game is won
                ImageView winImage = new ImageView(getClass().getResource("/net/rknabe/marioparty/assets/win.gif").toString());
                winImage.setFitWidth(W); // Set the width of the image to the width of the board
                winImage.setFitHeight(H); // Set the height of the image to the height of the board
                winImage.setTranslateX(70); // Adjust this value as needed
                getChildren().remove(winImage);
                getChildren().add(winImage);
            }
        } else {
            return;
        }
        });
    }



}