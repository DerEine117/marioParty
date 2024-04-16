package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Game1Controller {
    @FXML
    protected Button backToMenu;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
}
