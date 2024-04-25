package net.rknabe.marioparty.game2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.font.ImageGraphicAttribute;

public class Balloon {
    private double x;
    private double y;
    private static final double BALLOON_RADIUS = 20.0;
    private ImageView balloonImage;
    private Canvas gameCanvas;



    // ballon is a picture of a balloon

    public Balloon() {
        // this.x = random number between 0 and canvas width
        this.x = Math.random() * gameCanvas.getWidth();
        // this.y = bottom of the canvas
        this.y = gameCanvas.getHeight();

        Image image = new Image("net/rknabe/marioparty/assets/Balloon.png");
        this.balloonImage = new ImageView(image);
        this.balloonImage.setX(this.x);
        this.balloonImage.setY(this.y);

    }
    public void move(double y){
        // todo: move the balloon up the screen
        // aufgerufen in Game2Controller
        this.y++; //?
        this.balloonImage.setY(this.y);

    }
    public void display(double x, double y){
        // displays the balloon at the given position
    }
    public static void remove(){
        // when end of screen( y = canvas max height ) is reached
    }
}
