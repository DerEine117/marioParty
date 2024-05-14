package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController {
    @FXML
    protected Button backToMenu;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }

    // todo: Spielanleitung anzeigen

}
