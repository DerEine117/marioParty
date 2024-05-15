package net.rknabe.marioparty.MainGame;

import javafx.scene.image.Image;

public class Player {
    private final String name;
    private int position;
    private final boolean computer;
    private final Image image;


    public Player(String name, boolean computer,String imagePath) {
        this.name = name;
        this.position = 0;
        this.computer = computer;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());

    }
    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public boolean isComputer() {
        return computer;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void move(int diceNumber) {
        this.position += diceNumber;

    }


}
