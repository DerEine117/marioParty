module net.rknabe.marioparty {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.rknabe.marioparty to javafx.fxml;
    exports net.rknabe.marioparty;
}