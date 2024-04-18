package net.rknabe.marioparty.game1;

public class Board {
    private Field[][] board;

    // Constructor
    public Board() {
        // new empty board on game itit
        createEmptyBoard();
    }

    public Field getClickedField(double x, double y) {
        final int WIDTH = 300;
        final int HEIGHT = 300;
        final int CELLS = 3;

        int row = (int) (y / (HEIGHT / CELLS)); // Berechne die Zeilennummer des geklickten Feldes
        int column = (int) (x / (WIDTH / CELLS)); // Berechne die Spaltennummer des geklickten Feldes

        // Überprüfe, ob die Koordinaten innerhalb des Spielfelds liegen
        if (row >= 0 && row < CELLS && column >= 0 && column < CELLS) {
            return board[row][column]; // Gib das entsprechende Spielfeld zurück
        } else {
            return null; // Die Koordinaten liegen außerhalb des Spielfelds
        }
    }


    public void draw() {
        System.out.println("-   1 | 2 | 3");
        for (int i = 0; i < 3; i++) {
            System.out.print((i+1) + " - ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j].drawField() + " | ");
            }
            System.out.print("\n");
        }
    }

    public void clear() {
        createEmptyBoard();
    }

    public Field[][] toArray() {
        return board;
    }

    private void createEmptyBoard() {
        this.board = new Field[3][3];
        // Initialize the board with default empty fields
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Field(i,j);
            }
        }
    }
}
