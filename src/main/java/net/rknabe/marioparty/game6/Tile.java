package net.rknabe.marioparty.game6;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import static net.rknabe.marioparty.game6.Board.TILE_SIZE;

public class Tile extends StackPane {
    private int x, y;
    private int adjacentBombs;
    private boolean hasBomb;
    private boolean revealed;
    private boolean marked;
    private ImageView bombImage;
    private ImageView flagImage;

    public Tile(int x, int y, boolean hasBomb) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;
        // insert the bomb image if the tile has a bomb
        if (hasBomb) {
            bombImage.setVisible(false);
            getChildren().add(bombImage);
        }
        // insert the flag image if the tile is marked
        flagImage = new ImageView(getClass().getResource("/net/rknabe/marioparty/assets/flag2_game6.gif").toString());
        flagImage.setVisible(false);
        flagImage.setFitWidth(TILE_SIZE);
        flagImage.setFitHeight(TILE_SIZE);
        getChildren().add(flagImage);
        setPrefSize(TILE_SIZE, TILE_SIZE);
        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);

        // Add event handler to the tile
        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                reveal();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                mark();
            }
        });
        update();
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // set the bomb status of the tile
    public void setBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
        if (hasBomb && bombImage == null) {
            bombImage = new ImageView(getClass().getResource("/net/rknabe/marioparty/assets/bomb.gif").toString());
            bombImage.setVisible(false); // The image should initially not be visible
            bombImage.setFitWidth(TILE_SIZE);
            bombImage.setFitHeight(TILE_SIZE);
            getChildren().add(bombImage);
        }
    }
    // set the number of adjacent bombs
    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void mark() {
        marked = !marked;
        if (marked) {
            flagImage.setVisible(true);
        } else {
            flagImage.setVisible(false);
        }
        update();
    }

    public boolean isMarked() {
        return marked;
    }

    public void reveal() {
        // check if the game is over or the tile is already marked or revealed
        if (Game6Controller.getInstance().isGameOver() || marked || revealed)
            return;
        revealed = true;
        if (hasBomb) {
            Game6Controller.getInstance().showGameOverMessage("Game Over");
            Game6Controller.getInstance().setGameOver(true, "Game Over");

        } else {
            if (adjacentBombs == 0) {
                Board board = (Board) getParent();
                board.getNeighbors(this).forEach(Tile::reveal);
            }
        }
        update();
    }

    public boolean isRevealed() {
        return revealed;
    }
    //change the appearance of the tile
    public void update() {
        // Create a border around the tile
        Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        border.setFill(revealed ? (hasBomb ? Color.RED : Color.GREEN) : Color.GRAY);
        getChildren().clear(); // Clear all children
        getChildren().add(border);

        // If the tile is revealed and it doesn't have a bomb, display the number of adjacent bombs
        if (revealed && !hasBomb && adjacentBombs > 0) {
            Text text = new Text(String.valueOf(adjacentBombs));
            getChildren().add(text);
        }
        // If the tile is revealed and has a bomb, display the bomb image
        if (revealed && hasBomb && bombImage != null) {
            bombImage.setVisible(true);
            getChildren().add(bombImage);

        }
        // If the tile is marked, display the flag image
        if (marked) {
            getChildren().add(flagImage);
            border.setFill(Color.PINK);
            border.setStroke(Color.RED);
            border.setStrokeWidth(5.0);
        }
    }
}