package net.rknabe.marioparty.MainGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
        FXMLLoader fxmlLoaderGame6 = new FXMLLoader(MainApplication.class.getResource("../game6-view.fxml"));
        game6Scene = new Scene(fxmlLoaderGame6.load(), 600, 400);

        StageChanger.createStageController(window,mainMenuScene, game1Scene,game2Scene,game3Scene,game4Scene,game5Scene,game6Scene);

        window.setScene(mainMenuScene);

        window.setTitle("Mario Party Spiel");
        window.show();
    }

    @FXML
    public void initialize() {

        drawer.drawBackground(gameField);

        gridPane = new GridPane();
        miniGames = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        System.out.println(miniGames);

        board.setupBoard(gridPane);
        board.numberFieldsDFS(gridPane, 0, 0);
        drawer.drawPicture(playerPicture);
        drawer.drawGoalImage(gridPane, 36, board);


        board.setFieldStateBasedOnColor();
        gameField.getChildren().add(gridPane);
        //board.printFields();
        gameField.setBorder(Border.stroke(Color.BLACK));
        player1 = new Player("Player 1", false, "/net/rknabe/marioparty/assets/MainGame/player1.png");
        player2 = new Player("Computer", true, "/net/rknabe/marioparty/assets/MainGame/browser.png");
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
        System.out.println("Old Position: " + oldPosition);
        drawer.removePlayerImage(gridPane, oldPosition, board); // Remove the player image from the old position

        currentPlayer.move(diceNumber);
        if (currentPlayer.getPosition() >= 36) {
            setGameEnd(currentPlayer);
        }
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
            drawer.drawPlayerImage(gridPane, currentPlayer.getPosition(), currentPlayer, board);


            // Überprüfen Sie, ob der andere Spieler auf der alten Position des aktuellen Spielers ist
            if (otherPlayer.getPosition() == oldPosition) {
                int otherPlayerIndex = otherPlayer.equals(player1) ? 0 : 1;
                drawer.drawPlayer(gridPane, oldPosition, otherPlayerIndex, board);
            }

            checkPlayersOnSameField();
        }
        updateLabels();
        System.out.println("Player: " + currentPlayer.getName() + " Position: " + currentPlayer.getPosition() + " Coins: " + currentPlayer.getCoins());
        startMiniGame(miniGames);
    }
    private void checkPlayersOnSameField() {
        if (player1.getPosition() == player2.getPosition()) {
            Field field = board.getFieldByNumber(player1.getPosition());
            if (field != null) {
                // Erstellen Sie ein ImageView mit dem gewünschten Bild
                URL resourceUrl = getClass().getResource("/net/rknabe/marioparty/assets/MainGame/players_board.jpg");
                Image image = new Image(resourceUrl.toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(43); // Setzen Sie die Breite auf die Breite des Rechtecks
                imageView.setFitHeight(43); // Setzen Sie die Höhe auf die Höhe des Rechtecks

                // Fügen Sie das ImageView zum GridPane hinzu

                gridPane.add(imageView, field.getY(), field.getX());

            }
        }
    }
    private void setGameEnd(Player player) {
        // Code to end the game goes here
        System.out.println(player.getName() + " has reached the goal!");
        showEndGameAlert(player.getName() + " hat das Ziel erreicht und das Spiel gewonnen!");
    }
    private void showEndGameAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spielende");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void updateLabels() {
        marioCoins.setText("Coins: " + player1.getCoins());
        bowserCoins.setText("Coins: " + player2.getCoins());
        marioPosition.setText("Position: " + player1.getPosition());
        bowserPosition.setText("Position: " + player2.getPosition());
    }

}