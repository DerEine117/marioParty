package net.rknabe.marioparty.game2;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;



public class Game2Controller extends GameController implements Initializable {

    private static final int NUM_BALLOONS = 25;
    private final ConcurrentLinkedQueue<Balloon> balloons = new ConcurrentLinkedQueue<>();
    private boolean end = false;
    private int balloonsPopped;

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
    @FXML
    protected void startGameClick() {
        // create all the Balloons and display them on the canvas, but:
        // make sure the next balloon has an y ->   previousY-60 > y or y > previousY +60
        // make them more spread out
        reset();
        balloonsPopped = 0;
        double previousX = gameCanvas.getWidth()/2;

        for (int i = 0; i <= NUM_BALLOONS; i++) {
            Balloon balloon = new Balloon(gameCanvas);
            if (i == 0) {
                previousX = balloon.getX();
            } else {
                if (previousX - 60 > balloon.getX() || balloon.getX() > previousX + 60) {
                    balloons.add(balloon);
                    previousX = balloon.getX();
                } else {
                    i--;
                }
            }

        }
        gameLoop();
    }

    private GraphicsContext gc;

    private void updateBallonsPopped() {
        balloonsInflated.setText(increaseBalloonsPopped()+ "");
    }

    private void updateBalloonsLeft() {
        balloonsLeft.setText(toString().valueOf(balloons.size()-1));
    }
    private int increaseBalloonsPopped() {
        balloonsPopped++;
        return balloonsPopped;
    }

    public void gameLoop() {
        new Thread(() -> {
            for (Balloon balloon : balloons) {
                new Thread(() -> {
                    while (!balloon.isPopped() && !isEnd()) {
                        final Balloon b = balloon;
                        Platform.runLater(() -> {
                            b.move();
                            Balloon.remove(b);
                            redrawCanvas();
                        });
                        if (b.hasReachedTop()){
                            Platform.runLater(() -> {
                                Balloon.remove(b);
                                balloons.remove(b);
                                updateBalloonsLeft();
                                endGame(false, false);
                            });
                        }
                        try {
                            Thread.sleep(b.getMoveSpeed());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (balloons.isEmpty()) {
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

    public void redrawCanvas() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); // clear the entire canvas

        for (Balloon balloon : balloons) {
            // redraw the balloon at the updated position
            gc.drawImage(balloon.getBalloonImage().getImage(), balloon.getX(), balloon.getY(), balloon.getBalloonImage().getFitWidth(), balloon.getBalloonImage().getFitHeight());
        }
    }

    private void reset(){
        // zum einfacheren Testen

        // Stop the game
        endGame(false, true);
        // Reset the number of balloons left
        balloons.clear();
        setEnd(false);
    }

    private boolean isEnd() {
        return end;
    }

    private void setEnd(boolean end) {
        this.end = end;
    }

    protected void endGame(boolean playerWon, boolean reset) {
        // Check if the game has already ended
        if (isEnd()) {
            return;
        }

        setEnd(true);
        balloons.clear();
        redrawCanvas();

        if (!reset) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Spielende");
                alert.setHeaderText(null);
                if (playerWon) {
                    Player.increaseScore();
                    alert.setContentText("Sie haben gewonnen!");
                    Image gifImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/konfetti.gif").toExternalForm());
                    ImageView gifImageView = new ImageView(gifImage);

                } else {
                    alert.setContentText("Sie haben verloren!");
                    Computer.increaseScore();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // create a new Player
        Player player = new Player();
        balloonsInflated.setText("0");
        balloonsLeft.setText(NUM_BALLOONS + "");

        // set the background image
        String imageUrl = getClass().getResource("/net/rknabe/marioparty/assets/game2/backgroundGame2.png").toExternalForm();
        myAnchorPane.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: cover;");

        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/metalSpikes.png").toExternalForm());        imageView1.setImage(image);
        imageView1.setImage(image);
        imageView2.setImage(image);
        imageView3.setImage(image);
        imageView4.setImage(image);

        gameCanvas.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            Balloon balloonToRemove = null;
            for (Balloon balloon : balloons) {
                double balloonMinX = balloon.getX();
                double balloonMaxX = balloon.getX() + balloon.getBalloonImage().getFitWidth();
                double balloonMinY = balloon.getY();
                double balloonMaxY = balloon.getY() + balloon.getBalloonImage().getFitHeight();

                if (clickX >= balloonMinX && clickX <= balloonMaxX && clickY >= balloonMinY && clickY <= balloonMaxY) {
                    balloonToRemove = balloon;
                    balloon.setPopped(true);

                    Player.increaseScore();
                    // todo, player score shouldnt = popped balloons
                    // should be initialized 1x in endGame(true)
                    // change the variable that is displayed in the label

                    updateBallonsPopped();
                    updateBalloonsLeft();
                    break;
                }
            }
            if (balloonToRemove != null) {
                balloons.remove(balloonToRemove);
                Balloon.remove(balloonToRemove);


                // GIF
                Image gifImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/platzen.gif").toExternalForm());
                ImageView gifImageView = new ImageView(gifImage);

                // set position
                gifImageView.setX(balloonToRemove.getX()+720-557);
                gifImageView.setY(balloonToRemove.getY()+30);

                myAnchorPane.getChildren().add(gifImageView);

                // remove
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(GIFevent -> myAnchorPane.getChildren().remove(gifImageView));
                pause.play();
            }
        });
    }


}