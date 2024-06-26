package net.rknabe.marioparty.game4;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Fruit {
    private int x;
    private int y;
    private final ImageView fruitImageView;
    Random random = new Random();

    public Fruit() {
        this.x = 400;
        this.y = 300;

        // Lediglich ein Image, deswegen initialisiere ich es direkt mit im Konstruktor
        Image fruitImage = new Image(getClass().getResource("/net/rknabe/marioparty/assets/game4/Fruit.png").toExternalForm());
        fruitImageView = new ImageView(fruitImage);
        fruitImageView.setFitWidth(50);
        fruitImageView.setFitHeight(50);
    }

    public void draw(Pane pane) {
        pane.getChildren().add(fruitImageView);
        fruitImageView.setX(x);
        fruitImageView.setY(y);
    }

    public void move(Snake snake) { // Da gecheckt werden muss, ob die Frucht in der Schlange ist, muss eine Schlange an die Funktion übergeben werden
        // Erstellt einen boolean, der weder true noch false ist
        boolean collision;

        // Wenn collision = true (Frucht ist in Schlange) -> do-Anweisung ausführen (Random Feld für Frucht wählen und checken, ob Frucht in Schlange ist)
        do {
            x = random.nextInt(12) * 50;
            y = random.nextInt(12) * 50;

            collision = snake.getBodyParts().stream().anyMatch(bodyPart -> x == bodyPart.getX() && y == bodyPart.getY());
        } while (collision);

        setXPos(x);
        setYPos(y);
    }

    public double getXPos() {
        return this.x;
    }

    public double getYPos() {
        return this.y;
    }

    public void setXPos(int xPos) {
        this.x = xPos;
        this.fruitImageView.setX(xPos);
    }

    public void setYPos(int yPos) {
        this.y = yPos;
        this.fruitImageView.setY(yPos);
    }
}
