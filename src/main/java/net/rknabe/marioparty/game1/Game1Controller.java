package net.rknabe.marioparty.game1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rknabe.marioparty.StageChanger;

public class Game1Controller {
    @FXML
    protected Button backToMenu;

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
        System.out.println("Roberts branch testd");
    }
}
