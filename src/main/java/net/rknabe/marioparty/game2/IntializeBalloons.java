package net.rknabe.marioparty.game2;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IntializeBalloons {
    private final ConcurrentLinkedQueue<Balloon> balloons = new ConcurrentLinkedQueue<>();

    protected void createBalloons(int amount, Canvas gameCanvas) {
        // create all the Balloons and display them on the canvas, but:
        // make sure the next balloon has a y ->   previousY-60 > y or y > previousY +60
        // make them more spread out
        double previousX = gameCanvas.getWidth() / 2;

        for (int i = 0; i <= amount; i++) {
            Balloon balloon = new Balloon(gameCanvas);
            if (i == 0) {
                previousX = balloon.getX();
            } else {
                if (previousX - 60 > balloon.getX() || balloon.getX() > previousX + 60) {
                    balloons.add(balloon);
                    previousX = balloon.getX();
                } else {
                    i--;
                }
            }

        }
    }

    protected void removeBalloon(Balloon balloon) {
        balloons.remove(balloon);
    }

    protected void removeAllBalloons() {
        balloons.clear();
    }

    public List<Balloon> getBalloons() {
        return new ArrayList<>(balloons);
    }
}
