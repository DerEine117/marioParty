package net.rknabe.marioparty.game6;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class MinesWeeperApp extends Pane {
    private Board board;

    public MinesWeeperApp() {
        board = new Board();
        getChildren().add(board);
    }

    public void initializeBackground(Scene scene) {
        try {
            FileInputStream inputstream = new FileInputStream("/Users/student/IdeaProjects/marioParty/src/main/resources/net/rknabe/marioparty/assets/background_game6.jpeg");
            Image backgroundImage = new Image(inputstream);

            // Set the ImageView as the background for the MinesWeeperApp pane
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            setBackground(new Background(background));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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