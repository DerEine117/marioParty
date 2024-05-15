package net.rknabe.marioparty.MainGame;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Board {
    private boolean[][] visited = new boolean[8][8];
    private int counter = 1;

    private Map<String, String> specialFields = new HashMap<>();
    private Map<String, Rectangle> rectangles = new HashMap<>();
    private Map<String, String> fieldStates = new HashMap<>();
    private Map<String, Field> fields = new HashMap<>();
    private Map<String, ImageView> imageViews = new HashMap<>();
    private Map<String, ImageView> fieldImages = new HashMap<>();
    private Set<String> whiteFields = new HashSet<>();
    private Map<String, ImageView> playerImageViews = new HashMap<>();
    private Random random = new Random();

    public ImageView getPlayerImageViewByCoordinates(String key) {
        return playerImageViews.get(key);
    }

    public void setPlayerImageViewByCoordinates(String key, ImageView imageView) {
        playerImageViews.put(key, imageView);
    }
    public Board() {
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
                    int event = random.nextInt(3);
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
                rectangles.put(key, rectangle);
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
                    gridPane.add(rectangle, j, i);

                    // Erstellen -> Field-Objekt und speichern in der Map
                    Field field = new Field(counter, i, j);
                    field.setState(Integer.parseInt(fieldStates.get(key)));
                    fields.put(key, field);

                    if ("+coins".equals(event)&& !field.hasPlayer()) {
                        URL resourceUrl = getClass().getResource("/net/rknabe/marioparty/assets/MainGame/coin.gif");
                        Image image = new Image(resourceUrl.toExternalForm());
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(43);
                        imageView.setFitWidth(43);
                        gridPane.add(imageView, j, i);
                        fieldImages.put(key, imageView);

                        imageViews.put(key, imageView); // Speichern-> ImageView
                    }
                    if ("-coins".equals(event) && !field.hasPlayer()) {
                        URL resourceUrl = getClass().getResource("/net/rknabe/marioparty/assets/MainGame/looseCoins.JPG");
                        Image image = new Image(resourceUrl.toExternalForm());
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(43);
                        imageView.setFitWidth(43);
                        gridPane.add(imageView, j, i);

                        fieldImages.put(key, imageView);

                        imageViews.put(key, imageView);
                    }
                }
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
        if (x < 0 || y < 0 || x >= 8 || y >= 8 || visited[x][y]) {
            return;
        }
        String key = x + "," + y;
        if (specialFields.containsKey(key)) {
            visited[x][y] = true;

            Label label = new Label(String.valueOf(counter));
            gridPane.add(label, y, x);

            Field field = fields.get(key);
            if (field != null) {
                field.setState(Integer.parseInt(fieldStates.get(key)));
                field.setFieldNumber(counter);
            }
            counter++;

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
        return rectangles.get(key);
}
}