package net.rknabe.marioparty.game2;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InitializeBalloons {
    private ConcurrentLinkedQueue<Balloon> balloons = new ConcurrentLinkedQueue<>();

    protected void createBalloons(int amount, Canvas gameCanvas) {
        // creates all the Balloons

        for (int i = 0; i <= amount; i++) {
            Balloon balloon = new Balloon(gameCanvas);
            balloons.add(balloon);
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
        // sorts the balloons by deploy speed, so it gets faster over time
        List<Balloon> balloonList = new ArrayList<>(balloons);
        int n = balloonList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (balloonList.get(j).getDeploySpeed() < balloonList.get(j + 1).getDeploySpeed()) {
                    // Swap balloons[j+1] and balloons[i]
                    Balloon temp = balloonList.get(j);
                    balloonList.set(j, balloonList.get(j + 1));
                    balloonList.set(j + 1, temp);
                }
            }
        }
        balloons = new ConcurrentLinkedQueue<>(balloonList);
    }
}
