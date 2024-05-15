package net.rknabe.marioparty.game6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import net.rknabe.marioparty.GameController;

public class Game6Controller extends GameController {
    @FXML
    private Pane game6Pane; // Dieses Pane wird in der FXML-Datei definiert
    @FXML
    Button button;



    public void initialize() {
        button.setOnAction(event -> player1.addCoins(1000));
    }
    public void setCoins() {
        player1.addCoins(1000);
        getPlayer2().addCoins(100000);
    }


}