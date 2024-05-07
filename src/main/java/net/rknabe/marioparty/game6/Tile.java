package net.rknabe.marioparty.game6;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static net.rknabe.marioparty.game6.Board.TILE_SIZE;

public class Tile extends StackPane {
    private int x, y;
    private boolean hasBomb;
    private boolean revealed;
    private int adjacentBombs;
    private boolean marked = false;

    public Tile(int x, int y, boolean hasBomb) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;

        setPrefSize(TILE_SIZE, TILE_SIZE);
        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);

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

    public boolean hasBomb() {
        return hasBomb;
    }

    public void mark() {
        marked = !marked;
        update();
    }

    public boolean isMarked() {
        return marked;
    }

    public void reveal() {
        if (marked || revealed)
            return;

        revealed = true;

        if (hasBomb) {
            System.out.println("Game Over");
        } else {
            System.out.println("Safe");
            if (adjacentBombs == 0) {
                // Get the board from the parent of this tile
                Board board = (Board) getParent();
                // Reveal all neighbors if this tile has no adjacent bombs
                board.getNeighbors(this).forEach(Tile::reveal);
            }
        }

        update();
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public int getAdjacentBombs() {
        return adjacentBombs;
    }
    public void update() {
        Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        border.setFill(revealed ? (hasBomb ? Color.RED : Color.GREEN) : Color.GRAY);

        getChildren().addAll(border);

        // If the tile is revealed and it doesn't have a bomb, display the number of adjacent bombs
        if (revealed && !hasBomb && adjacentBombs > 0) {
            Text text = new Text(String.valueOf(adjacentBombs));
            getChildren().add(text);
        }
        if (marked) {
            // Change the appearance of the tile to show that it is marked
            // This is just an example, you can change it to fit your needs
            Rectangle border1 = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
            border.setFill(Color.BLUE);
            getChildren().add(border1);
        }
    }
}