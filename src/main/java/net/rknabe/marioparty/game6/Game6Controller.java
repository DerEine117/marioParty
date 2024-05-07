package net.rknabe.marioparty.game6;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import net.rknabe.marioparty.GameController;

public class Game6Controller extends GameController {
    private MinesWeeperApp game;
    private GameTimer gameTimer;
    private boolean gameOver = false;

    @FXML
    private Label gameStatusLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label bombsLabel;

    @FXML
    private StackPane game6Pane;


    private static Game6Controller instance;

    public Game6Controller() {
        instance = this;
    }

    public static Game6Controller getInstance() {
        return instance;
    }

    public void initialize() {
        System.out.println("initialize was called"); // Debug output
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
    protected void newGame() {
        // Das Spiel zurücksetzen
        game6Pane.getChildren().remove(game);
        if (gameTimer != null) {
            gameTimer.stop(); // Stop the existing timer
        }
        game = new MinesWeeperApp();
        game.setPrefSize(500, 500); // Set the preferred size of the MinesWeeperApp pane

        game6Pane.getChildren().add(game);
        game6Pane.setAlignment(Pos.CENTER); // Center the MinesWeeperApp pane in the game6Pane
        Scene scene = game6Pane.getScene();
        game.initializeBackground(scene);
        game.startGame();
        gameTimer = new GameTimer(game.getBoard());
        gameTimer.start(timerLabel);
        gameOver = false;
        gameStatusLabel.setText("Spiel gestartet. Viel Glück!");
        bombsLabel.setText(String.valueOf(game.getBoard().getTotalBombs()));
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver, String message) {
        this.gameOver = gameOver;
        gameStatusLabel.setText(message);
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }
}