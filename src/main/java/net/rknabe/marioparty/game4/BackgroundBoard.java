package net.rknabe.marioparty.game4;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
public class BackgroundBoard {
    private AnchorPane anchorPane;
    private Image backgroundImage;
    private ImageView backgroundImageView;

    public BackgroundBoard(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        this.backgroundImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game4/Background.jpg").toExternalForm());
        this.backgroundImageView = new ImageView(backgroundImage);
    }

    public void drawBackground() {
        backgroundImageView.setFitWidth(anchorPane.getWidth());
        backgroundImageView.setFitHeight(anchorPane.getHeight());
        anchorPane.getChildren().add(backgroundImageView);
        // Verschiebt das Hintergrundbild in den Hintergund -> sonst wäre nur das Board sichtbar
        backgroundImageView.toBack();
    }
}
