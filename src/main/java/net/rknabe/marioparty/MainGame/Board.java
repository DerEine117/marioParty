package net.rknabe.marioparty.MainGame;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Board {
    private boolean[][] visited = new boolean[8][8]; // Besuchte Felder
    private int counter = 1; // Zähler für die Nummerierung

    private Map<String, String> specialFields = new HashMap<>();
    private Map<String, Rectangle> rectangles = new HashMap<>();
    private Map<String, String> fieldStates = new HashMap<>();
    private Map<String, Field> fields = new HashMap<>();


    private Set<String> whiteFields = new HashSet<>();
    private Random random = new Random();

    public Board() {
        // Füllen Sie das whiteFields Set mit den Koordinaten, die weiß sein sollen
        for (int j = 1; j < 8; j++) {
            whiteFields.add("3," + j);
            whiteFields.add("7," + j);

        }
        for (int j = 0; j < 7; j++) {
            whiteFields.add("1," + j);
            whiteFields.add("5," + j);
        }


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String key = i + "," + j;
                if (!whiteFields.contains(key)) {
                    int event = random.nextInt(3); // Zufällige Zahl zwischen 0 und 2
                    switch (event) {
                        case 0:
                            specialFields.put(key, "+coins");
                            break;
                        case 1:
                            specialFields.put(key, "-coins");
                            break;
                        case 2:
                            specialFields.put(key, "neutral");
                            break;
                    }
                    fieldStates.put(key, String.valueOf(event));

                }
            }
        }
    }

    public GridPane initializeBoard(GridPane gridPane) {
        gridPane.setHgap(7);
        gridPane.setVgap(7);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String key = i + "," + j;
                Rectangle rectangle = new Rectangle(43, 43);
                rectangles.put(key, rectangle); // Speichern Sie das Rectangle in der Map
                if (whiteFields.contains(key)) {
                    rectangle.setFill(Color.WHITE);
                } else if (specialFields.containsKey(key)) {
                    String event = specialFields.get(key);
                    switch (event) {
                        case "+coins":
                            rectangle.setFill(Color.GREEN);
                            break;
                        case "-coins":
                            rectangle.setFill(Color.RED);
                            break;
                        case "neutral":
                            rectangle.setFill(Color.GRAY);
                            break;
                    }
                    // Erstellen Sie ein Field-Objekt und speichern Sie es in der Map
                    Field field = new Field(counter, i, j);
                    field.setState(Integer.parseInt(fieldStates.get(key)));
                    fields.put(key, field);
                }
                gridPane.add(rectangle, j, i);
            }
        }

        return gridPane;
    }

    public void removeField(GridPane gridPane, int x, int y) {
        String key = x + "," + y;
        Rectangle rectangle = rectangles.get(key);
        if (rectangle != null) {
            gridPane.getChildren().remove(rectangle);
            gridPane.getChildren().remove(fields.get(key));
        }
    }

    public void setupBoard(GridPane gridPane) {
        initializeBoard(gridPane);

        // Entfernen Sie die Felder nach der Initialisierung des Boards
        for (int j = 1; j < 8; j++) {
            removeField(gridPane, 3, j);
            removeField(gridPane, 7, j);
        }
        for (int j = 0; j < 7; j++) {
            removeField(gridPane, 1, j);
            removeField(gridPane, 5, j);
        }
    }
    public void numberFieldsDFS(GridPane gridPane, int x, int y) {
        // Überprüfen Sie, ob die Koordinaten innerhalb des Grids liegen und ob das Feld noch nicht besucht wurde
        if (x < 0 || y < 0 || x >= 8 || y >= 8 || visited[x][y]) {
            return;
        }
        String key = x + "," + y;
        // Überprüfen Sie, ob das Feld einen der drei Zustände hat
        if (specialFields.containsKey(key)) {
            // Markieren Sie das Feld als besucht
            visited[x][y] = true;

            // Erstellen Sie ein Label mit der aktuellen Zählerzahl und fügen Sie es zum GridPane hinzu
            Label label = new Label(String.valueOf(counter));
            gridPane.add(label, y, x);

            // Setzen Sie den Zustand und die Feldnummer für das aktuelle Feld
            Field field = fields.get(key);
            if (field != null) {
                field.setState(Integer.parseInt(fieldStates.get(key)));
                field.setFieldNumber(counter);
            }
            // Erhöhen Sie den Zähler
            counter++;

            // Besuchen Sie alle benachbarten Felder
            numberFieldsDFS(gridPane, x - 1, y);
            numberFieldsDFS(gridPane, x + 1, y);
            numberFieldsDFS(gridPane, x, y - 1);
            numberFieldsDFS(gridPane, x, y + 1);
        }
    }
    public void setFieldStateBasedOnColor() {
        for (Map.Entry<String, Rectangle> entry : rectangles.entrySet()) {
            String key = entry.getKey();
            Rectangle rectangle = entry.getValue();
            Field field = fields.get(key);

            if (field != null) {
                if (rectangle.getFill().equals(Color.RED)) {
                    field.setState(2);
                } else if (rectangle.getFill().equals(Color.GREEN)) {
                    field.setState(1);
                } else if (rectangle.getFill().equals(Color.GRAY)) {
                    field.setState(0);
                }
            }
        }
    }
    public void printFields() {
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            Field field = entry.getValue();
            System.out.println("Koordinaten: " + entry.getKey());
            System.out.println("Feldnummer: " + field.getFieldNumber());
            System.out.println("Zustand: " + field.getState());
            System.out.println("-------------------");
        }
    }
    public Field getFieldByNumber(int fieldNumber) {
        for (Field field : fields.values()) {
            if (field.getFieldNumber() == fieldNumber) {
                return field;
            }
        }
        return null;
    }
    public Map<String, Field> getFields() {
        return fields;
    }
    public Rectangle getRectangleByCoordinates(int x, int y) {
        String key = x + "," + y;
        System.out.println("Key: " + key);
        return rectangles.get(key);

    }



}
