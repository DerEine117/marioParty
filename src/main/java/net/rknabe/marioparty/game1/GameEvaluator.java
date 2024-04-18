package net.rknabe.marioparty.game1;

public class GameEvaluator {
    Board gameBoard;
    public GameEvaluator(Board board) {
        this.gameBoard = board;
    }

    public FieldState checkForGameEnd() {
        Field[][] board = this.gameBoard.toArray();
        for (int row = 0; row < 3; row++) {
            if (board[row][0].getState() != FieldState.EMPTY && board[row][0].getState() == board[row][1].getState() && board[row][0].getState() == board[row][2].getState()) {
                return board[row][0].getState();
            }
        }

        // Überprüfen der vertikalen Spalten
        for (int col = 0; col < 3; col++) {
            if (board[0][col].getState() != FieldState.EMPTY && board[0][col].getState() == board[1][col].getState() && board[0][col].getState() == board[2][col].getState()) {
                return board[0][col].getState();
            }
        }

        // Überprüfen der Diagonalen
        if (board[0][0].getState() != FieldState.EMPTY && board[0][0].getState() == board[1][1].getState() && board[0][0].getState() == board[2][2].getState()) {
            return board[0][0].getState();
        }
        if (board[0][2].getState() != FieldState.EMPTY && board[0][2].getState() == board[1][1].getState() && board[0][2].getState() == board[2][0].getState()) {
            return board[0][2].getState();
        }

        // Überprüfen auf Unentschieden
        boolean isFull = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getState() == FieldState.EMPTY) {
                    isFull = false;
                    break;
                }
            }
        }
        if (isFull) {
            return null; // 'T' steht für Tie (Unentschieden)
        }

        // Wenn niemand gewonnen hat und das Spiel noch nicht vorbei ist
        return FieldState.EMPTY;
    }
}
