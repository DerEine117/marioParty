package net.rknabe.marioparty.game6;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTimer extends Thread {
    private long startTime;
    private Board board;
    private Label label;

    public GameTimer(Board board, Label label) {
        this.board = board;
        this.label = label;
    }
    // start the timer
    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        while (!Game6Controller.getInstance().isGameOver()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            updateLabel(elapsedTime);
            board.checkWin();
            board.checkTooManyMarks();
        }
    }

    public void updateLabel(long elapsedTime) {
        Platform.runLater(() -> label.setText(elapsedTime + " Sekunden"));
    }
}