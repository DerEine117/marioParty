package net.rknabe.marioparty.game4;

import java.util.Random;
public class Computerplayer {
    private int reachedLength;

    public Computerplayer() {
        Random randomLength = new Random();
        this.reachedLength = randomLength.nextInt(7) + 12;
        // Mindestens 10 -> wäre sonst zu einfach
    }

    public int getReachedLength() {
        return this.reachedLength;
    }

    // für den Resetfall, soll nicht nochmal die alte Länge als Maßstab dienen
    public int newLength() {
        Random roleNewLength = new Random();
        int newLength = roleNewLength.nextInt(7) + 12;
        this.reachedLength = newLength;
        return newLength;
    }
}
