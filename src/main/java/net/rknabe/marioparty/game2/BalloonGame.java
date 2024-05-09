package net.rknabe.marioparty.game2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BalloonGame extends GameController implements Initializable {

    private static final int NUM_BALLOONS = 25;
    private int balloonsPopped;
    private int playerScore = 0;
    private int computerScore = 0;
    private IntializeBalloons initializer = new IntializeBalloons();
    private Drawer drawer = new Drawer(initializer);
    private GameState gameState = new GameState();


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
    public Label balloonsInflated;
    @FXML
    public Label balloonsLeft;
    @FXML
    public Canvas gameCanvas;
    @FXML
    private Button startGame;

    @Override
    @FXML
    protected void backToMenuClick() {
        endGame(false, true);
        StageChanger.setScene(0);
    }
    @FXML
    protected void startGameClick() {
        reset();
        initializer.createBalloons(NUM_BALLOONS, gameCanvas);
        System.out.println(initializer.getBalloons());
        gameLoop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balloonsInflated.setText("0");
        balloonsLeft.setText(NUM_BALLOONS + "");
        drawer.drawBackground(myAnchorPane);
        drawer.drawSpikes(imageView1, imageView2, imageView3, imageView4);

        gameCanvas.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            Balloon balloonToRemove = null;
            for (Balloon balloon : initializer.getBalloons()) {
                double balloonMinX = balloon.getX();
                double balloonMaxX = balloon.getX() + balloon.getBalloonImage().getFitWidth();
                double balloonMinY = balloon.getY();
                double balloonMaxY = balloon.getY() + balloon.getBalloonImage().getFitHeight();

                if (clickX >= balloonMinX && clickX <= balloonMaxX && clickY >= balloonMinY && clickY <= balloonMaxY) {
                    balloonToRemove = balloon;
                    balloon.setPopped(true);
                    updateBallonsPopped();
                    updateBalloonsLeft();
                    break;
                }
            }
            if (balloonToRemove != null) {
                initializer.removeBalloon(balloonToRemove);
                Drawer.remove(gameCanvas, balloonToRemove);
                drawer.drawExplosion(myAnchorPane, balloonToRemove);
            }
        });
    }

    private void updateBallonsPopped() {
        balloonsInflated.setText(balloonsPopped++ + "");
    }

    private void updateBalloonsLeft() {
        balloonsLeft.setText(toString().valueOf(initializer.getBalloons().size() - 1));
    }

    public void gameLoop() {
        if (initializer == null) {
            System.out.println("Initializer is null. Please make sure to call startGameClick before gameLoop.");
            return;
        }
        new Thread(() -> {
            for (Balloon balloon : initializer.getBalloons()) {
                new Thread(() -> {
                    while (!balloon.isPopped() && !gameState.isEnd()) {
                        final Balloon b = balloon;
                        Platform.runLater(() -> {
                            b.move();
                            Drawer.remove(gameCanvas, b);
                            drawer.redrawCanvas(gameCanvas);
                            // has to update every ballon, so they don't overlap
                        });
                        if (b.hasReachedTop()) {
                            Platform.runLater(() -> {
                                Drawer.remove(gameCanvas, b);
                                initializer.removeAllBalloons();
                                balloonsLeft.setText(toString().valueOf(initializer.getBalloons().size()));
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
        // Reset the number of balloons left
        initializer.removeAllBalloons();
        gameState.setEnd(false);
        balloonsPopped = 0;
        updateBallonsPopped();
        balloonsLeft.setText(NUM_BALLOONS + "");
    }

    protected void endGame(boolean playerWon, boolean reset) {
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
                    //todo
                    playerScore += 50;
                    // set image
                    // Load the first image
                    Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/gewonnenText.png").toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(175);
                    imageView.setFitHeight(30);
                    alert.setGraphic(imageView);

                } else {
                    computerScore += 50;

                    // Load the second image
                    Image image2 = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/verlorenText.png").toExternalForm());
                    ImageView imageView2 = new ImageView(image2);
                    imageView2.setFitWidth(175);
                    imageView2.setFitHeight(30);
                    // Set the Pane as the graphic for the alert
                    alert.setGraphic(imageView2);
                }

                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == okButton) {
                    StageChanger.setScene(0); // Zurück zum Hauptspiel
                }
            });
        }
    }
}