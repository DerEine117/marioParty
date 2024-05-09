package net.rknabe.marioparty.game6;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import net.rknabe.marioparty.GameController;

import java.net.URL;

public class Game6Controller extends GameController {
    private MinesWeeperApp game;
    private GameTimer gameTimer;
    private boolean gameOver = false;
    @FXML
    private Label gameOverLabel;
    @FXML
    private Label gameStatusLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label bombsLabel;

    @FXML
    private StackPane game6Pane;
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Image backgroundImage;


    @FXML
    private ComboBox<String> cmb_auswahl;




    private static Game6Controller instance;

    public Game6Controller() {
        instance = this;
    }

    public static Game6Controller getInstance() {
        return instance;
    }
    public void initialize() {
        game = new MinesWeeperApp();
        cmb_auswahl.setValue("easy");

        game6Pane.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    newScene.windowProperty().addListener(new ChangeListener<Window>() {
                        @Override
                        public void changed(ObservableValue<? extends Window> observableValue, Window oldWindow, Window newWindow) {
                            if (newWindow != null) {
                                ((Stage) newWindow).addEventHandler(WindowEvent.WINDOW_SHOWN, window -> newGame());
                            }
                        }
                    });
                }
            }
        });
    }

    @FXML
    public void newGame() {
        // Reset the game
        game6Pane.getChildren().remove(game);
        if (gameTimer != null) {
            gameTimer.stop(); // Stop the existing timer
        }
        game = new MinesWeeperApp();

        // Set the difficulty from the selection
        setDifficultyFromSelection();

        game6Pane.getChildren().add(game);
        game.startGame();
        gameTimer = new GameTimer(game.getBoard());
        gameTimer.start(timerLabel);
        gameOver = false;
        gameStatusLabel.setText("Game started. Good luck!");
        bombsLabel.setText(String.valueOf(game.getBoard().getTotalBombs()));
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver, String message) {
        this.gameOver = gameOver;
        Platform.runLater(() -> {
            gameStatusLabel.setText(message);
            gameStatusLabel.setVisible(true);
        });
        if (gameOver) {
            gameTimer.stop();
        }
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    @FXML
    void click(ActionEvent event) {
        setDifficultyFromSelection();
        newGame(); // Start a new game with the selected difficulty
    }

    private void setDifficultyFromSelection() {
        String selectedDifficulty = cmb_auswahl.getValue();
        switch (selectedDifficulty) {
            case "easy":
                game.getBoard().setDifficulty(0.10);
                System.out.println("easy");
                break;
            case "hard":
                game.getBoard().setDifficulty(0.35);
                System.out.println("hard");
                break;

            default:
                System.out.println("Unbekannte Schwierigkeitsstufe ausgewählt");
        }
        System.out.println("Schwierigkeit:" +selectedDifficulty);
    }
}