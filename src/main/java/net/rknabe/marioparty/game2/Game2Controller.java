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
import net.rknabe.marioparty.StageChanger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Game2Controller implements Initializable {

    private static final int NUM_BALLOONS = 70;
    private final List<Balloon> balloons = new ArrayList<>();
    private int deploy_speed;


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
        // delete all the balloons on the canvas
        // reset the score
        List<Balloon> balloonsToRemove = new ArrayList<>(balloons);
        for (Balloon balloon : balloonsToRemove) {
            Balloon.remove(balloon);
            balloons.remove(balloon);
        }
        // todo: maybe?
    }

    @FXML
    private Button startGame;
    @FXML
    protected void startGameClick() {
        // create all the Balloons and display them on the canvas, but:
        // make sure the next balloon has an y ->   previousY-60 > y or y > previousY +60
        double previousX = gameCanvas.getWidth()/2;

        for (int i = 0; i < NUM_BALLOONS; i++) {
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

    private void increaseSpeeds(){
        new Thread(() -> {
            while (!isEnd()) {
                try {
                    Thread.sleep(1000); // wait for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                deploy_speed = (int)(Math.random() * (1000 - 700)) + 700;
            }
            }).start();
    }


    protected void Effects(){
        // todo
        //sound effekt and pop animation
    }

    public void gameLoop() {
        increaseSpeeds();
        new Thread(() -> {
            for (Balloon balloon : balloons) {
                new Thread(() -> {
                    while (!isEnd()) {
                        final Balloon b = balloon;
                        Platform.runLater(() -> {
                            b.move();
                            redrawCanvas();
                        });
                        try {
                            Thread.sleep(b.getMoveSpeed());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                try {
                    Thread.sleep(deploy_speed);
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

    protected boolean isEnd() {
        //todo
        if (balloons.isEmpty()) {
            return true;
        }
        // else if (ballon on top event) {
         //   return true;}
        return false;
    }

    protected void endGame() {
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


        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/metal-spikes.png").toExternalForm());        imageView1.setImage(image);
        imageView1.setImage(image);
        imageView2.setImage(image);
        imageView3.setImage(image);
        imageView4.setImage(image);

        gameCanvas.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            for (Balloon balloon : balloons) {
                double balloonMinX = balloon.getX();
                double balloonMaxX = balloon.getX() + balloon.getBalloonImage().getFitWidth();
                double balloonMinY = balloon.getY();
                double balloonMaxY = balloon.getY() + balloon.getBalloonImage().getFitHeight();

                if (clickX >= balloonMinX && clickX <= balloonMaxX && clickY >= balloonMinY && clickY <= balloonMaxY) {
                    Balloon.remove(balloon);
                    Effects();
                    Player.increaseScore();
                    updateScore();
                    updateBalloonsLeft();
                    // todo add other updated labels
                    break;
                }
            }
        });
    }


}