package net.rknabe.marioparty.MainGame;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;


public class Field {
    private String state;
    private int coins;
    private Rectangle rectangle;
    private boolean isPlayerOnField;
    private int playerPosition;  // Add this line
    private int fieldNumber;  // Add this line
    private Label fieldNumberLabel;  // Add this line
    private Pane pane;





    public Field(String state, int coins) {
        this.state = state;
        this.coins = coins;
        this.rectangle = new Rectangle(50, 50); // Größe des Rechtecks
        this.isPlayerOnField = false;
        updateColor();
        fieldNumberLabel = new Label();
        pane = new Pane();
        pane.getChildren().add(rectangle);
        pane.getChildren().add(fieldNumberLabel);
    }

    public String getState() {
        return state;
    }
    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
        fieldNumberLabel.setText(String.valueOf(fieldNumber));
        if (!state.equals("leer")) {
            fieldNumberLabel.setVisible(true);

        }}
            public void setState(String state) {
        this.state = state;
    }
    public Pane getPane() {  // Add this method
        return pane;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    private void updateColor() {
        Platform.runLater(() -> {
            if (isPlayerOnField) {
                rectangle.setFill(Color.BLUE);
            } else {
                switch (state) {
                    case "setscore +5":
                        rectangle.setFill(Color.GREEN);
                        break;
                    case "setscore -5":
                        rectangle.setFill(Color.RED);
                        break;
                    case "nichts":
                        rectangle.setFill(Color.GRAY);
                        break;
                    case "leer":
                        rectangle.setFill(Color.WHITE);
                        break;
                    default:
                        rectangle.setFill(Color.WHITE);
                        break;
                }
            }
        }
        );

    }
    public boolean isEventField() {
    return this.state.equals("setscore +5") || this.state.equals("setscore -5");
}

    public void setPlayerOnField() {
        this.isPlayerOnField = true;
        updateColor();
    }
    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    public int getFieldNumber() {
        return fieldNumber;
    }

}