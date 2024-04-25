package net.rknabe.marioparty.game2;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rknabe.marioparty.StageChanger;
import java.util.ArrayList;
import java.util.List;


public class Game2Controller {

    private static final int NUM_BALLOONS = 50;
    private static final double MOVE_SPEED_INCREASEMENT = 0.1;
    private double move_speed = 1.0;
    private final List<Balloon> balloons = new ArrayList<>();
    private int deploy_speed = 2000; // milliseconds
    private static final int DEPLOY_SPEED_INCREASEMENT = 500; // milliseconds


    // todo initialize the construktor and add the number of balloons to the list(NUM_BALLOONS)
    // all the ballons initialized in the list should be invisible at the beginning


    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;

    @FXML
    private Button backToMenu;


    protected void initialize(){
        // create Person and Computer Objects
        // todo: go through the list of balloons and make one after another visible and move up the screen
        // iterate through list with thread that sleeps for deploy_speed milliseconds

        // if balloon is clicked-> balloon.remove and Player.increaseSc the speed of the balloons
        // if y = canvas max height -> ballon.remove()
    }

    @FXML
    protected void balloonClicked() {
        Balloon.remove();
        Player.increaseScore();
        increaseMove();
        increaseDeploy();
        Effects();
    }

    protected void increaseMove(){
        move_speed += MOVE_SPEED_INCREASEMENT;
    }

    protected void increaseDeploy(){
        deploy_speed -= DEPLOY_SPEED_INCREASEMENT;
    }

    protected void Effects(){
        //sound effekt and pop animation
    }

    @FXML
    protected Label playerScore;

    @FXML
    protected Label computerScore;

    @FXML
    protected TextField balloonsLeft;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
}