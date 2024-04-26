package net.rknabe.marioparty.game2;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Game2Controller implements Initializable {

    private static final int NUM_BALLOONS = 50;
    private static final double MOVE_SPEED_INCREASEMENT = 0.1;
    private double move_speed = 1.0;
    private final List<Balloon> balloons = new ArrayList<>();
    private int deploy_speed = 2000; // milliseconds
    private static final int DEPLOY_SPEED_INCREASEMENT = 500; // milliseconds

    // todo initialize the construktor and add the number of balloons to the list(NUM_BALLOONS)


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
        // Start the game loop here, if you have one
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
        //
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