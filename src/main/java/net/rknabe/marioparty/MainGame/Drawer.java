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

    protected void drawPicture (ImageView imageview) {
        Image image = new Image(getClass().getResource("/net/rknabe/marioparty/assets/MainGame/players.png").toExternalForm());
        imageview.setImage(image);
    }

    protected void drawBoard(GridPane gridpane){
        // draw the board
    }
    protected void drawPlayer(GridPane gridPane, int position, int playerIndex,Board board) {
        for (Field field : board.getFields().values()) {
            if (field.getFieldNumber() == position) {
                Circle circle = new Circle(20, playerIndex == 0 ? Color.PINK : Color.BLACK);
                gridPane.getChildren().removeAll(gridPane.lookupAll(".player" + playerIndex));
                System.out.println("DrawPlayer Position: "+ position);
                circle.setId("player" + playerIndex);
                gridPane.add(circle, field.getY(), field.getX()); // Vertauschen Sie die x- und y-Koordinaten
                circle.toFront();

                System.out.println("X: " + field.getX() + ", Y: " + field.getY());
                markRectangle(field.getX(), field.getY(), board);

                changeRectangleColor(field.getX(), field.getY(), Color.YELLOW, board);

                break;
            }
        }
    }
    public void markRectangle(int x, int y,Board board) {
        Rectangle rectangle = board.getRectangleByCoordinates(x, y);
        if (rectangle != null) {
            System.out.println("Rectangle: " + y + "," + x);
        }
    }
    public void changeRectangleColor(int x, int y, Color color,Board board) {
        Rectangle rectangle = board.getRectangleByCoordinates(x, y);
        if (rectangle != null) {
            rectangle.setFill(color);
        }
    }
    public void resetRectangleColor(int x, int y, Board board,  int fieldNumber) {
        Rectangle rectangle = board.getRectangleByCoordinates(x, y);
        Field field = board.getFieldByNumber(fieldNumber);
        if (rectangle != null && field != null) {
            switch (field.getState()) {
                case 0:
                    rectangle.setFill(Color.GRAY);
                    break;
                case 1:
                    rectangle.setFill(Color.GREEN);
                    break;
                case 2:
                    rectangle.setFill(Color.RED);
                    break;
            }
        }
    }
}
