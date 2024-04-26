package net.rknabe.marioparty.game2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Balloon {
    private double x;
    private double y;
    private static final double BALLOON_RADIUS = 20.0;
    private ImageView balloonImage;
    private Canvas gameCanvas;





    // ballon is a picture of a balloon

    public Balloon(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        // this.x = random number between ((fxml width - canvas width) and fxml width)
        this.x = (Math.random() * (gameCanvas.getWidth()-50));
        System.out.println("x: " + this.x);
        System.out.println("canvas width: " + gameCanvas.getWidth());
        // this.y = bottom of the canvas
        this.y = gameCanvas.getHeight() -10;
        System.out.println("y: " + this.y);
        System.out.println("canvas height: " + gameCanvas.getHeight());

        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/Balloon.png").toExternalForm());
        this.balloonImage = new ImageView(image);
        this.balloonImage.setFitWidth(60); // set the width of the image to 20
        this.balloonImage.setFitHeight(60); // set the height of the image to 20
        this.balloonImage.setX(this.x);
        this.balloonImage.setY(this.y);

        this.balloonImage.setOnMouseClicked(event -> {
            // Hier können Sie den Code hinzufügen, der ausgeführt werden soll, wenn auf den Ballon geklickt wird
            System.out.println("Balloon clicked!");
            // Zum Beispiel, Sie könnten die remove-Methode hier aufrufen
            // remove(this);
        });
    }
    //todo: erstelle Event was abbildet das ein Ballon oben am Canvas angekommen ist

    public void move(Balloon balloon, double move_speed){
        // todo: move the balloon up the screen
        // aufgerufen in Game2Controller
        remove(balloon);
        this.y-=move_speed;
        this.balloonImage.setY(this.y);
        display(balloon);

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
