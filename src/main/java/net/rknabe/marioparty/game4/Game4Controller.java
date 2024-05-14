package net.rknabe.marioparty.game4;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;

public class Game4Controller extends GameController {
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Pane snakepane;
    @FXML
    private Pane fruitPane;
    @FXML
    private TextField lengthSnake;
    @FXML
    private TextField gamestate;
    private BackgroundBoard backgroundBoard;
    private Snake snake;
    private Fruit fruit;
    private Computerplayer bestComputerPlayer;
    private long lastUpdateTime;

    public void initialize() {
        backgroundBoard = new BackgroundBoard(backgroundPane);
        backgroundBoard.drawBackground();

        snake = new Snake();
        snake.draw(snakepane);

        fruit = new Fruit();
        fruit.draw(fruitPane);

        bestComputerPlayer = new Computerplayer();

        lastUpdateTime = System.currentTimeMillis();

        // Anonymen Klasse von Interface: AnimationTimer
        AnimationTimer gameTicks = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // wird bei jedem "Tick" ausgeführt
                update();
            }
        };
        gameTicks.start();
    }

    public void update() {
        // Mithilfe von currentTimeMillis wird alle 75 Millisekunden ein gameTick ausgeführt
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 75) {
            lastUpdateTime = currentTime;

            snake.updateSnakeMovement();
            snake.draw(snakepane);
            checkFruitCollision();
            if (snake.isGameOver()) {
                if (!gamestate.getText().equals("Verloren!")) {
                    updateGamestateTextFieldLost();
                }
            }
            updateLengthTextField();
        }
    }

    @FXML
    public void onKeyPressedMove(KeyEvent event) {
        // Der Eventhandler soll nur eine Tastatureingabe akzeptieren, wenn man noch nicht verloren hat. Sont könnte man nach Niederlage einfach weiter spielen
        // Eventhandler funktioniert, jedoch auch wenn die angegebene Länge erreicht wurde -> Highscore möglich (Freeplay)
        if (!snake.isGameOver()) {
            String keyPressed = event.getText();
            switch (keyPressed) {
                // Bei einer Anweisung muss immer kontrolliert werden, ob die letzte Richtung nicht die Entgegengesetzte war -> Schlange kann sich nicht um 180 Grad drehen, weil sie sonst in ihr selbst wäre.
                case "w":
                    if (!snake.getLastDirection().equals("s")) {
                        snake.setUpPressed(true);
                        snake.setDownPressed(false);
                        snake.setLeftPressed(false);
                        snake.setRightPressed(false);
                    }
                    break;
                case "s":
                    if (!snake.getLastDirection().equals("w")) {
                        snake.setUpPressed(false);
                        snake.setDownPressed(true);
                        snake.setLeftPressed(false);
                        snake.setRightPressed(false);
                    }
                    break;
                case "a":
                    if (!snake.getLastDirection().equals("d")) {
                        snake.setUpPressed(false);
                        snake.setDownPressed(false);
                        snake.setLeftPressed(true);
                        snake.setRightPressed(false);
                    }
                    break;
                case "d":
                    if (!snake.getLastDirection().equals("a")) {
                        snake.setUpPressed(false);
                        snake.setDownPressed(false);
                        snake.setLeftPressed(false);
                        snake.setRightPressed(true);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    void checkFruitCollision() {
        double snakeHeadX = snake.getHeadX();
        double snakeHeadY = snake.getHeadY();

        // Falls Koordinaten von Kopf mit Koordinaten von Frucht übereinstimmen -> Schlange bekommt ein Körperteil, Frucht bekommt neues Feld
        if (snakeHeadX == fruit.getXPos() && snakeHeadY == fruit.getYPos()) {
            snake.addBodyPart();
            fruit.move(snake);

            // Falls durch die vergangene Frucht die länge des Gegenspielers erreicht wurde, ist das Spiel gewonnen
            if (snake.getSnakeLength() == bestComputerPlayer.getReachedLength()) {
                updateGamestateTextFieldWon();
            }
        }
    }

    @FXML
    void updateLengthTextField() {
        lengthSnake.setText("Länge: " + snake.getSnakeLength() + "/" + bestComputerPlayer.getReachedLength());
    }

    @FXML
    void updateGamestateTextFieldLost() {
        // Wenn die Schlange bereits größer als die des Gegngers ist, kann man nicht mehr verlieren
        if (snake.getSnakeLength() < bestComputerPlayer.getReachedLength()) {
            gamestate.setText("Verloren!");
            showAlert("Spielende", "Verloren!", "Du hast das Spiel verloren.");
        }
        snake.stopSnakeMovement();
        // die Schlange soll stehen bleiben wenn man "verloren" hat
    }

    @FXML
    private void updateGamestateTextFieldWon() {
        gamestate.setText("Gewonnen!");
        showAlert("Spielende", "Gewonnen!", "Herzlichen Glückwunsch! Du hast das Spiel gewonnen.");
        snake.stopSnakeMovement();
        // auch wenn man gewonnen hat soll sie stehen bleiben, die Option auf weiter spielen besteht jedoch
    }

    private void showAlert(String title, String headerText, String contentText) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.showAndWait();
            StageChanger.setScene(0);
            reset();
        });
    }

    //Falls Snake Game nochmals gespielt wird
    public void reset() {
        // Schlange auf werkseinstellungen
        snake.reset();
        snake.draw(snakepane);

        // Spielstatutus auf Werkseinstellungen
        gamestate.setText("");

        // Neue Leistung des Computers
        bestComputerPlayer.newLength();
    }
}