package net.rknabe.marioparty.game1;

public class ComputerPlayer {
    private Board board;

    public ComputerPlayer(Board board) {
        this.board = board;
    }

    public Field findBestMove(Board board) {
        Field[][] currentBoard = board.toArray();
        int bestScore = Integer.MIN_VALUE;
        Field bestMove = null;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j].isFree()) {

                    // Kopie des aktuellen Spielfelds erstellen
                    Field[][] tempBoard = copyBoard(currentBoard);

                    // Zug machen
                    tempBoard[i][j].setState(FieldState.B);

                    // Minimax aufrufen
                    int score = minimax(tempBoard, 0, false);

                    // Besten Zug aktualisieren
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Field(i, j);
                    }

                    tempBoard[i][j].setState(FieldState.EMPTY);
                }
            }
        }
        return bestMove;
    }

    private Field[][] copyBoard(Field[][] original) {
        Field[][] copy = new Field[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = new Field(original[i][j].getRow(), original[i][j].getCol());
                copy[i][j].setState(original[i][j].getState());
            }
        }
        return copy;
    }

    private int minimax(Field[][] currentBoard, int depth, boolean isMaximizing) {
        // Überprüfe auf Endspiel oder maximale Tiefe
        FieldState gameResult = GameEvaluator.checkForGameEnd(currentBoard);
        if (gameResult != FieldState.EMPTY || depth == 4) {
            if (gameResult == FieldState.A)
                return -depth;
            else if (gameResult == FieldState.B)
                return depth;
            else
                return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j].isFree()) {
                        // Kopie des aktuellen Spielfelds erstellen
                        Field[][] tempBoard = copyBoard(currentBoard);
                        tempBoard[i][j].setState(FieldState.B);

                        int score = minimax(tempBoard, depth + 1, false);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j].isFree()) {
                        // Kopie des aktuellen Spielfelds erstellen
                        Field[][] tempBoard = copyBoard(currentBoard);
                        tempBoard[i][j].setState(FieldState.A);

                        int score = minimax(tempBoard, depth + 1, true);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

}
