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
    private ImageView flagImage;
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
        flagImage = new ImageView(new Image("file:/Users/student/IdeaProjects/marioParty/src/main/resources/net/rknabe/marioparty/assets/flag_game6.gif"));
        flagImage.setVisible(false); // Das Bild sollte zunächst nicht sichtbar sein
        flagImage.setFitWidth(TILE_SIZE);
        flagImage.setFitHeight(TILE_SIZE);
        getChildren().add(flagImage);

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

    public void setBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
        if (hasBomb && bombImage == null) {
            bombImage = new ImageView(new Image("file:/Users/student/IdeaProjects/marioParty/src/main/resources/net/rknabe/marioparty/assets/bomb.gif"));
            bombImage.setVisible(false); // The image should initially not be visible
            bombImage.setFitWidth(TILE_SIZE);
            bombImage.setFitHeight(TILE_SIZE);
            getChildren().add(bombImage);
        }
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void mark() {
        marked = !marked;
        System.out.println("Mark method called, marked: " + marked); // Debug output

        if (marked) {
            flagImage.setVisible(true); // Zeigen Sie das Flaggen-GIF an, wenn das Feld markiert ist
        } else {
            flagImage.setVisible(false); // Verstecken Sie das Flaggen-GIF, wenn das Feld nicht markiert ist
        }
        update();
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

        if (marked) {
            getChildren().add(flagImage);
        }
    }


}