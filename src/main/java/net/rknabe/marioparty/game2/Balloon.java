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
    private final Canvas gameCanvas;
    private boolean popped;
    private boolean reachedTop;

    public int getMoveSpeed() {
        return this.move_speed;
    }
    public int getDeploySpeed() {
        return this.deploy_speed;
    }

    public boolean isPopped() {
        return this.popped;
    }

    public void setPopped(boolean popped) {
        this.popped = popped;
        // todo
        // shouldnt be neccesarry
    }

    public boolean hasReachedTop() {
        return this.reachedTop;
    }


    // ballon is a picture of a balloon

    public Balloon(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        // this.x = random number between ((fxml width - canvas width) and fxml width)
        this.x = (Math.random() * (gameCanvas.getWidth()-50));
        // this.y = bottom of the canvas
        this.y = gameCanvas.getHeight() -10;
        this.move_speed = (int)(Math.random() * (20 - 3)) + 3;
        this.deploy_speed = (int)(Math.random() * (1000 - 400)) + 400;
        this.popped = false;
        this.reachedTop = false;

        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/Balloon.png").toExternalForm());
        this.balloonImage = new ImageView(image);
        this.balloonImage.setFitWidth(60); // set the width of the image to 20
        this.balloonImage.setFitHeight(60); // set the height of the image to 20
        this.balloonImage.setX(this.x);
        this.balloonImage.setY(this.y);

    }

    public void move(){
        // move the balloon up the screen
        this.y--;
        this.balloonImage.setY(this.y);

        // Check if the balloon has reached the top of the canvas
        if (this.y <= 0) {
            this.reachedTop = true;
        }

    }

    public ImageView getBalloonImage() {
        return balloonImage;
    }

    public void display(Balloon balloon){
        // displays the balloon at the given position
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.drawImage(balloon.getBalloonImage().getImage(), balloon.x, balloon.y, balloon.getBalloonImage().getFitWidth(), balloon.getBalloonImage().getFitHeight());
    }
    public static void remove(Balloon balloon){
        // when popped, remove the balloon from the canvas
        GraphicsContext gc = balloon.gameCanvas.getGraphicsContext2D();
        gc.clearRect(balloon.x, balloon.y, balloon.getBalloonImage().getFitWidth(), balloon.getBalloonImage().getFitHeight());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
