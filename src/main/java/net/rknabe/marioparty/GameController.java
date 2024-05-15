package net.rknabe.marioparty;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import net.rknabe.marioparty.MainGame.Player;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


public class GameController {
    @FXML
    protected Button spielinfo;
  
    private static GameController instance;

    protected int player1Coins = 100;
    protected int player2Coins = 100;
    

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

    @FXML
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Hier ist der Text der Spielinfo.");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }
}
