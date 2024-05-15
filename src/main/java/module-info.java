module net.rknabe.marioparty {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;
    requires javafx.graphics;
    requires java.desktop;


    opens net.rknabe.marioparty to javafx.fxml;
    exports net.rknabe.marioparty;
    exports net.rknabe.marioparty.game1;
    exports net.rknabe.marioparty.MainGame to javafx.graphics;
    opens net.rknabe.marioparty.game1 to javafx.fxml;
    opens net.rknabe.marioparty.game2 to javafx.fxml;
    opens net.rknabe.marioparty.game3 to javafx.fxml;
    exports net.rknabe.marioparty.game3 to javafx.graphics;
    opens net.rknabe.marioparty.game4 to javafx.fxml;
    opens net.rknabe.marioparty.game5 to javafx.fxml;
    opens net.rknabe.marioparty.game6 to javafx.fxml;
    opens net.rknabe.marioparty.MainGame to javafx.fxml;

}