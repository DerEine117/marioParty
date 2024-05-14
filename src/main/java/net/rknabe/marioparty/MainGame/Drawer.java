package net.rknabe.marioparty.MainGame;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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

    protected void drawPicture(ImageView imageView) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/MainGame/players.png").toExternalForm());
        imageView.setImage(image);

    }

    protected void drawPlayer(GridPane gridPane, int position, int playerIndex, Board board) {
        for (Field field : board.getFields().values()) {
            if (field.getFieldNumber() == position) {
                changeRectangleColor(field.getX(), field.getY(), playerIndex == 0 ? Color.YELLOW : Color.BLACK, board);
                break;
            }
        }
    }
    public void changeRectangleColor(int x, int y, Color color,Board board) {
        Rectangle rectangle = board.getRectangleByCoordinates(x, y);
        if (rectangle != null) {
            rectangle.setFill(color);
            rectangle.toFront();
        }
    }
    public void resetRectangleColor(GridPane gridPane, int position, Board board) {
        Field field = board.getFieldByNumber(position);
        if (field != null) {
            Rectangle rectangle = board.getRectangleByCoordinates(field.getX(), field.getY());
            if (rectangle != null) {
                rectangle.setFill(Color.GRAY);
            }
        }
    }

}
