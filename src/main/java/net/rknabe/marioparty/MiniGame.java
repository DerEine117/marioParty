package net.rknabe.marioparty;

import javafx.fxml.Initializable;

public interface MiniGame extends Initializable {
    void startGame();
    void endGame();
    void resetGame();
    @Override
    void initialize(java.net.URL location, java.util.ResourceBundle resources);
}
