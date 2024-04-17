package net.rknabe.marioparty.game1;

public class Board {
    private Field[][] board;

    // Constructor
    public Board() {
        board = new Field[3][3];
        // Initialize the board with default empty fields
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Field();
            }
        }
    }

    public void draw() {
        System.out.println("-   1 | 2 | 3");
        for (int i = 0; i < 3; i++) {
            System.out.print((i+1) + " - ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[j][j].drawField() + " | ");
            }
            System.out.print("\n");
        }
    }
}
