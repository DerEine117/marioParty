package net.rknabe.marioparty.game2;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IntializeBalloons {
    private ConcurrentLinkedQueue<Balloon> balloons = new ConcurrentLinkedQueue<>();

    protected void createBalloons(int amount, Canvas gameCanvas) {
        // create all the Balloons and display them on the canvas, but:
        // make sure the next balloon has a y ->   previousY-60 > y or y > previousY +60
        // make them more spread out
        double previousX = gameCanvas.getWidth() / 2;

        for (int i = 0; i <= amount; i++) {
            Balloon balloon = new Balloon(gameCanvas);

            balloons.add(balloon);
            /*if (i == 0) {
                previousX = balloon.getX();
            } else {
                if (previousX - 60 > balloon.getX() || balloon.getX() > previousX + 60) {
                    balloons.add(balloon);
                    previousX = balloon.getX();
                } else {
                    i--;
                }
            }
            */
        }
    }

    protected void removeBalloon(Balloon balloon) {
        balloons.remove(balloon);
    }

    protected void removeAllBalloons() {
        balloons.clear();
    }

    protected List<Balloon> getBalloons() {
        return new ArrayList<>(balloons);
    }

    protected void sortBalloonsByDeploySpeed() {
        List<Balloon> balloonList = new ArrayList<>(balloons);
        int n = balloonList.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (balloonList.get(j).getDeploySpeed() < balloonList.get(j+1).getDeploySpeed()) {
                    // Swap balloons[j+1] and balloons[i]
                    Balloon temp = balloonList.get(j);
                    balloonList.set(j, balloonList.get(j+1));
                    balloonList.set(j+1, temp);
                }
            }
        }
        balloons = new ConcurrentLinkedQueue<>(balloonList);
    }
}
