package net.rknabe.marioparty.MainGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
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
        GridPane gridPane = new GridPane();

        board.setupBoard(gridPane);
        board.numberFieldsDFS(gridPane, 0, 0);
        drawer.drawPicture(playerPicture);


        board.setFieldStateBasedOnColor();
        gameField.getChildren().add(gridPane);
        //board.printFields();
        gameField.setBorder(Border.stroke(Color.BLACK));
        player1 = new Player("Player 1",false);
        player2= new Player("Computer",true);
        drawer.drawPlayer(gridPane, player1.getPosition(), 0,board);
        drawer.drawPlayer(gridPane, player2.getPosition(), 1,board);


    }
    public static void main(String[] args) {
        launch(args);
    }
    private void würfeln(boolean computer) {
        Player currentPlayer = computer ? player2 : player1;
        Dice currentDice = computer ? player2dice : player1dice;
        ImageView currentDiceImageView = computer ? dice2 : dice1;

        drawer.drawDiceAnimation(currentDiceImageView);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                int diceNumber = currentDice.roll();
                drawer.drawDicePicture(diceNumber, currentDiceImageView);

                updateGameState(currentPlayer, diceNumber);
            });
        }).start();
    }

    private void updateGameState(Player currentPlayer, int diceNumber) {
        int oldPosition = currentPlayer.getPosition();
        currentPlayer.move(diceNumber);

        Field oldField = board.getFieldByNumber(oldPosition);
        if (oldField != null) {
            oldField.setHasPlayer(false); // Der Spieler hat das alte Feld verlassen
        }

        Field newField = board.getFieldByNumber(currentPlayer.getPosition());
        if (newField != null) {
            newField.setHasPlayer(true); // Der Spieler hat das neue Feld betreten
            drawer.resetRectangleColor(gridPane, oldPosition, board);


            // Überprüfen Sie, ob das Feld bereits von einem anderen Spieler besucht wurde
            Player otherPlayer = currentPlayer.equals(player1) ? player2 : player1;
            if (newField.getFieldNumber() == otherPlayer.getPosition()) {
                // Setzen Sie den Zustand des Feldes auf "neutral" und ändern Sie die Farbe des Rechtecks auf Grau
                newField.setState(0);
                Rectangle rectangle = board.getRectangleByCoordinates(newField.getX(), newField.getY());
                if (rectangle != null) {
                    rectangle.setFill(Color.GRAY);
                }
            } else {
                switch (newField.getState()) {
                    case 1: // Grünes Feld
                        currentPlayer.setCoins(+5);
                        Rectangle rectangle = board.getRectangleByCoordinates(newField.getX(), newField.getY());
                        if (rectangle != null) {
                            rectangle.toFront();
                        }
                        break;
                    case 2: // Rotes Feld
                        currentPlayer.setCoins(-5);
                        rectangle = board.getRectangleByCoordinates(newField.getX(), newField.getY());
                        if (rectangle != null) {
                            rectangle.toFront();
                        }
                        break;
                    default: // Graues Feld
                        System.out.println("Graues Feld");
                        break;
                }
            }
            int playerIndex = currentPlayer.equals(player1) ? 0 : 1;
            drawer.drawPlayer(gridPane, currentPlayer.getPosition(), playerIndex, board);

            // Überprüfen Sie, ob der andere Spieler auf der alten Position des aktuellen Spielers ist
            if (otherPlayer.getPosition() == oldPosition) {
                int otherPlayerIndex = otherPlayer.equals(player1) ? 0 : 1;
                drawer.drawPlayer(gridPane, oldPosition, otherPlayerIndex, board);
            }

            checkPlayersOnSameField();
        }
        System.out.println("Player: " + currentPlayer.getName() + " Position: " + currentPlayer.getPosition() + " Coins: " + currentPlayer.getCoins());
    }
    private void checkPlayersOnSameField() {
    if (player1.getPosition() == player2.getPosition()) {
        Field field = board.getFieldByNumber(player1.getPosition());
        if (field != null) {
            Rectangle rectangle = board.getRectangleByCoordinates(field.getX(), field.getY());
            if (rectangle != null) {
                rectangle.setFill(Color.BLUE);
            }
        }
    }
}
}
