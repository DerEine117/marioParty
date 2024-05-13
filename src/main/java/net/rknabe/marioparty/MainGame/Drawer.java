package net.rknabe.marioparty.MainGame;

import javafx.fxml.FXML;
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

    public void drawDice(Player player, ImageView whichDice) {
        // draw the dice
    }

    protected void drawPicture (ImageView imageview) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/MainGame/players.png").toExternalForm());
        imageview.setImage(image);
    }


    protected void drawSpikes(ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game2/metalSpikes.png").toExternalForm());
        imageView1.setImage(image);
        imageView2.setImage(image);
        imageView3.setImage(image);
        imageView4.setImage(image);
    }
    protected void drawBoard(GridPane gridpane){


        // draw the board
    }

}
