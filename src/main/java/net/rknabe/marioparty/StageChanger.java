package net.rknabe.marioparty;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageChanger {
    private static Stage window;
    private static Scene menu, game1, game2, game3, game4, game5, game6;

    public static void createStageController(Stage stage, Scene menuScene, Scene game1Scene,
                                             Scene game2Scene, Scene game3Scene, Scene game4Scene,
                                             Scene game5Scene, Scene game6Scene) {
        window = stage;
        menu = menuScene;
        game1 = game1Scene;
        game2 = game2Scene;
        game3 = game3Scene;
        game4 = game4Scene;
        game5 = game5Scene;
        game6 = game6Scene;

    }

    public static void setScene(int scene) {
        /*
        changes the scene. Syntax:
        StageChanger.setScene(0) -> Changes back to main menu
        StageChanger.setScene(1) -> Changes back to game 1
        StageChanger.setScene(2) -> Changes back to game 2
        StageChanger.setScene(3) -> Changes back to game 3
        StageChanger.setScene(4) -> Changes back to game 4
        StageChanger.setScene(5) -> Changes back to game 5
        StageChanger.setScene(6) -> Changes back to game 6

         */
        if (0 <= scene && scene <= 6) {
            switch (scene) {
                case 0:
                    window.setScene(menu);
                    window.setWidth(750);
                    window.setHeight(400);
                    window.setTitle("Main Menu");
                    window.sizeToScene();
                    break;

                case 1:
                    window.setScene(game1);
                    window.setTitle("Minigame 1");
                    break;

                case 2:
                    window.setScene(game2);
                    window.setTitle("Minigame 2");
                    window.setWidth(711);
                    window.setHeight(400);
                    break;

                case 3:
                    window.setScene(game3);
                    window.setTitle("Minigame 3");
                    window.setWidth(600);
                    window.setHeight(400);
                    break;

                case 4:
                    window.setScene(game4);
                    window.setTitle("Minigame 4");
                    window.setWidth(616);
                    window.setHeight(700);
                    break;

                case 5:
                    window.setScene(game5);
                    window.setTitle("Minigame 5");
                    window.setWidth(800);
                    window.setHeight(600);
                    break;

                case 6:
                    window.setScene(game6);
                    window.setWidth(600);
                    window.setHeight(1100);
                    window.setTitle("Minigame 6");
                    break;
            }
        }
    }


}
