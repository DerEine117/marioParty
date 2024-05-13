package net.rknabe.marioparty.MainGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class MainGame extends Application {
    private final Drawer drawer = new Drawer();

    @FXML
    private AnchorPane gameField;
    @FXML
    private Button rollButton;
    @FXML
    ImageView dice1;
    @FXML
    ImageView dice2;
    @FXML
    ImageView playerPicture;
    @FXML
    private void onRollButtonClicked() {
        drawer.drawPicture(playerPicture);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/net/rknabe/marioparty/mainGame.fxml"));
        primaryStage.setTitle("Main Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}

