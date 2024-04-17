package net.rknabe.marioparty.game1;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.ResourceBundle;

public class Game1Controller extends GameController implements Initializable {
    Drawer drawer;
    Board board;

    @FXML
    protected Canvas gameCanvas;

    @FXML
    private AnchorPane rootPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set scene background
        Image backgroundImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/entireBackground.jpg"));
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        rootPane.setBackground(new Background(background));

        // draw game field
        GraphicsContext gf = gameCanvas.getGraphicsContext2D();
        drawer = new Drawer(gf);
        drawer.drawField();

        // creating the (empty) board
        board = new Board();
    }

    @FXML
    protected void gameCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (board.getClickedField(x,y).isFree()) {
            drawer.drawMove(board.getClickedField(x,y));
        }
    }
}
