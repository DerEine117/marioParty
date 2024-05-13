package net.rknabe.marioparty.MainGame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.rknabe.marioparty.GameController;

import java.util.Random;

public class MainGameController extends GameController {
    private Player[] players;
    private int currentRound;
    private Player currentPlayer;
    private Board board;
    private Random random;


    @FXML
    private Pane boardPane;
    @FXML
    private Button rollDiceButton;
    @FXML
    private Label player1coins;
    @FXML
    private Label player2coins;

    @FXML
    private Label player1_dice;
    @FXML
    private Label player2_dice;
    @FXML
    private Label player1_position;
    @FXML
    private Label player2_position;
    @FXML
    public void initialize() {
        Board board = new Board();
        boardPane.getChildren().add(board);
        rollDiceButton=new Button("Roll Dice");
        rollDiceButton.setOnAction(e -> playRound());
    }

    public MainGameController() {
        // Initialize the players and the board
        players = new Player[] {
                new Player("Player 1"),
                new Player("Player 2")
        };
        board = new Board();
        random = new Random();
    }

    public void playRound() {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            int diceRoll = rollDice();

            // roll a dice
            System.out.println(player.getName() + " rolled a " + diceRoll);

            // Move player
            player.move(diceRoll, board);


            if (i == 0) {
                player1_dice.setText("Player 1 dice: " + diceRoll);
                player1_position.setText("Player 1 position: " + player.getPosition());
            } else if (i == 1) {
                player2_dice.setText("Player 2 dice: " + diceRoll);
                player2_position.setText("Player 2 position: " + player.getPosition());
            }}
        player1coins.setText("Player 1 coins: " + players[0].getCoins());
        player2coins.setText("Player 2 coins: " + players[1].getCoins());

    }    private int rollDice() {
        return random.nextInt(6) + 1;  // Returns a random number between 1 and 6
    }
    public void playGame(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("Round " + (i + 1));
            playRound();
        }
    }
    public Label getPlayer1coins() {
        return player1coins;
    }

    public void setPlayer1coins(Label player1coins) {
        this.player1coins = player1coins;
    }

    public Label getPlayer2coins() {
        return player2coins;
    }

    public void setPlayer2coins(Label player2coins) {
        this.player2coins = player2coins;
    }

    public Button getRollDiceButton() {
        return rollDiceButton;
    }

    public void setRollDiceButton(Button rollDiceButton) {
        this.rollDiceButton = rollDiceButton;
    }

    public Label getPlayer1_dice() {
        return player1_dice;
    }

    public void setPlayer1_dice(Label player1_dice) {
        this.player1_dice = player1_dice;
    }
}

