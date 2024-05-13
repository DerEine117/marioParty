module net.rknabe.marioparty {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.rknabe.marioparty to javafx.fxml;
    exports net.rknabe.marioparty;
    exports net.rknabe.marioparty.game1;
    opens net.rknabe.marioparty.MainGame to javafx.fxml;
    exports net.rknabe.marioparty.MainGame to javafx.graphics; // Add this line

    opens net.rknabe.marioparty.game1 to javafx.fxml;
    opens net.rknabe.marioparty.game2 to javafx.fxml;
    opens net.rknabe.marioparty.game3 to javafx.fxml;
    opens net.rknabe.marioparty.game4 to javafx.fxml;
    opens net.rknabe.marioparty.game5 to javafx.fxml;
    opens net.rknabe.marioparty.game6 to javafx.fxml;
}