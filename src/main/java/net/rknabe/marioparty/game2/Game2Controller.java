package net.rknabe.marioparty.game2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rknabe.marioparty.StageChanger;

public class Game2Controller {
    @FXML
    protected Button backToMenu;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
}
