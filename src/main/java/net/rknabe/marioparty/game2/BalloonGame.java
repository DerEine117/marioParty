package net.rknabe.marioparty.game2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.rknabe.marioparty.GameController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BalloonGame extends GameController implements Initializable {

    private static final int NUM_BALLOONS = 30;
    private int numBalloonsInflated;
    private int numBalloonsLeft;
    private final InitializeBalloons initializer = new InitializeBalloons();
    private final Drawer drawer = new Drawer(initializer);
    private final GameState gameState = new GameState();

    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Label balloonsInflated;
    @FXML
    private Label balloonsLeft;
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Button startGame;

    @Override
    @FXML
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Klicke auf die Ballons, um sie zum Platzen zu bringen.\n" +
                "Du gewinnst, wenn du alle Ballons zum Platzen bringst,\n" +
                "bevor sie den oberen Bildschirmrand erreichen.\n" +
                "Viel Spaß!");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    @FXML
    private void startGameClick() {
        reset();
        initializer.createBalloons(NUM_BALLOONS, gameCanvas);
        initializer.sortBalloonsByDeploySpeed();
        gameLoop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numBalloonsInflated = 0;
        numBalloonsLeft = NUM_BALLOONS;
        updateBallonsInflated();
        updateBalloonsLeft();

        drawer.drawBackground(myAnchorPane);
        drawer.drawSpikes(imageView1, imageView2, imageView3, imageView4);

        gameCanvas.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            Optional<Balloon> balloonToRemove = initializer.getBalloons().stream()
                    .filter(balloon -> {
                        double balloonMinX = balloon.getX();
                        double balloonMaxX = balloon.getX() + balloon.getBalloonImage().getFitWidth();
                        double balloonMinY = balloon.getY();
                        double balloonMaxY = balloon.getY() + balloon.getBalloonImage().getFitHeight();

                        return clickX >= balloonMinX && clickX <= balloonMaxX && clickY >= balloonMinY && clickY <= balloonMaxY;
                    })
                    .findFirst();

            balloonToRemove.ifPresent(balloon -> {
                balloon.setPopped(true);
                numBalloonsLeft--;
                numBalloonsInflated++;
                updateBallonsInflated();
                updateBalloonsLeft();

                initializer.removeBalloon(balloon);
                Drawer.remove(gameCanvas, balloon);
                drawer.drawExplosion(myAnchorPane, balloon);
            });
        });
    }

    private void updateBallonsInflated() {
        balloonsInflated.setText(String.valueOf(numBalloonsInflated));
    }

    private void updateBalloonsLeft() {
        balloonsLeft.setText(String.valueOf(numBalloonsLeft));
    }

    private void gameLoop() {
        // Check if the game has ended at the start of the game loop
        if (gameState.isEnd()) {
            return;
        }

        new Thread(() -> {
            for (Balloon balloon : initializer.getBalloons()) {
                // Check if the game has ended before starting a new thread for a balloon
                if (gameState.isEnd()) {
                    break;
                }

                new Thread(() -> {
                    while (!balloon.isPopped() && !gameState.isEnd()) {
                        final Balloon b = balloon;
                        Platform.runLater(() -> {
                            b.move();
                            Drawer.remove(gameCanvas, b);
                            drawer.redrawCanvas(gameCanvas);
                        });
                        if (b.hasReachedTop()) {
                            Platform.runLater(() -> {
                                Drawer.remove(gameCanvas, b);
                                initializer.removeAllBalloons();
                                endGame(false, false);
                            });
                        }
                        try {
                            Thread.sleep(b.getMoveSpeed());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (initializer.getBalloons().isEmpty()) {
                            endGame(true, false);
                        }
                    }
                }).start();
                try {
                    Thread.sleep(balloon.getDeploySpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void reset() {
        // Stop the game
        endGame(false, true);
        // Resets the number of balloons left
        initializer.removeAllBalloons();
        gameState.setEnd(false);
        numBalloonsInflated = 0;
        updateBallonsInflated();
        numBalloonsLeft = NUM_BALLOONS;
        updateBalloonsLeft();
        balloonsLeft.setText(NUM_BALLOONS + "");
    }

    private void endGame(boolean playerWon, boolean reset) {
        // Check if the game has already ended
        // and creates an alert dialog to inform the player
        if (gameState.isEnd()) {
            return;
        }
        gameState.setEnd(true);
        initializer.removeAllBalloons();
        drawer.redrawCanvas(gameCanvas);

        if (!reset) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Spielende");
                alert.setHeaderText(null);
                if (playerWon) {
                    getInstance().addCoinsToPlayer1(50);
                    drawer.drawText(true, alert);

                } else {
                    getInstance().addCoinsToPlayer2(50);
                    drawer.drawText(false, alert);
                }

                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == okButton) {
                    backToMenuClick();
                    getInstance();
                }
            });
        }
    }
}