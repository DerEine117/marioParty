package net.rknabe.marioparty.MainGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MainGame extends Application {
    private final Drawer drawer = new Drawer();
    private Dice player1dice = new Dice(); // for each player
    private Dice player2dice = new Dice(); // for each player

    @FXML
    private AnchorPane gameField;
    @FXML
    private Button rollButton;
    @FXML
    ImageView dice1;
    @FXML
    ImageView dice2;
    @FXML
    ImageView playerPicture;
    @FXML
    Label marioCoins;
    @FXML
    Label bowserCoins;
    @FXML
    Label marioPosition;
    @FXML
    Label bowserPosition;
    @FXML
    private GridPane gridPane;

    @FXML
    private void onWürfelnClick() {
        würfeln(false);
        würfeln(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/net/rknabe/marioparty/mainGame.fxml"));
        primaryStage.setTitle("Main Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @FXML
    public void initialize() {
        drawer.drawPicture(playerPicture);
        Board board = new Board();
        board.initalizeBoard(gridPane);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void würfeln(boolean computer) {
        if (computer) {
            drawer.drawDiceAnimation(dice2);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.runLater(() -> drawer.drawDicePicture(player2dice.roll(), dice2));
            }).start();
        } else {
            drawer.drawDiceAnimation(dice1);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.runLater(() -> drawer.drawDicePicture(player1dice.roll(), dice1));
            }).start();
        }
    }
}

