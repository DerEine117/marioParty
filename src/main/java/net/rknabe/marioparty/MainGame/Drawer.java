package net.rknabe.marioparty.MainGame;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;

public class Drawer {
    // class for drawing the player_picture on the designed field
    // drawing the dices
    // drawing the field
    // drawing the ....


    public Drawer() {
    }

    protected void drawBackground(AnchorPane myAnchorPane) {
        String imageUrl = getClass().getResource("/net/rknabe/marioparty/assets/MainGame/background_gridPane.JPG").toExternalForm();
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
    public void drawGoalImage(GridPane gridPane, int position, Board board) {
        Field field = board.getFieldByNumber(position);
        if (field != null) {
            URL resourceUrl = getClass().getResource("/net/rknabe/marioparty/assets/MainGame/goal.png");
            Image image = new Image(resourceUrl.toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(43); // Setzen Sie die Breite auf die Breite des Rechtecks
            imageView.setFitHeight(43); // Setzen Sie die Höhe auf die Höhe des Rechtecks
            gridPane.add(imageView, field.getY(), field.getX());
        }
    }


    protected void drawPlayer(GridPane gridPane, int position, int playerIndex, Board board) {
        for (Field field : board.getFields().values()) {
            if (field.getFieldNumber() == position) {
                changeRectangleColor(field.getX(), field.getY(), playerIndex == 0 ? Color.LIGHTBLUE : Color.DARKRED, board);
                break;
            }
        }
    }
    public void drawPlayerImage(GridPane gridPane, int position, Player player, Board board) {
        Field field = board.getFieldByNumber(position);
        if (field != null) {
            ImageView imageView = new ImageView(player.getImage());
            imageView.setFitWidth(43); // Setzen Sie die Breite auf die Breite des Rechtecks
            imageView.setFitHeight(43); // Setzen Sie die Höhe auf die Höhe des Rechtecks
            gridPane.add(imageView, field.getY(), field.getX());

            String key = field.getX() + "," + field.getY();
            board.setPlayerImageViewByCoordinates(key, imageView);  // Add this line
            imageView.toFront();
        }
        System.out.println("Ich wurde ausgewführt");
    }
    public void removePlayerImage(GridPane gridPane, int position, Board board) {
        Field field = board.getFieldByNumber(position);
        if (field != null) {
            String key = field.getX() + "," + field.getY();
            ImageView imageView = board.getPlayerImageViewByCoordinates(key);  // Change this line
            if (imageView != null) {
                gridPane.getChildren().remove(imageView);
                board.setPlayerImageViewByCoordinates(key, null);  // Add this line
            }
        }
    }
    public void changeRectangleColor(int x, int y, Color color,Board board) {
        Rectangle rectangle = board.getRectangleByCoordinates(x, y);
        if (rectangle != null) {
            rectangle.setFill(color);
            rectangle.toFront();
        }

        System.out.println(board.getFieldByNumber(25).getX() + " " + board.getFieldByNumber(25).getY() + " " + board.getFieldByNumber(25).getFieldNumber() + " " + board.getFieldByNumber(25).getState() + " " + board.getFieldByNumber(25).hasPlayer());
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
