package net.rknabe.marioparty.game4;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        // Mithilfe von currentTimeMillis wird alle 125 Millisekunden ein gameTick ausgeführt (Maß für die Schwierigkeit -> niedrigere Zahl -> schnellere/mehr Aktualisierung -> schnellere Schlange)
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 125) {
            lastUpdateTime = currentTime;

            snake.updateSnakeMovement();
            snake.draw(snakepane);
            checkFruitCollision();
            if (snake.isGameOver()) {
                if (!gamestate.getText().equals("Bowser war besser!")) {
                    updateGamestateTextFieldLost();
                }
            }
            updateLengthTextField();
        }
    }

    @FXML
    public void onKeyPressedMove(KeyEvent event) {
        // Der Eventhandler soll nur eine Tastatureingabe akzeptieren, wenn man noch nicht verloren hat. Sonst könnte man nach Niederlage einfach weiter spielen
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
        gamestate.setText("Bowser war besser!");
        showAlert("Spielende", "Du hast Verloren!", "Wiggler ist sauer auf dich! Bowser hat ihm besser geholfen.\n \n" +
                "Du erhältst keine Münzen und machst dich schnell aus dem Staub.", "WigglerAngry.gif");
        snake.stopSnakeMovement();
        // Die Schlange soll stehen bleiben, wenn man "verloren" hat
    }
    @FXML
    private void updateGamestateTextFieldWon() {
        gamestate.setText("Bowser wurde besiegt!");
        showAlert("Spielende", "Du hast Gewonnen!", "Wiggler bedankt sich bei dir und ist glücklich, dass du ihm besser helfen konntes als Bowser es tat! \n \n" +
                "Als Belohnung gibt er dir 20 Münzen.", "WigglerHappy.gif");
        snake.stopSnakeMovement();
    }

    //Methode die Dialogfeld für Game Ende erstellt
    private void showAlert(String title, String headerText, String contentText, String imageName) {
        // Thread, da Dialogfeld nicht mit aktivem Animationtimer funktioniert. Durch Thread behindern sich die beiden Aktionen nicht mehr
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);

            // Wiggler Image abhängig von Ausgabe setzen
            Image wigglerImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game4/" + imageName).toExternalForm());
            ImageView wigglerImageView = new ImageView(wigglerImage);
            alert.setGraphic(wigglerImageView);

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

        fruit.setXPos(400);
        fruit.setYPos(300);
    }

    // onSpielInfoClick Methode aus Superklasse wird überschrieben und passender Text eingefügt
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Wiggler hat großen Hunger. Hilf ihm satt zu werden und führe ihn zu den Pilzen (Steuerung WASD).\n" +
                "Für jeden Pilz wird Wiggler um ein Feld länger. Ziel ist es, Wiggler auf eine größere Länge wachsen zu lassen, als es Bowser vor dir geschafft hat.\n" +
                "Oben rechts siehst du, was Bowser erreicht hat.");


        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        // Um nach Rückkehr des Infotextes wieder spielen zu können, muss der Fokus neu aufs snakepane gerichtet werden
        alert.setOnHidden(event -> snakepane.requestFocus());
        alert.showAndWait();
    }
}