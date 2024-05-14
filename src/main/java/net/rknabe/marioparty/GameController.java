package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class GameController {
    @FXML
    protected Button SpielanleitungButton;
    @FXML
    private void SpielanleitungClick() {
        // todo: Spielanleitung anzeigen
    }

    // Methode existiert weiterhin
    // beim Spielende soll automatisch backToMenuClick() aufgerufen werden!!!!
    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
}
