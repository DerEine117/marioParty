package net.rknabe.marioparty.game6;

import javafx.scene.layout.Pane;

public class MinesWeeperApp extends Pane {
    private Board board;

    public MinesWeeperApp() {
        board = new Board();
        getChildren().add(board);
    }

    public void startGame() {
        System.out.println("MinesWeeperApp's startGame was called"); // Debug output

        System.out.println("startGame was called"); // Debug output

        board.startGame();
    }


}