package net.rknabe.marioparty.game6;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static net.rknabe.marioparty.game6.Board.TILE_SIZE;
import javafx.scene.image.Image;

public class Tile extends StackPane {
    private ImageView bombImage;
    private int x, y;
    private boolean hasBomb;
    private boolean revealed;
    private int adjacentBombs;
    private boolean marked = false;

    public Tile(int x, int y, boolean hasBomb) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;

        if (hasBomb) {
            bombImage = new ImageView(new Image("file:/Users/student/IdeaProjects/marioParty/src/main/resources/net/rknabe/marioparty/assets/bomb.gif"));            bombImage.setVisible(false); // Das Bild sollte zunächst nicht sichtbar sein
            bombImage.setFitWidth(TILE_SIZE);
            bombImage.setFitHeight(TILE_SIZE);
            bombImage.setVisible(false); // The image should initially not be visible
            getChildren().add(bombImage);
        }

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
        ((Board) getParent()).checkWin();
        if (((Board) getParent()).allTilesRevealedOrMarked()) {
            ((Board) getParent()).checkWin();
        }

    }

    public boolean isMarked() {
        return marked;
    }

    public void reveal() {
        if (Game6Controller.getInstance().isGameOver() || marked || revealed)
            return;

        revealed = true;

        if (hasBomb) {
            if (bombImage == null) {
                System.out.println("bombImage is null. Check if the image file exists at the specified path.");
            } else {
                bombImage.setVisible(true);
            }
            System.out.println("Game Over");
            Game6Controller.getInstance().getGameTimer().stop();
            Game6Controller.getInstance().setGameOver(true, "Game Over");

        } else {
            System.out.println("Safe");
            if (adjacentBombs == 0) {
                // Get the board from the parent of this tile
                Board board = (Board) getParent();
                // Reveal all neighbors if this tile has no adjacent bombs
                board.getNeighbors(this).forEach(Tile::reveal);
            }
        }
        if (((Board) getParent()).allTilesRevealedOrMarked()) {
            ((Board) getParent()).checkWin();
        }

        update();
        ((Board) getParent()).checkWin();
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

        getChildren().clear(); // Clear all children

        // If the tile is revealed and it has a bomb, don't add the Rectangle
        if (!(revealed && hasBomb)) {
            getChildren().add(border);
        }

        // If the tile has a bomb, add the bombImage
        if (hasBomb) {
            getChildren().add(bombImage);
        }

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