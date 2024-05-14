package net.rknabe.marioparty.MainGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.rknabe.marioparty.MainApplication;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainGame extends Application {
    Stage window;
    Scene mainMenuScene;
    Scene game1Scene, game2Scene, game3Scene, game4Scene, game5Scene, game6Scene;
    private final Drawer drawer = new Drawer();
    private Dice player1dice = new Dice(); // for each player
    private Dice player2dice = new Dice(); // for each player
    private Player player1;
    private Player player2;
    private final Board board = new Board();
    private List<Integer> miniGames;
    private static final List<String> miniGameNames = new ArrayList<>(Arrays.asList("Tic Tac Toe", "Balloon Platzen", "Hangman", "Snake", "Schiffe versenken", "Minenfeld"));
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
    public void start(Stage stage) throws Exception {

        window = stage;

        // Hauptmenü erstellen
        FXMLLoader fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("mainGame.fxml"));
        mainMenuScene = new Scene(fxmlLoaderMenu.load(), 750, 400);
        // Initializing the game Scenes from fxml
        FXMLLoader fxmlLoaderGame1 = new FXMLLoader(MainApplication.class.getResource("game1-view.fxml"));
        game1Scene = new Scene(fxmlLoaderGame1.load(), 600, 400);
        FXMLLoader fxmlLoaderGame2 = new FXMLLoader(MainApplication.class.getResource("game2-view.fxml"));
        game2Scene = new Scene(fxmlLoaderGame2.load(), 600, 400);
        FXMLLoader fxmlLoaderGame3 = new FXMLLoader(MainApplication.class.getResource("game3-view.fxml"));
        game3Scene = new Scene(fxmlLoaderGame3.load(), 600, 400);
        FXMLLoader fxmlLoaderGame4 = new FXMLLoader(MainApplication.class.getResource("game4-view.fxml"));
        game4Scene = new Scene(fxmlLoaderGame4.load(), 600, 400);
        FXMLLoader fxmlLoaderGame5 = new FXMLLoader(MainApplication.class.getResource("game5-view.fxml"));
        game5Scene = new Scene(fxmlLoaderGame5.load(), 600, 400);
        FXMLLoader fxmlLoaderGame6 = new FXMLLoader(MainApplication.class.getResource("game6-view.fxml"));
        game6Scene = new Scene(fxmlLoaderGame6.load(), 600, 400);

        StageChanger.createStageController(window,mainMenuScene, game1Scene,game2Scene,game3Scene,game4Scene,game5Scene,game6Scene);

        window.setScene(mainMenuScene);

        window.setTitle("Mario Party Spiel");
        window.show();
    }

    @FXML
    public void initialize() {
        GridPane gridPane = new GridPane();
        miniGames = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        System.out.println(miniGames);
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
                Thread.sleep(2500);
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

    private void startMiniGame(List miniGames){
        System.out.println("starttttttttt");
        Random rand = new Random();
        int randomSpiel = rand.nextInt(miniGames.size());
        System.out.println("random Number"+randomSpiel);
        int miniGameNumber = (int) miniGames.get(randomSpiel);
        System.out.println("miniGameNumber"+miniGameNumber);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sind Sie sicher?", ButtonType.OK);
        alert.setContentText("Mini Game " + miniGameNames.get(miniGameNumber-1) + miniGameNumber+ " wird gestartet! "+ miniGames);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                StageChanger.setScene(miniGameNumber);
            }
        });
        removeMiniGame(miniGameNumber);
    }
    private void removeMiniGame(int gameNumber){
        miniGames.remove(Integer.valueOf(gameNumber));
    }
    private boolean noMiniGamesLeft(){
        return miniGames.isEmpty();
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
