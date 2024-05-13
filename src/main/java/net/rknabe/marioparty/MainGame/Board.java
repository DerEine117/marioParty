package net.rknabe.marioparty.MainGame;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {


    void initalizeBoard(GridPane gridPane){
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for(int i = 0; i<8;i++){
            for(int j = 0; j<8;j++){
                Rectangle rectangle = new Rectangle(50,50);
                rectangle.setFill(Color.BLUE);
                gridPane.add(rectangle,i,j);
            }

    } }

    private static void setFelder() {

    }
    private static void genereateRandomState() {
    }
}
