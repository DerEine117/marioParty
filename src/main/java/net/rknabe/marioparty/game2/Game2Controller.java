package net.rknabe.marioparty.game2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.rknabe.marioparty.StageChanger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Game2Controller implements Initializable {

    private static final int NUM_BALLOONS = 5;
    private final ConcurrentLinkedQueue<Balloon> balloons = new ConcurrentLinkedQueue<>();
    private boolean end = false;

    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private ImageView background;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;

    @FXML
    private Button backToMenu;
    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
    @FXML
    private Button reset;
    @FXML
    public Label playerScore;
    @FXML
    public Label balloonsLeft;
    @FXML
    public Canvas gameCanvas;

    @FXML
    protected void resetClick(){
        // Stop the game
        setEnd(true);

        // Reset the player's score
        updateScore();

        // Reset the number of balloons left
        balloons.clear();
        updateBalloonsLeft();

        // Clear the canvas
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    @FXML
    private Button startGame;
    @FXML
    protected void startGameClick() {
        // create all the Balloons and display them on the canvas, but:
        // make sure the next balloon has an y ->   previousY-60 > y or y > previousY +60
        // make them more spread out
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

    private void updateScore() {
        playerScore.setText(toString().valueOf(Player.getScore()));
    }
    private void updateBalloonsLeft() {
        balloonsLeft.setText(toString().valueOf(balloons.size()));
    }


    protected void Effects(){
        // todo
        //sound effekt and pop animation
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
                                endGame(true);
                            });
                        }
                        try {
                            Thread.sleep(b.getMoveSpeed());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (balloons.isEmpty()) {
                            endGame(true);
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


    private boolean isEnd() {
        return end;
    }

    private void setEnd(boolean end) {
        this.end = end;
    }

    protected void endGame(boolean playerWon) {
        setEnd(true);
        System.out.println("Game Over");
        // todo
        // show end screen
        // show score
        // show highscore
        // show play again button
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // create a new Player
        Player player = new Player();


        // set the background image
        String imageUrl = getClass().getResource("/net/rknabe/marioparty/assets/backgroundGame2.png").toExternalForm();
        myAnchorPane.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: cover;");

        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/metal-spikes.png").toExternalForm());        imageView1.setImage(image);
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

                    Effects();
                    Player.increaseScore();
                    // todo, player score shouldnt = popped balloons
                    // should be initialized 1x in endGame(true)
                    // change the variable that is displayed in the label

                    updateScore();
                    updateBalloonsLeft();
                    break;
                }
            }
            if (balloonToRemove != null) {
                balloons.remove(balloonToRemove);
                Balloon.remove(balloonToRemove);
            }
        });
    }


}