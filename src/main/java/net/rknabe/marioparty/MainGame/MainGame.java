package net.rknabe.marioparty.MainGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainGame extends GameController {
    Stage window;
    Scene mainMenuScene;
    Scene game1Scene, game2Scene, game3Scene, game4Scene, game5Scene, game6Scene;
    private final Drawer drawer = new Drawer();
    private Dice player1dice = new Dice();
    private Dice player2dice = new Dice();
    private boolean player1HasRolled = false;
    private boolean player2HasRolled = false;
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

    @Override
    @FXML
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Klicke auf den Würfeln-Button, um zu würfeln und das Spiel zu starten.\n" +
                "Das Spiel besteht aus 6 Runden, in denen du abwechselnd würfelst und Minispiele spielst.\n" +
                "Wenn du auf ein gelbem Feld landest, erhältst du 20 Münzen, wenn du auf ein rotes Feld landest, verlierst du 20 Münzen.\n" +
                "Die Minispiele werden zufällig ausgewählt und du kannst sie gewinnen, indem du die Anweisungen befolgst.\n" +
                "Der Spieler mit den meisten Münzen am Ende des Spiels gewinnt.\n" +
                "Viel Spaß!");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    @FXML
    private void onWürfelnClick() {
        würfeln(false);
        würfeln(true);
    }



    @FXML
    public void initialize() {
        startUpdatingLabels();

        drawer.drawBackground(gameField);

        gridPane = new GridPane();
        miniGames = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println(miniGames);

        board.setupBoard(gridPane);
        board.numberFieldsDFS(gridPane, 0, 0);
        drawer.drawPicture(playerPicture);
        drawer.drawGoalImage(gridPane, 36, board);


        board.setFieldStateBasedOnColor();
        gameField.getChildren().add(gridPane);
        //board.printFields();
        gameField.setBorder(Border.stroke(Color.BLACK));
        player1 = getPlayer1();
        player2 = getPlayer2();
        drawer.drawPlayer(gridPane, player1.getPosition(), 0, board);
        drawer.drawPlayer(gridPane, player2.getPosition(), 1, board);
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
                if (computer) {
                    player2HasRolled = true;
                } else {
                    player1HasRolled = true;
                }
                // If both players have rolled, start the mini game
                if (player1HasRolled && player2HasRolled) {
                    if (miniGames.isEmpty()) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Platform.runLater(() -> {
                                setGameEnd();
                            });
                        }).start();
                    } else {
                        startMiniGame(miniGames);
                    }
                    // Reset the flags for the next round
                    player1HasRolled = false;
                    player2HasRolled = false;
                }

            });
        }).start();
    }

    private void startMiniGame(List miniGames) {
        System.out.println("start");
        Random rand = new Random();
        int randomSpiel = rand.nextInt(miniGames.size());
        int miniGameNumber = (int) miniGames.get(randomSpiel);
        System.out.println("miniGameNumber" + miniGameNumber);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sind Sie sicher?", ButtonType.OK);
        alert.setContentText("Mini Game " + miniGameNames.get(miniGameNumber - 1) + miniGameNumber + " wird gestartet! " + miniGames);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                StageChanger.setScene(miniGameNumber);
            }
        });
        removeMiniGame(miniGameNumber);
    }

    private void removeMiniGame(int gameNumber) {
        miniGames.remove(Integer.valueOf(gameNumber));
    }

    private boolean noMiniGamesLeft() {
        return miniGames.isEmpty();
    }

    private void updateGameState(Player currentPlayer, int diceNumber) {
        int oldPosition = currentPlayer.getPosition();
        drawer.removePlayerImage(gridPane, oldPosition, board); // Remove the player image from the old position;
        currentPlayer.move(diceNumber);
        if (currentPlayer.getPosition() >= 36) {
            setGameEnd();
        }
        Field oldField = board.getFieldByNumber(oldPosition);
        if (oldField != null) {
            oldField.setHasPlayer(false); // Der Spieler hat das alte Feld verlassen
        }

        Field newField = board.getFieldByNumber(currentPlayer.getPosition());
        if (newField != null) {
            newField.setHasPlayer(true); // Der Spieler hat das neue Feld betreten
            drawer.resetRectangleColor(gridPane, oldPosition, board);

            Player otherPlayer = currentPlayer.equals(player1) ? player2 : player1;
            if (newField.getFieldNumber() == otherPlayer.getPosition()) {
                newField.setState(0);
                Rectangle rectangle = board.getRectangleByCoordinates(newField.getX(), newField.getY());
                if (rectangle != null) {
                    rectangle.setFill(Color.GRAY);
                }
            } else {
                switch (newField.getState()) {
                    case 1: // gelbes Feld
                        if (currentPlayer.equals(getInstance().getPlayer1())) {
                            getInstance().addCoinsToPlayer1(20);
                            showCoinAlert(currentPlayer.getName(), 20);
                        } else if (currentPlayer.equals(getInstance().getPlayer2())) {
                            getInstance().addCoinsToPlayer2(20);
                            showCoinAlert(currentPlayer.getName(), 20);
                        }
                        Rectangle rectangle = board.getRectangleByCoordinates(newField.getX(), newField.getY());
                        if (rectangle != null) {
                            rectangle.toFront();
                        }
                        break;
                    case 2: // Rotes Feld
                        if (currentPlayer.equals(getInstance().getPlayer1())) {
                            getInstance().addCoinsToPlayer1(-20);
                            showCoinAlert(currentPlayer.getName(), -20);
                        } else if (currentPlayer.equals(getInstance().getPlayer2())) {
                            getInstance().addCoinsToPlayer2(-20);
                            showCoinAlert(currentPlayer.getName(), -20);
                        }
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


            if (otherPlayer.getPosition() == oldPosition) {
                int otherPlayerIndex = otherPlayer.equals(player1) ? 0 : 1;
                drawer.drawPlayer(gridPane, oldPosition, otherPlayerIndex, board);
            }

            checkPlayersOnSameField();
        }
        System.out.println("Player: " + currentPlayer.getName() + " Position: " + currentPlayer.getPosition() + " Coins: " + getInstance().getPlayer1Coins());
    }

    public void showCoinAlert(String playerName, int coinChange) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        if (coinChange > 0) {
            alert.setContentText(playerName + " hat " + coinChange + " Coins bekommen!");
        } else {
            alert.setContentText(playerName + " hat " + Math.abs(coinChange) + " Coins verloren!");
        }
        alert.showAndWait();
    }


    private void checkPlayersOnSameField() {
        if (player1.getPosition() == player2.getPosition()) {
            Field field = board.getFieldByNumber(player1.getPosition());
            if (field != null) {
                URL resourceUrl = getClass().getResource("/net/rknabe/marioparty/assets/MainGame/players_board.jpg");
                Image image = new Image(resourceUrl.toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(43);
                imageView.setFitHeight(43);


                gridPane.add(imageView, field.getY(), field.getX());

            }
        }
    }

    private void setGameEnd() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Das Spiel zu Ende, Runde 6 ist vorbei und es sind keine MiniSpiele mehr übrig!!", ButtonType.OK);
        alert.setTitle("Spielende");
        if (getInstance().getPlayer2Coins() > getInstance().getPlayer1Coins()) {
            alert.setHeaderText(player2.getName() + " hat mit "+getInstance().getPlayer2Coins()+" zu "+getInstance().getPlayer1Coins()+ " Punkten gewonnen!");
        } else if (getInstance().getPlayer1Coins() > getInstance().getPlayer2Coins()) {
            alert.setHeaderText(player1.getName() + " hat mit "+getInstance().getPlayer1Coins()+" zu "+getInstance().getPlayer2Coins()+ " Punkten gewonnen!");
        }
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

    private void updateLabels() {
        marioCoins.setText( String.valueOf(getInstance().getPlayer1Coins()));
        bowserCoins.setText(String.valueOf(getInstance().getPlayer2Coins()));
        marioPosition.setText(String.valueOf(player1.getPosition()));
        bowserPosition.setText(String.valueOf(player2.getPosition()));
    }
    private Timeline updateLabelsTimeline;

    public void startUpdatingLabels() {
        if (updateLabelsTimeline != null) {
            updateLabelsTimeline.stop();
        }
        updateLabelsTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateLabels()));
        updateLabelsTimeline.setCycleCount(Timeline.INDEFINITE);
        updateLabelsTimeline.play();
    }


}