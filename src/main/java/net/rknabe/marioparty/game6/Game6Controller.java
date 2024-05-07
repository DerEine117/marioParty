package net.rknabe.marioparty.game6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Game6Controller {
    @FXML
    private Pane game6Pane; // Dieses Pane wird in der FXML-Datei definiert

    private MinesWeeperApp game;

    @FXML
    private Button startGame6;

    public void initialize() {
        System.out.println("initialize was called"); // Debug output

        game = new MinesWeeperApp();
        game6Pane.getChildren().add(game);
        game.startGame();
    }


    @FXML
    protected void backToMenuClick() {
        // Implementieren Sie die Logik, um zur Menüszene zurückzukehren
    }
}