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
    private boolean win;


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


    @Override
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Das Ziel des Spiels ist es, alle Felder aufzudecken, ohne auf eine Mine zu klicken. Der Spieler interagiert mit einem Rasterfeld, das aus verdeckten Feldern besteht, die zufällig mit Minen belegt sind.\n\n"
                + "Rechtsklick wird verwendet, um ein Feld als möglichen Standort einer Mine zu markieren. Wenn ein Spieler vermutet, dass sich an einem bestimmten Ort eine Mine befindet, kann er dieses Feld markieren, um es zu kennzeichnen.\n\n"
                + "Linksklick wird verwendet, um ein Feld aufzudecken. Wenn das Feld eine Mine enthält, verliert der Spieler das Spiel. Wenn das Feld keine Mine enthält, wird angezeigt, wie viele Minen in den angrenzenden Feldern vorhanden sind.\n\n "
                + "Das Spiel endet, wenn alle Felder außer den Minenfeldern aufgedeckt sind,  der Spieler auf eine Mine klickt oder zu viele Markierungen setzt.\n\n"
                + "Viel Spaß und Vorsicht!.");


        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

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
        if (win){
            getInstance().addCoinsToPlayer1(50);
        }
        else {
            getInstance().addCoinsToPlayer2(50);
        }
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
    public void setWin(){
        this.win = true;
    }
}