package net.rknabe.marioparty.game2;


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

    private static final int NUM_BALLOONS = 5;
    private static final double MOVE_SPEED_INCREASEMENT = 1;
    private double move_speed = 5.0;
    private final List<Balloon> balloons = new ArrayList<>();
    private int deploy_speed = 3000; // milliseconds
    private static final int DEPLOY_SPEED_INCREASEMENT = 20; // milliseconds

    // todo initialize the construktor and add the number of balloons to the list(NUM_BALLOONS)

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
    protected void resetClick(){
        // delete all the balloons on the canvas
        // reset the score
        List<Balloon> balloonsToRemove = new ArrayList<>(balloons);
        for (Balloon balloon : balloonsToRemove) {
            Balloon.remove(balloon);
            balloons.remove(balloon);
        }
    }

    @FXML
    private Button startGame;
    @FXML
    protected void startGameClick() {
        for (int i = 0; i < NUM_BALLOONS; i++) {
            Balloon balloon = new Balloon(gameCanvas);
            balloons.add(balloon);
            balloon.display(balloon);
        }
        gameLoop();
    }
    @FXML
    public Label playerScore;
    @FXML
    public Label computerScore;
    @FXML
    public Label balloonsLeft;
    @FXML
    public Canvas gameCanvas;

    private GraphicsContext gc;

    protected void increaseMove(){
        move_speed += MOVE_SPEED_INCREASEMENT;
    }

    protected void increaseDeploy(){
        deploy_speed -= DEPLOY_SPEED_INCREASEMENT;
    }

    protected void Effects(){
        //sound effekt and pop animation
    }

    public void updateScore() {
        playerScore.setText("Score: " + Player.getScore());
    }

    public void gameLoop() {
        new Thread(() -> {
            for (Balloon balloon : balloons) {
                new Thread(() -> {
                    while (!isEnd()) {
                        balloon.move(balloon, move_speed);
                        try {
                            Thread.sleep(100);
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

    protected boolean isEnd() {
        if (balloons.isEmpty()) {
            return true;
        }
        // else if (ballon on top event) {
         //   return true;}
        return false;
    }

    protected void endGame() {
        // show end screen
        // show score
        // show highscore
        // show play again button
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // create a new Player
        // todo: go through the list of balloons and make one after another visible and move up the screen
        // iterate through list with thread that sleeps for deploy_speed milliseconds
        // if balloon is clicked-> balloon.remove and Player.increaseSc the speed of the balloons

        // todo: set the metal_spikes.png in the Hbox in the fxml file

        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/metal-spikes.png").toExternalForm());
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
                    increaseMove();
                    increaseDeploy();
                    break;
                }
            }
        });
    }


}