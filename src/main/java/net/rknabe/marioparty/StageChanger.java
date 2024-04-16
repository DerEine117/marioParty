package net.rknabe.marioparty;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageController {
    private static Stage window;
    private static Scene menu, game1, game2, game3;

    public static void createStageController(Stage stage, Scene menuScene, Scene game1Scene, Scene game2Scene, Scene game3Scene) {
        window = stage;
        menu = menuScene;
        game1 = game1Scene;
        game2 = game2Scene;
        game3 = game3Scene;

    }

    public static void setScene(int scene) {
        if (0 <= scene && scene <= 3) {
            switch (scene) {
                case 0:
                    window.setScene(menu);
                    break;

                case 1:
                    window.setScene(game1);
                    break;

                case 2:
                    window.setScene(game2);
                    break;

                case 3:
                    window.setScene(game3);
                    break;
            }
        }
    }


}
