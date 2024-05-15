package net.rknabe.marioparty.game2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Balloon {

    private final double x;
    private double y;
    private final int move_speed;
    private final int deploy_speed;
    private final ImageView balloonImage;
    private boolean popped;
    private boolean reachedTop;

    protected int getMoveSpeed() {
        return this.move_speed;
    }

    protected int getDeploySpeed() {
        return this.deploy_speed;
    }

    protected boolean isPopped() {
        return this.popped;
    }

    protected void setPopped(boolean popped) {
        this.popped = popped;
    }

    protected boolean hasReachedTop() {
        return this.reachedTop;
    }

    protected ImageView getBalloonImage() {
        return balloonImage;
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }


    // ballon is a picture of a balloon
    protected Balloon(Canvas gameCanvas) {
        // this.x = random number between ((fxml width - canvas width) and fxml width)
        this.x = (Math.random() * (gameCanvas.getWidth() - 50));
        // this.y = Beneath of the canvas
        this.y = gameCanvas.getHeight()+10;
        // lower is faster (Thread.sleep(move_speed)-> ms per pixel)
        this.move_speed = (int) (Math.random() * (18 - 3)) + 3;
        this.deploy_speed = (int) (Math.random() * (1000 - 400)) + 400;
        this.popped = false;
        this.reachedTop = false;

        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/balloon.png").toExternalForm());
        this.balloonImage = new ImageView(image);
        this.balloonImage.setFitWidth(60);
        this.balloonImage.setFitHeight(73);
        this.balloonImage.setX(this.x);
        this.balloonImage.setY(this.y);

    }

    protected void move() {
        // move the balloon up the screen
        this.y--;
        this.balloonImage.setY(this.y);

        // Check if the balloon has reached the top of the canvas
        if (this.y == 0) {
            this.reachedTop = true;
        }
    }
}
