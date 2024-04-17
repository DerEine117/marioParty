package net.rknabe.marioparty.game1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.ResourceBundle;

public class Game1Controller implements Initializable {
    Drawer drawer;

    @FXML
    protected Button backToMenu;

    @FXML
    protected Canvas gameCanvas;

    @FXML
    private AnchorPane rootPane;

    @FXML
    protected void backToMenuClick() {
        Board board = new Board();
        board.draw();
        StageChanger.setScene(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set scene background
        // Lade das Bild
        Image backgroundImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/entireBackground.jpg"));

        // Setze das Bild als Hintergrundbild
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );

        // Setze den Hintergrund der Szene
        rootPane.setBackground(new Background(background));

        // draw game field on init
        GraphicsContext gf = gameCanvas.getGraphicsContext2D();
        drawer = new Drawer(gf);
        drawer.drawField();


    }
}
