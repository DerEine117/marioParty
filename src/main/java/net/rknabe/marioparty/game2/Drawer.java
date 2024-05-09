package net.rknabe.marioparty.game2;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Drawer {
    IntializeBalloons initializer;

    public Drawer(IntializeBalloons initializer) {
        this.initializer = initializer;
    }

    protected void redrawCanvas(Canvas gameCanvas) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); // clear the entire canvas
        for (Balloon balloon : initializer.getBalloons()) {
            // redraw the balloon at the updated position
            gc.drawImage(balloon.getBalloonImage().getImage(), balloon.getX(), balloon.getY(), balloon.getBalloonImage().getFitWidth(), balloon.getBalloonImage().getFitHeight());
        }
    }

    protected static void remove(Canvas gameCanvas, Balloon balloon) {
        // when popped, remove the balloon from the canvas
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(balloon.getX(), balloon.getY(), balloon.getBalloonImage().getFitWidth(), balloon.getBalloonImage().getFitHeight());
    }

    protected void drawBackground(AnchorPane myAnchorPane) {
        String imageUrl = getClass().getResource("/net/rknabe/marioparty/assets/game2/backgroundGame2.png").toExternalForm();
        myAnchorPane.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: cover;");
    }

    protected void drawSpikes(ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/metalSpikes.png").toExternalForm());
        imageView1.setImage(image);
        imageView2.setImage(image);
        imageView3.setImage(image);
        imageView4.setImage(image);
    }

    protected void drawExplosion(AnchorPane myAnchorPane, Balloon balloon) {
        // GIF
        Image gifImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/platzen.gif").toExternalForm());
        ImageView gifImageView = new ImageView(gifImage);

        // set position
        gifImageView.setX(balloon.getX() + 720 - 557);
        gifImageView.setY(balloon.getY() + 30);

        myAnchorPane.getChildren().add(gifImageView);

        // remove
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(GIFevent -> myAnchorPane.getChildren().remove(gifImageView));
        pause.play();
    }
    protected void drawText(boolean won, Alert alert){
        if (won){
            Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/gewonnenText.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(175);
            imageView.setFitHeight(30);
            alert.setGraphic(imageView);
        }
        else {
            // Load the second image
            Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/verlorenText.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(175);
            imageView.setFitHeight(30);
            // Set the Pane as the graphic for the alert
            alert.setGraphic(imageView);
        }
    }

}
