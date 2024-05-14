package net.rknabe.marioparty.game4;

import java.util.Random;
public class Computerplayer {
    private final int reachedLength;

    public Computerplayer() {
        Random randomLength = new Random();
        this.reachedLength = randomLength.nextInt(1) + 4;
        // Mindestens 10 -> wäre sonst zu einfach
    }

    public int getReachedLength() {
        return this.reachedLength;
    }
}
