package net.rknabe.marioparty.game5;

import java.util.*;

public class ComputerPlayer {
    private Board opponentBoard;
    private List<int[]> events;
    private List<int[]> sweepLine;
    private Random random;

    public ComputerPlayer(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
        this.events = new ArrayList<>();
        this.sweepLine = new ArrayList<>();
        this.random = new Random();

    }

    public int[] takeTurn() {
        // Initialisieren Sie eine Liste von "Ereignissen", die jeder mögliche Schuss ist.
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!opponentBoard.wasShot(x, y)) {
                    events.add(new int[]{x, y});
                }
            }
        }

        // Berechnen Sie für jedes Ereignis eine Priorität, basierend auf der Wahrscheinlichkeit, dass es ein Treffer ist.
        Map<int[], Double> eventPriorities = new HashMap<>();
        for (int[] event : events) {
            double priority = calculatePriority(event);
            eventPriorities.put(event, priority);
        }

        // Sortieren Sie diese Ereignisse nach ihrer Priorität.
        events.sort((a, b) -> eventPriorities.get(b).compareTo(eventPriorities.get(a)));

        // Initialisieren Sie eine "Besenlinie", die den ersten Schuss in der sortierten Liste ist.
        sweepLine.add(events.get(0));

        // Für jedes Ereignis in der sortierten Liste:
        for (int[] event : events) {
            opponentBoard.shoot(event[0], event[1]);
            if (opponentBoard.isHit(event[0], event[1])) {
                // Wenn das Ereignis ein Treffer ist, fügen Sie es zur Besenlinie hinzu.
                sweepLine.add(event);
            } else {
                // Wenn das Ereignis ein Fehlschuss ist, entfernen Sie es aus der Besenlinie.
                sweepLine.remove(event);
            }
        }

        // Wählen Sie das nächste Ereignis auf der Besenlinie als nächsten Schuss.
        int[] nextShot;
        if (random.nextDouble() < 0.1) {
            // 50% Chance, das Ereignis mit der höchsten Priorität zu wählen.
            nextShot = sweepLine.get(0);
        } else {
            // 50% Chance, ein zufälliges Ereignis zu wählen.
            nextShot = sweepLine.get(random.nextInt(sweepLine.size()));
        }
        sweepLine.remove(nextShot);

        return nextShot;
    }

    private double calculatePriority(int[] event) {
        // Count the number of neighboring cells that have already been hit.
        int neighboringHits = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = event[0] + dx;
                int y = event[1] + dy;
                if (x >= 0 && x < 10 && y >= 0 && y < 10 && opponentBoard.isHit(x, y)) {
                    neighboringHits++;
                }
            }
        }
        return neighboringHits;
    }
}