package net.rknabe.marioparty.game2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Balloon {
    private final double x;
    private double y;
    private static final double BALLOON_RADIUS = 20.0;

     // ballon is a picture of a balloon

    public Balloon() {
        // this.x = random number between 0 and canvas width
        // this.y = bottom of the canvas
        // choose picture by random

    }
    public void move(double x, double y){
        // todo: move the balloon up the screen
        // aufgerufen in Game2Controller
        this.y++; //?
    }
    public void display(double x, double y){
        // displays the balloon at the given position
    }
    public static void remove(){
        // when end of screen( y = canvas max height ) is reached
    }
}
