package net.rknabe.marioparty.game1;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.rknabe.marioparty.GameController;
import net.rknabe.marioparty.StageChanger;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Game1Controller extends GameController implements Initializable {
    Drawer drawer;
    //GameEvaluator gameEvaluator;
    ComputerPlayer computerPlayer;
    Board board;
    boolean playerATurn;

    @FXML
    protected Canvas gameCanvas;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label turnLabel;

    @FXML
    @Override
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Spieler A beginnt das Spiel. " +
                "Klicke auf ein freies Feld, um deinen Zug zu machen. " +
                "Das Ziel ist es, drei Felder in einer Reihe zu haben. " +
                "Viel Spaß!");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set scene background
        Image backgroundImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/entireBackground.jpg"));
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        rootPane.setBackground(new Background(background));

        // draw game field
        GraphicsContext gf = gameCanvas.getGraphicsContext2D();
        drawer = new Drawer(gf);
        drawer.drawField();

        // creating the (empty) board
        board = new Board();

        // creating gameEvulator
        //gameEvaluator = new GameEvaluator(board);

        // intilializing ComputerPlayer
        computerPlayer = new ComputerPlayer(board);

        // first Player A starts
        playerATurn = true;
    }

    @FXML
    protected void gameCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (board.getClickedField(x,y).isFree() && board.getClickedField(x,y) != null) {
            drawer.drawMove(board.getClickedField(x,y), 'A');
            board.drawField(board.getClickedField(x,y), FieldState.A);
            board.draw();

            if (GameEvaluator.checkForGameEnd(board.toArray()) != FieldState.EMPTY) {
                gameEnd(GameEvaluator.checkForGameEnd(board.toArray()));
            } else {
                Field computerField = computerPlayer.findBestMove(board);
                System.out.println("Computetrfeld: " + computerField.drawField());
                drawer.drawMove(computerField, 'B');
                board.drawField(computerField, FieldState.B);
            }
            if (GameEvaluator.checkForGameEnd(board.toArray()) != FieldState.EMPTY) {
                gameEnd(GameEvaluator.checkForGameEnd(board.toArray()));
            }
        } else {
            Thread thread = new Thread(() -> {
                try {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            turnLabel.setTextFill(Color.RED);
                            turnLabel.setText("Field is not free!");
                        }
                    });
                    Thread.sleep(1000);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            turnLabel.setTextFill(Color.BLACK);
                            turnLabel.setText("Your turn");
                        }
                    });

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }

    }

    private void gameEnd(FieldState state) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("game end");
        alert.setHeaderText(null); // Keine Header-Text

        // Setzen des Nachrichtentexts
        Image endAlertImage;
        if (state == FieldState.A) {
            alert.setContentText("Player A has won!!! Congratulations!.");
            endAlertImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/PlayerA.png"), 45, 45, true, false);
        } else if (state == FieldState.B) {
            alert.setContentText("Computer Player has won!!.");
            endAlertImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/PlayerB.png"), 45, 45, true, false);
        } else {
            alert.setContentText("Tie! Sounds like a new game!");
            endAlertImage = new Image(getClass().getResourceAsStream("/net/rknabe/marioparty/assets/cloudMario.png"), 45, 45, true, false);
        }

        ImageView imageView = new ImageView(endAlertImage);
        imageView.setFitWidth(50); // Passe die Breite des Bildes an
        imageView.setFitHeight(50); // Passe die Höhe des Bildes an
        alert.setGraphic(imageView);

        // Hinzufügen einer OK-Schaltfläche
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Zeige den Dialog und warte auf die Benutzeraktion
        Optional<ButtonType> result = alert.showAndWait();

        // Handhabung der Benutzeraktion
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                System.out.println("game end. back to main menu.");
                board.clear();
                drawer.resetCanvas();
                drawer.drawField();
                backToMenuClick();
            }
        });
    }

}
