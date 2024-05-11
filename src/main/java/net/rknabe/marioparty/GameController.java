package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class GameController {
    @FXML
    protected Button backToMenu;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
}
