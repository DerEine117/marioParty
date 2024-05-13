package net.rknabe.marioparty.MainGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Drawer {
    // class for drawing the player_picture on the designed field
    // drawing the dices
    // drawing the field
    // drawing the ....

    public Drawer() {
    }

    protected void drawBackground(AnchorPane myAnchorPane) {
        String imageUrl = getClass().getResource("//path").toExternalForm();
        myAnchorPane.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: cover;");
    }

    protected void drawDicePicture(int number, ImageView imageView) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/MainGame/dice/" + number + ".png").toExternalForm());
        imageView.setImage(image);
        imageView.setFitWidth(85);
        imageView.setFitHeight(85);
        double centerX = (130 - imageView.getFitWidth()) / 2;
        double centerY = (130 - imageView.getFitHeight()) / 2;
        imageView.setX(centerX);
        imageView.setY(centerY);
    }
    protected void drawDiceAnimation(ImageView imageView) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/MainGame/dice/diceRoll.gif").toExternalForm());
        imageView.setImage(image);
        imageView.setFitWidth(130);
        imageView.setFitHeight(130);
        double centerX = (130 - imageView.getFitWidth()) / 2;
        double centerY = (130 - imageView.getFitHeight()) / 2;
        imageView.setX(centerX);
        imageView.setY(centerY);
    }

    protected void drawPicture (ImageView imageview) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/MainGame/players.png").toExternalForm());
        imageview.setImage(image);
    }

    protected void drawBoard(GridPane gridpane){
        // draw the board
    }
}
