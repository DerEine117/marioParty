package net.rknabe.marioparty.MainGame;

import java.util.Random;

public class Dice {
    private Random random;

    public Dice() {
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(6) + 1;
    }

    public void startAnimation() {
        // Start the animation
    }

    public void stopAnimation() {
        // Stop the animation
    }
}
