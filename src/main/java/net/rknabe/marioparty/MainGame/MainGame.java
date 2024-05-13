package net.rknabe.marioparty.MainGame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MainGame {
    private final Drawer drawer = new Drawer();

    @FXML
    private Button rollButton;
    @FXML
    ImageView dice1;
    @FXML
    ImageView dice2;
    @FXML
    ImageView playerPicture;
    @FXML
    private void clickOnRollButton() {
        drawer.drawPicture(playerPicture);
    }


}
