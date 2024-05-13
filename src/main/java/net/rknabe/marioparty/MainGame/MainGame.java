package net.rknabe.marioparty.MainGame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MainGame {

    @FXML
    private Button rollButton;
    @FXML
    ImageView dice1;
    @FXML
    ImageView dice2;
    @FXML
    ImageView playerPicture;

    Drawer drawer = new Drawer();

    drawer.drawPicture(playerPicture);
}
