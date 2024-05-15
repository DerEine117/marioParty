package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rknabe.marioparty.MainGame.Player;


public abstract class GameController {
    @FXML
    protected Button SpielanleitungButton;
    protected Player player1 = new Player("Mario", false, "/net/rknabe/marioparty/assets/MainGame/player1.png");
    protected Player player2 = new Player("Bowser", true, "/net/rknabe/marioparty/assets/MainGame/browser.png");

    @FXML
    protected void backToMenuClick() {
        StageChanger.setScene(0);
    }
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
