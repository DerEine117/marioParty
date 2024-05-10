package net.rknabe.marioparty.game6;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.*;





public class MinesWeeperApp extends Pane {
    private Board board;

    public MinesWeeperApp() {
        board = new Board();
        getChildren().add(board);
        try {
            Image backgroundImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/background_game6.jpeg").toString());
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, true, true, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            setBackground(new Background(background));
            board.setDifficulty(0.1); // Move this line here
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }





    public void startGame() {
        System.out.println("MinesWeeperApp's startGame was called"); // Debug output

        System.out.println("startGame was called"); // Debug output
        board.startGame();

    }
    public Board getBoard() { // Add this method
        return board;
    }


}