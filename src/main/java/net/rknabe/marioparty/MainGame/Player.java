package net.rknabe.marioparty.MainGame;

public class Player {
    private String name;
    private int position;
    private int coins;
    private int score;
    private int x;  // Add this
    private int y;
    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.coins = 0;
        this.score = 0;

    }
    public void move(int diceRoll, Board board) {
        int newPosition = this.position + diceRoll;

        // Check if the new position exceeds the last numbered field
        if (newPosition > board.getLastFieldNumber()) {
            newPosition = board.getLastFieldNumber();
        }

        // Update the player's position
        this.position = newPosition;

        // Check the event on the field where the player landed
        Field field = board.getFieldByNumber(this.position);
        System.out.println("By number" + board.getFieldByNumber(field.getFieldNumber()));
        if (field != null) {
            System.out.println("Feldnummer: " + field.getFieldNumber() + "By nunmber: " + board.getFieldByNumber(field.getFieldNumber()));
            System.out.println("By number: " + field);
            System.out.println("State: "+field.getState());
            System.out.println("normal:" +field.getState());
            String state = board.getFieldByNumber(position).getState();
            System.out.println("neu: " + state);
            if (state.equals("setscore +5")) {
                this.setScore(this.getScore() + 5);
                System.out.println(this.getName() + " gained 5 points");
            } else if (state.equals("setscore -5")) {
                this.setScore(this.getScore() - 5);
                System.out.println(this.getName() + " lost 5 points");
            } else {
                System.out.println("Nothing happens on this field");
            }
        }
    }



public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}