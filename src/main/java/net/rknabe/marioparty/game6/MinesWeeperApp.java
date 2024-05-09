package net.rknabe.marioparty.game6;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.*;





public class MinesWeeperApp extends Pane {
    private Board board;

    public MinesWeeperApp() {
        board = new Board();
        getChildren().add(board);
        Image backgroundImage = new Image("file:/Users/student/IdeaProjects/marioParty/src/main/resources/net/rknabe/marioparty/assets/background_game6.jpeg");
        BackgroundSize backgroundSize = new BackgroundSize(500, 500, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));
        board.setDifficulty(0.1);
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