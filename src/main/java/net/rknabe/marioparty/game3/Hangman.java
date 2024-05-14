package net.rknabe.marioparty.game3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hangman extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/rknabe/marioparty/game3-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hangman Game");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Prints any loading errors to the console
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
