package net.rknabe.marioparty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {

    Stage window;
    Scene mainMenuScene;
    Scene game1Scene, game2Scene, game3Scene, game4Scene, game5Scene, game6Scene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        // Hauptmenü erstellen
        FXMLLoader fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("mainMenu-view.fxml"));
        mainMenuScene = new Scene(fxmlLoaderMenu.load(), 600, 500);

        VBox gameLayout = new VBox(10);

        // Initializing the game Scenes from fxml

        FXMLLoader fxmlLoaderGame1 = new FXMLLoader(MainApplication.class.getResource("game1-view.fxml"));
        game1Scene = new Scene(fxmlLoaderGame1.load(), 600, 400);

        FXMLLoader fxmlLoaderGame2 = new FXMLLoader(MainApplication.class.getResource("game2-view.fxml"));
        game2Scene = new Scene(fxmlLoaderGame2.load(), 600, 400);

        FXMLLoader fxmlLoaderGame3 = new FXMLLoader(MainApplication.class.getResource("game3-view.fxml"));
        game3Scene = new Scene(fxmlLoaderGame3.load(), 600, 400);

        FXMLLoader fxmlLoaderGame4 = new FXMLLoader(MainApplication.class.getResource("game4-view.fxml"));
        game4Scene = new Scene(fxmlLoaderGame4.load(), 600, 400);

        FXMLLoader fxmlLoaderGame5 = new FXMLLoader(MainApplication.class.getResource("game5-view.fxml"));
        game5Scene = new Scene(fxmlLoaderGame5.load(), 600, 400);

        FXMLLoader fxmlLoaderGame6 = new FXMLLoader(MainApplication.class.getResource("game6-view.fxml"));
        game6Scene = new Scene(fxmlLoaderGame6.load(), 600, 750);



        StageChanger.createStageController(window,mainMenuScene, game1Scene,game2Scene,game3Scene,game4Scene,game5Scene,game6Scene);



        window.setScene(mainMenuScene);

        window.setTitle("Mario Party Spiel");
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
