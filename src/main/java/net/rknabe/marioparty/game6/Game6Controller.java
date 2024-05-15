package net.rknabe.marioparty.game6;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import net.rknabe.marioparty.GameController;
import java.util.Optional;


public class Game6Controller extends GameController {
    private MinesWeeperApp game;
    private GameTimer gameTimer;
    private boolean gameOver = false;
    private static Game6Controller instance;
    private String showGameOverMessage;


    @FXML
    private Label gameStatusLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private Label bombsLabel;
    @FXML
    private StackPane game6Pane;
    @FXML
    private ComboBox<String> cmb_auswahl;

    public Game6Controller() {
        instance = this;
    }
    public static Game6Controller getInstance() {
        return instance;
    }

    public void initialize() {
        game = new MinesWeeperApp();
        cmb_auswahl.setValue("easy");
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // Set the game over status and show a message (Alert) to the user
    public void setGameOver(boolean gameOver, String message) {
        this.gameOver = gameOver;
        Platform.runLater(() -> {
            gameStatusLabel.setText(message);
            gameStatusLabel.setVisible(true);
        });
        gameTimer.interrupt();

        PauseTransition pauseBeforeAlert = new PauseTransition(Duration.seconds(1)); // 2 seconds delay
        pauseBeforeAlert.setOnFinished(event -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Spielende");
                alert.setHeaderText(showGameOverMessage);

                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == okButton) {

                        resetGame();
                        initializeGame_after_newGame();
                        gameStatusLabel.setVisible(false);
                        bombsLabel.setVisible(false);
                        timerLabel.setVisible(false);
                        backToMenuClick();

                }
            });
        });
        pauseBeforeAlert.play();
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    @FXML
    void click(ActionEvent event) {
        setDifficultyFromSelection();
        newGame();
    }

    private void setDifficultyFromSelection() {
        String selectedDifficulty = cmb_auswahl.getValue();
        switch (selectedDifficulty) {
            case "easy":
                game.getBoard().setDifficulty("easy");
                break;
            case "hard":
                game.getBoard().setDifficulty("hard");
                break;
        }
        System.out.println("Schwierigkeit:" + selectedDifficulty);
    }

    public void newGame() {
        resetGame();
        initializeGame_after_newGame();
        bombsLabel.setVisible(true);
        timerLabel.setVisible(true);
        gameStatusLabel.setVisible(true);
        startGame();
    }

    private void resetGame() {
        game6Pane.getChildren().remove(game);
        if (gameTimer != null) {
            gameTimer.interrupt();
            gameTimer.updateLabel(0);
        }
        gameOver = false;
    }

    private void initializeGame_after_newGame() {
        game = new MinesWeeperApp();
        setDifficultyFromSelection(); // this is important so you dont have to select the difficulty every time you start a new game
        game6Pane.getChildren().add(game);
    }

    private void startGame() {
        game.startGame();
        gameTimer = new GameTimer(game.getBoard(), timerLabel);
        gameTimer.start();
        gameStatusLabel.setText("Game started. Good luck!");
        bombsLabel.setText(String.valueOf(game.getBoard().getTotalBombs()));
    }

    public String showGameOverMessage(String message) {
        this.showGameOverMessage = message;
        return message;
    }
}