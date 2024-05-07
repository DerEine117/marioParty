package net.rknabe.marioparty.game6;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game6Controller {

    private boolean gameOver = false;


    private static Game6Controller instance;

    public Game6Controller() {
        instance = this;
    }

    public static Game6Controller getInstance() {
        return instance;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        gameTimer.stop();
    }
    @FXML
    private Button newGameButton;

    @FXML
    private Label timerLabel;

    @FXML
    private BorderPane root;


    @FXML
    private Pane game6Pane; // Dieses Pane wird in der FXML-Datei definiert

    private MinesWeeperApp game;

    private GameTimer gameTimer = new GameTimer();


    @FXML
    protected void newGame() {
        // Das Spiel zurücksetzen
        game6Pane.getChildren().remove(game);
        game = new MinesWeeperApp();
        game6Pane.getChildren().add(game);
        game.startGame();
        gameTimer.start(timerLabel);
        gameOver = false;
    }

    public void initialize() {
        System.out.println("initialize was called"); // Debug output

        game = new MinesWeeperApp();
        game6Pane.getChildren().add(game);
        game.startGame();
        gameTimer.start(timerLabel);


    }


    @FXML
    protected void backToMenuClick() {
        // Implementieren Sie die Logik, um zur Menüszene zurückzukehren
    }
}