package net.rknabe.marioparty.game4;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class Snake {
    private final LinkedList<ImageView> bodyParts;
    private final int rectangleSize = 50;
    private String lastDirection = "d";
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public Snake() {
        // Schlange wird standardmäßig auf den drei hier angegeben Koordinaten initialisiert
        this.bodyParts = new LinkedList<>();
        ImageView head = createImageView("file:C:/Users/knoll/IdeaProjects/marioParty/src/main/java/net/rknabe/marioparty/game4/Images/HeadFin.png");
        head.setX(250);
        head.setY(300);
        bodyParts.add(head);
        ImageView tail_1 = createImageView("file:C:/Users/knoll/IdeaProjects/marioParty/src/main/java/net/rknabe/marioparty/game4/Images/Body1Fin.png");
        tail_1.setX(200);
        tail_1.setY(300);
        bodyParts.add(tail_1);
        ImageView tail_2 = createImageView("file:C:/Users/knoll/IdeaProjects/marioParty/src/main/java/net/rknabe/marioparty/game4/Images/Body2Fin.png");
        tail_2.setX(150);
        tail_2.setY(300);
        bodyParts.add(tail_2);
    }

    private ImageView createImageView(String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(rectangleSize);
        imageView.setFitHeight(rectangleSize);
        return imageView;
    }

    public void draw(Pane pane) {
        // zuerst die "alte" Schlange löschen
        pane.getChildren().clear();
        // dann neue Schlange zeichnen
        bodyParts.forEach(bodyPart -> pane.getChildren().add(bodyPart));
    }

    public void addBodyPart() {
        Random random = new Random();
        int randomBodypart = random.nextInt(4) + 1;
        // Eins der vier Images für den Snakebody wird zufällig gewählt
        ImageView tail = createImageView("file:C:/Users/knoll/IdeaProjects/marioParty/src/main/java/net/rknabe/marioparty/game4/Images/Body" + randomBodypart + "Fin.png");
        bodyParts.add(tail);
    }

    private void updateBodyPartsPosition(double headX, double headY) {
        // Alte Kopfposition bestimmen
        double oldHeadX = this.bodyParts.getFirst().getX();
        double oldHeadY = this.bodyParts.getFirst().getY();


        // Methodenparameter als neue Position für Kopf setzen
        this.bodyParts.getFirst().setX(headX);
        this.bodyParts.getFirst().setY(headY);


        // Körperimages dem Kopf hinterherziehen
        for (int i = 1; i < bodyParts.size(); i++) {
            // i-tes Körperteil in Variable speichern
            ImageView currentBodyPart = bodyParts.get(i);

            // Variable in X und Y aufteilen
            double oldBodyX = currentBodyPart.getX();
            double oldBodyY = currentBodyPart.getY();

            // X und Y Koordinate neu setzen -> Ursprüngliche Position des Kopfes
            currentBodyPart.setX(oldHeadX);
            currentBodyPart.setY(oldHeadY);

            // Postion des Kopfes wird zur Position des Körperteils hinter dem Kopf, um Schritte für den kompletten Körper zu wiederholen
            oldHeadX = oldBodyX;
            oldHeadY = oldBodyY;
        }
    }

    public void moveUp() {
        double headX = this.bodyParts.getFirst().getX();
        double headY = this.bodyParts.getFirst().getY() - rectangleSize; // Differenz, weil Board oben mit (0/0) startet und nach unten grö0er wird
        updateBodyPartsPosition(headX, headY);
        lastDirection = "w";
    }

    public void moveDown() {
        double headX = this.bodyParts.getFirst().getX();
        double headY = this.bodyParts.getFirst().getY() + rectangleSize;
        updateBodyPartsPosition(headX, headY);
        lastDirection = "s";
    }

    public void moveLeft() {
        double headX = this.bodyParts.getFirst().getX() - rectangleSize;
        double headY = this.bodyParts.getFirst().getY();
        updateBodyPartsPosition(headX, headY);
        lastDirection = "a";
    }

    public void moveRight() {
        double headX = this.bodyParts.getFirst().getX() + rectangleSize;
        double headY = this.bodyParts.getFirst().getY();
        updateBodyPartsPosition(headX, headY);
        lastDirection = "d";
    }

    // Methode zum Aktualisieren der Schlangenbewegung
    public void updateSnakeMovement() {
        if (upPressed) {
            moveUp();
        } else if (downPressed) {
            moveDown();
        } else if (leftPressed) {
            moveLeft();
        } else if (rightPressed) {
            moveRight();
        }
    }

    void stopSnakeMovement() {
        setUpPressed(false);
        setDownPressed(false);
        setLeftPressed(false);
        setRightPressed(false);
    }

    public boolean isGameOver() {
        double headX = getHeadX();
        double headY = getHeadY();

        //Boarder erreicht
        if (headX < 0 || headX >= 600 || headY < 0 || headY >= 600) {
            return true;
        }

        //Schlange berührt eigenes Körperteil
        for (int i = 1; i < bodyParts.size(); i++) {
            ImageView bodyPart = bodyParts.get(i);
            if (headX == bodyPart.getX() && headY == bodyPart.getY()) {
                return true;
            }
        }
        return false;
    }

    public double getHeadX() {
        return this.bodyParts.getFirst().getX();
    }

    public double getHeadY() {
        return this.bodyParts.getFirst().getY();
    }

    public String getLastDirection() {
        return this.lastDirection;
    }

    public List<ImageView> getBodyParts() {
        return bodyParts;
    }

    public int getSnakeLength() {
        return (bodyParts.size());
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
}