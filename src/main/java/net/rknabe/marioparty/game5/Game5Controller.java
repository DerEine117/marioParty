package net.rknabe.marioparty.game5;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import net.rknabe.marioparty.GameController;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class Game5Controller extends GameController implements Initializable{
    @FXML
    private GridPane gridPlayer1, gridPlayer2;
    private Board boardPlayer1, boardPlayer2;
    private ComputerPlayer computerPlayer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardPlayer1 = new Board();
        boardPlayer2 = new Board();
        computerPlayer = new ComputerPlayer(boardPlayer1);

        // Place ships randomly on the boards
        for (int size = 1; size <= 5; size++) {
            boardPlayer1.placeShip(new Ship(size, boardPlayer1));
            boardPlayer2.placeShip(new Ship(size, boardPlayer2));
        }

        initializeGrid(gridPlayer1, boardPlayer1, true);
        initializeGrid(gridPlayer2, boardPlayer2, false);
    }

    private void initializeGrid(GridPane grid, Board board, boolean showShips) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button button = new Button();
                button.setPrefWidth(50);  // Set the preferred width to 50
                button.setPrefHeight(50); // Set the preferred height to 50
                if (showShips && board.isOccupied(i, j)) {
                    button.setStyle("-fx-background-color: black;");
                }
                int x = i;
                int y = j;
                button.setOnAction(event -> {
                    if (board.isHit(x, y)) {
                        button.setStyle("-fx-background-color: red;");
                    } else {
                        button.setStyle("-fx-background-color: darkgray;");
                    }
                    int[] computerShot = computerPlayer.takeTurn();
                    updateGrid(gridPlayer1, boardPlayer1, computerShot);
                });
                grid.add(button, i, j);
            }
        }
    }
    private void updateGrid(GridPane grid, Board board, int[] shot) {
        for (Node node : grid.getChildren()) {
            Button button = (Button) node;
            int x = GridPane.getColumnIndex(button);
            int y = GridPane.getRowIndex(button);
            if (x == shot[0] && y == shot[1] && board.isHit(x, y)) {
                button.setStyle("-fx-background-color: yellow;");
            } else if (board.wasShot(x, y)) {
                button.setStyle("-fx-background-color: darkgray;");
            }
        }
    }
}
