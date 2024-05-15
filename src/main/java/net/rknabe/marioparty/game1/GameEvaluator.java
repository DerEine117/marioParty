package net.rknabe.marioparty.game1;

public class GameEvaluator {

    public static FieldState checkForGameEnd(Field[][] board) {

        for (int row = 0; row < 3; row++) {
            if (board[row][0].getState() != FieldState.EMPTY && board[row][0].getState() == board[row][1].getState() && board[row][0].getState() == board[row][2].getState()) {
                return board[row][0].getState();
            }
        }

        // check verticals
        for (int col = 0; col < 3; col++) {
            if (board[0][col].getState() != FieldState.EMPTY && board[0][col].getState() == board[1][col].getState() && board[0][col].getState() == board[2][col].getState()) {
                return board[0][col].getState();
            }
        }

        // check diagonal
        if (board[0][0].getState() != FieldState.EMPTY && board[0][0].getState() == board[1][1].getState() && board[0][0].getState() == board[2][2].getState()) {
            return board[0][0].getState();
        }
        if (board[0][2].getState() != FieldState.EMPTY && board[0][2].getState() == board[1][1].getState() && board[0][2].getState() == board[2][0].getState()) {
            return board[0][2].getState();
        }

        // Check for tie
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
            return null;
        }

        // if the game is not over, return EMpty
        return FieldState.EMPTY;
    }
}
