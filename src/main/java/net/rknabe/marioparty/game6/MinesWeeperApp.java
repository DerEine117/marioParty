package net.rknabe.marioparty.game6;

import javafx.scene.layout.*;
import javafx.scene.image.*;


public class MinesWeeperApp extends Pane {
    private Board board;
    // create a new board and add it to the pane
    public MinesWeeperApp() {
        board = new Board();
        getChildren().add(board);
        Image backgroundImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/background_game6.jpeg").toString());
        BackgroundSize backgroundSize = new BackgroundSize(500, 500, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));
        board.setDifficulty("easy");
    }
    public void startGame() {
        board.startGame();
    }
    public Board getBoard() { // Add this method
        return board;
    }


}