package net.rknabe.marioparty.game3;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import net.rknabe.marioparty.GameController;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;


public class Game3Controller extends GameController {

    @FXML private Label wordLabel;
    @FXML private TextField guessField;
    @FXML private Label usedLettersLabel;
    @FXML private Label attemptsLeftLabel;
    @FXML private Label feedbackLabel;
    @FXML private Button submitGuess;

    @FXML private AnchorPane rootPane;
    private String secretWord;
    private StringBuilder currentGuess;
    private ArrayList<Character> previousGuesses = new ArrayList<>();
    private int maxAttempts = 6;
    private int attemptsLeft;

    public Game3Controller() throws IOException {
        secretWord = getSecretWord();
        currentGuess = new StringBuilder("_".repeat(secretWord.length()));
        attemptsLeft = maxAttempts;

        if (secretWord.length() > 1) {
            currentGuess.setCharAt(0, secretWord.charAt(0));
            currentGuess.setCharAt(secretWord.length() - 1, secretWord.charAt(secretWord.length() - 1));
        }
    }

    @FXML
    private void initialize() {
        // Set the background image on the rootPane using CSS
        String imagePath = getClass().getResource("/net/rknabe/marioparty/assets/mario-question-block.jpg").toExternalForm();
        rootPane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center center;");

        updateDisplay();
    }

    private void EndGame(boolean won){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spielende");
        alert.setHeaderText(null);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        if (won) {
            alert.setContentText("Herzlichen Glückwunsch! Du hast gewonnen!");
        } else {
            alert.setContentText("Game over! Das richtige Wort war: " + secretWord);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            backToMenuClick();
        }
    }

    @FXML
    private void handleGuess(ActionEvent event) {
        String guess = guessField.getText().trim().toLowerCase();
        if (guess.isEmpty() || guess.length() > 1) {
            feedbackLabel.setText("Please enter a single letter.");
            return;
        }
        char guessedLetter = guess.charAt(0);
        if (!Character.isLetter(guessedLetter)) {
            feedbackLabel.setText("Invalid input. Please enter a letter.");
            return;
        }
        if (guess(guessedLetter)) {
            if (isGameOver()) {
                feedbackLabel.setText("Congratulations! You won!");
                EndGame(true);
            } else {
                feedbackLabel.setText("Correct guess!");
            }

        } else {
            if (isGameOver()) {
                feedbackLabel.setText("Game over! Correct word was: " + secretWord);
                EndGame(false);
            } else {
                feedbackLabel.setText("Wrong guess. Try again.");
            }
        }
        guessField.clear();
        updateDisplay();
    }

    private boolean guess(char letter) {
        if (previousGuesses.contains(letter)) {
            feedbackLabel.setText("You've already guessed that letter.");
            return false;
        }

        previousGuesses.add(letter);
        boolean correct = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                currentGuess.setCharAt(i, letter);
                correct = true;
            }
        }
        if (!correct) {
            attemptsLeft--;
        }
        return correct;
    }

    private boolean isGameOver() {
        return currentGuess.toString().contains("_") == false || attemptsLeft <= 0;
    }

    private void updateDisplay() {
        wordLabel.setText(currentGuess.toString());
        usedLettersLabel.setText("Used Letters: " + previousGuesses);
        attemptsLeftLabel.setText("Attempts Left: " + attemptsLeft);
    }

    public String getSecretWord() throws IOException {
        ArrayList<String> allWords = new ArrayList<>();
        Path myFile = Paths.get("src/main/java/net/rknabe/marioparty/game3/Hangman.txt");
        BufferedReader myReader = Files.newBufferedReader(myFile);
        String line;
        while ((line = myReader.readLine()) != null) {
            allWords.add(line);
        }
        myReader.close();
        return allWords.get(new Random().nextInt(allWords.size()));
    }

}
