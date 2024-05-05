package net.rknabe.marioparty.game6;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import net.rknabe.marioparty.GameController;
import javafx.stage.Stage;

public class Game6Controller extends GameController {
    private Board board;
    private Stage stage;

    public Game6Controller() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startGame() {
        board = new Board(10, 10, 0.2);

        Scene scene = new Scene(board, 200, 200);

        scene.setOnMouseClicked(e -> {
            int x = (int) e.getX() / 20;
            int y = (int) e.getY() / 20;

            if (e.getButton() == MouseButton.PRIMARY) {
                board.revealTile(x, y);
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}