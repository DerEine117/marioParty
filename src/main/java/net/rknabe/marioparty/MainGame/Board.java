package net.rknabe.marioparty.MainGame;

import javafx.scene.layout.GridPane;
import net.rknabe.marioparty.MainGame.Field;

import java.util.Random;

public class Board extends GridPane {
    private Field[][] fields;
    public static final int BOARD_SIZE = 10;
    private static final Random RANDOM = new Random();
    public static final int NUMBER_OF_FIELDS = BOARD_SIZE * BOARD_SIZE;

    public Board() {
        fields = new Field[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }
private void initializeBoard() {
    // Initialize all fields before generating the path
    for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            fields[i][j] = new Field("leer", 5);
        }
    }

    int[][] path = generatePath();

    // Number the fields along the path before generating the states
    numberFieldsAlongPath(path);

    for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            String state;
            switch (path[i][j]) {
                case 0:
                    state = "leer";
                    break;
                case 1:
                    state = generateRandomState(i,j);
                    break;
                default:
                    state = "nichts";
                    break;
            }
            fields[i][j].setState(state);

            add(fields[i][j].getPane(), j, i);
        }
    }
}
private int[][] generatePath() {
    // Initialize the path
    int[][] path = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},

    };
    return path;
}
    private void numberFieldsAlongPath(int[][] path) {
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        int fieldNumber = 1;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (path[i][j] == 1 && !visited[i][j]) {
                    fieldNumber = dfs(path, visited, i, j, fieldNumber);
                }
            }
        }
    }

    private int dfs(int[][] path, boolean[][] visited, int i, int j, int fieldNumber) {
        // Check if the current position is out of bounds or if the field has been visited
        if (i < 0 || i >= BOARD_SIZE || j < 0 || j >= BOARD_SIZE || visited[i][j] || path[i][j] == 0) {
            return fieldNumber;
        }

        // Mark the field as visited
        visited[i][j] = true;

        // Set the field number
        fields[i][j].setFieldNumber(fieldNumber++);

        // Visit all adjacent fields
        fieldNumber = dfs(path, visited, i - 1, j, fieldNumber);  // Up
        fieldNumber = dfs(path, visited, i + 1, j, fieldNumber);  // Down
        fieldNumber = dfs(path, visited, i, j - 1, fieldNumber);  // Left
        fieldNumber = dfs(path, visited, i, j + 1, fieldNumber);  // Right

        return fieldNumber;
    }


    private String generateRandomState(int x, int y) {
        // Überprüfe, ob das Feld auf dem Weg liegt (nicht leer und mit einer Feldnummer versehen)
        if (fields[x][y].getFieldNumber() != 0) {
            int randomValue = RANDOM.nextInt(100);
            if (randomValue < 40) {
                return "setscore +5";
            } else if (randomValue < 80) {
                return "setscore -5";
            } else {
                return "nichts";
            }
        } else {
            return "leer"; // Nicht nummerierte Felder bleiben leer
        }
    }


    public Field getField(int x, int y) {
        return fields[x][y];
    }
   public Field getFieldByNumber(int fieldNumber) {
    for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (fields[i][j].getFieldNumber() == fieldNumber && !fields[i][j].getState().equals("leer")) {
                return fields[i][j];
            }
        }
    }
    return null;
}

    public int getLastFieldNumber() {
        int lastFieldNumber = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (fields[i][j].getFieldNumber() > lastFieldNumber) {
                    lastFieldNumber = fields[i][j].getFieldNumber();
                }
            }
        }
        return lastFieldNumber;
    }
}