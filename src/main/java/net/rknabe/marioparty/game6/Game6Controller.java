package net.rknabe.marioparty.game6;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class Game6Controller {
    @FXML
    private Pane game6Pane; // Dieses Pane wird in der FXML-Datei definiert

    private MinesWeeperApp game;

    public void initialize() {
        game = new MinesWeeperApp();
        game6Pane.getChildren().add(game);
    }

    @FXML
    protected void startGame6Click() {
        game.startGame();
    }

    @FXML
    protected void backToMenuClick() {
        // Implementieren Sie die Logik, um zur Menüszene zurückzukehren
    }
}