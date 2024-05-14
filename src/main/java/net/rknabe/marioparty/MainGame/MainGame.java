package net.rknabe.marioparty.MainGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class MainGame extends Application {
    private final Drawer drawer = new Drawer();
    private Dice player1dice = new Dice(); // for each player
    private Dice player2dice = new Dice(); // for each player
    private Player player1;
    private Player player2;
    private final Board board = new Board();

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
        GridPane gridPane = new GridPane();

        board.setupBoard(gridPane);
        board.numberFieldsDFS(gridPane, 0, 0);


        board.setFieldStateBasedOnColor();
        gameField.getChildren().add(gridPane);
        gameField.setBorder(Border.stroke(Color.BLACK));
        player1 = new Player("Player 1",false);
        player2= new Player("Player 2",true);
        drawer.drawPlayer(gridPane, player1.getPosition(), 0,board);
        drawer.drawPlayer(gridPane, player2.getPosition(), 1,board);


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
                Platform.runLater(() -> {
                    int diceNumber = player2dice.roll();
                    drawer.drawDicePicture(diceNumber, dice2);

                    // Entfärben Sie das Rechteck, bevor der Spieler bewegt wird
                    Field currentField = board.getFieldByNumber(player2.getPosition());
                    if (currentField != null) {
                        drawer.changeRectangleColor(currentField.getX(), currentField.getY(), Color.GRAY, board);
                    }

                    // Bewegen Sie den Spieler
                    player2.move(diceNumber);
                    drawer.drawPlayer(gridPane, player2.getPosition(), 1, board);
                });
            }).start();
        } else {
            drawer.drawDiceAnimation(dice1);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.runLater(() -> {
                    int diceNumber = player1dice.roll();
                    drawer.drawDicePicture(diceNumber, dice1);

                    // Entfärben Sie das Rechteck, bevor der Spieler bewegt wird
                    Field currentField = board.getFieldByNumber(player1.getPosition());
                    if (currentField != null) {
                        drawer.changeRectangleColor(currentField.getX(), currentField.getY(), Color.GRAY, board);
                    }

                    // Bewegen Sie den Spieler
                    player1.move(diceNumber);
                    drawer.drawPlayer(gridPane, player1.getPosition(), 0, board);
                });
            }).start();
        }
    }

}
