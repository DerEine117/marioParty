package net.rknabe.marioparty.game1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

public class Drawer {
    private GraphicsContext graphicsContext;
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private final int CELLS = 3;

    public Drawer(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void drawField() {
        drawBackground();
        drawGrid();
        //drawMarioIcons();
    }

    private void drawBackground() {

        // fill game Field
        graphicsContext.setFill(Color.rgb(153, 255, 153, 0.5));
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void drawGrid() {
        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.setLineWidth(2.0);

        // Vertical lines
        for (int i = 1; i < CELLS; i++) {
            double x = i * WIDTH / CELLS;
            graphicsContext.strokeLine(x, 0, x, HEIGHT);
        }

        // Horizontal lines
        for (int i = 1; i < CELLS; i++) {
            double y = i * HEIGHT / CELLS;
            graphicsContext.strokeLine(0, y, WIDTH, y);
        }
    }

    private void drawMarioIcons() {
        double cellWidth = WIDTH / CELLS;
        double cellHeight = HEIGHT / CELLS;

        // Draw Mario icons in the center of each cell
        for (int row = 0; row < CELLS; row++) {
            for (int col = 0; col < CELLS; col++) {
                double x = col * cellWidth + cellWidth / 4;
                double y = row * cellHeight + cellHeight / 4;

                // Draw a simple Mario icon (a red circle)
                graphicsContext.setFill(Color.rgb(255, 0, 0, 0.5));
                graphicsContext.fillOval(x, y, cellWidth / 2, cellHeight / 2);
            }
        }
    }


    public void drawMove(Field field, char playerAB) {
        double cellWidth = WIDTH / (double) CELLS;
        double cellHeight = HEIGHT / (double) CELLS;

        // Berechne die Koordinaten des Feldes
        double x = field.getCol() * cellWidth + cellWidth / 2;
        double y = field.getRow() * cellHeight + cellHeight / 2;

        //read image and set field to not free
        Image playerFieldImage;
        if (playerAB == 'A') {
            playerFieldImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/game1/PlayerA.png"), 45, 45, true, false);
            field.setState(FieldState.A);
        } else {
            playerFieldImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/game1/PlayerB.png"), 45, 45, true, false);
            field.setState(FieldState.B);
        }
        graphicsContext.drawImage(playerFieldImage, x-15, y-15);

    }

    public void resetCanvas() {
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
    }
}
