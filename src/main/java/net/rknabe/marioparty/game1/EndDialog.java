package net.rknabe.marioparty.game1;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EndDialog extends Dialog<String> {
    public EndDialog() {
        super();

        setTitle("Game end.");

        Button restartButton = new Button("new Game!");
        Button endGameButton = new Button("back to main menu");

        Button fileChooseButton = new Button("Save game history log?");
        fileChooseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Datei auswählen");
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                System.out.println("Ausgewählte Datei: " + selectedFile.getAbsolutePath());
                setResult(selectedFile.getAbsolutePath().toString());
                close();
            }
        });

        // Füge die Steuerelemente zum Dialog hinzu
        getDialogPane().setContent(new VBox(restartButton, endGameButton, fileChooseButton));

        restartButton.setOnAction(actionEvent -> {
            System.out.println("new game is starting");
            setResult("restart");
            close();
        });

        endGameButton.setOnAction(actionEvent -> {
            System.out.println("game end. back to main menu");
            setResult("end");
            close();
        });



    }
}
