package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rknabe.marioparty.MainGame.Player;


public class GameController {
    private static GameController instance;

    protected int player1Coins = 100;
    protected int player2Coins = 100;
    @FXML
    protected Button SpielanleitungButton;

    protected static Player player1;
    protected static Player player2;

    public static synchronized GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    // Statische Methode zum Erstellen der Spielerinstanzen
    public static void initializePlayers() {
        if (player1 == null) {
            player1 = new Player("Mario", false, "/net/rknabe/marioparty/assets/MainGame/player1.png");
        }
        if (player2 == null) {
            player2 = new Player("Bowser", true, "/net/rknabe/marioparty/assets/MainGame/browser.png");
        }
    }

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
    public void addCoinsToPlayer1(int coins){
        player1Coins += coins;
    }
    public void addCoinsToPlayer2(int coins){
        player2Coins += coins;
    }
    public int getPlayer1Coins(){
        return player1Coins;
    }
    public int getPlayer2Coins(){
        return  player2Coins;
    }
}
