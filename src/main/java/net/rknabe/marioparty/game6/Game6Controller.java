package net.rknabe.marioparty.game6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rknabe.marioparty.StageChanger;

public class Game6Controller {
    @FXML
    protected Button backToMenu;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
}
