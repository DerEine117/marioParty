package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML
    private Button playMiniGame1;

    @FXML
    private Button playMiniGame2;

    @FXML
    private Button playMiniGame3;

    @FXML
    private Button playMiniGame4;

    @FXML
    private Button playMiniGame5;

    @FXML
    private Button playMiniGame6;


    @FXML
    protected void playMiniGame1Click() {
        System.out.println("switching to Game 1");
        StageChanger.setScene(1);
    }

    @FXML
    protected void playMiniGame2Click() {
        System.out.println("switching to Game 2");
        StageChanger.setScene(2);
    }

    @FXML
    protected void playMiniGame3Click() {
        System.out.println("switching to Game 3");
        StageChanger.setScene(3);
    }

    @FXML
    protected void playMiniGame4Click() {
        System.out.println("switching to Game 4");
        StageChanger.setScene(4);
    }

    @FXML
    protected void playMiniGame5Click() {
        System.out.println("switching to Game 5");
        StageChanger.setScene(5);
    }

    @FXML
    protected void playMiniGame6Click() {
        System.out.println("switching to Game 6");
        StageChanger.setScene(6);
    }
}
