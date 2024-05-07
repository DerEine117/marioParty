package net.rknabe.marioparty.game6;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private int elapsedTime = 0;
    private Board board; // Add this line

    public GameTimer(Board board) { // Add this line
        this.board = board; // Add this line
    }

    public void start(Label label) {
        if (timer != null) {
            timer.cancel(); // Stop the existing timer
        }
        elapsedTime = 0; // Reset elapsed time
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime++;
                System.out.println("Elapsed time: " + elapsedTime);
                updateLabel(label);
                board.checkWin(); // Add this line
                board.checkTooManyMarks();
            }
        }, 1000, 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
    public void updateLabel(Label label) {
        Platform.runLater(() -> label.setText( elapsedTime + " Sekunden"));
    }
}