package net.rknabe.marioparty.MainGame;public class Player {
    private final String name;
    private int position;
    private int coins;
    private final boolean computer;

    public Player(String name, boolean computer) {
        this.name = name;
        this.position = 0;
        this.coins = 0;
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isComputer() {
        return computer;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
    }

}
