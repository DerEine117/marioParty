package net.rknabe.marioparty.game5;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.rknabe.marioparty.GameController;
import javafx.scene.image.Image;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class Game5Controller extends GameController implements Initializable {
    @FXML
    private GridPane gridPlayer1, gridPlayer2;
    @FXML
    private VBox gameLayout;
    @FXML
    private Label playerShipCountLabel;
    @FXML
    private Label computerShipCountLabel;

    private Board boardPlayer1, boardPlayer2;
    private ComputerPlayer computerPlayer;
    private List<Ship> playerShips;  // Declare playerShips as an instance variable
    private List<Ship> computerShips;
    private Canvas[][] canvasesPlayer1 = new Canvas[10][10];
    private Canvas[][] canvasesPlayer2 = new Canvas[10][10];

    @FXML
    @Override
    protected void onSpielInfoClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SpielInfo");
        alert.setHeaderText(null);
        alert.setContentText("Willkommen zu dem Minispiel \"Sternenschlacht\" – einem düsteren Abenteuer,"+
                " das \"Schiffe versenken\" neu interpretiert. Kämpfe gegen einen finsteren Dunkelheitskrieger," +
                " den Bowser, der seine Sternengruppen hinter undurchdringlichen Wolken verbirgt. Dein Ziel ist es,"+
                " diese versteckten Sternengruppen, die aus 1 bis 5 Sternen bestehen, zu finden, indem du auf die"+
                " Wolken klickst und sie entfernst. Aber sei vorsichtig: Der Dunkelheitskrieger sucht ebenfalls nach"+
                " Sternen und wird versuchen, deine vorgegebenen Sternengruppen zu entdecken. ");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create new boards for player 1 and player 2
        boardPlayer1 = new Board();
        boardPlayer2 = new Board();

        // Create a new computer player with player 2's board
        computerPlayer = new ComputerPlayer(boardPlayer2);

        // Create ships for both the player and the computer
        playerShips = createShips();
        computerShips = createShips();

        // Initialize the grid fields with the created boards and ships
        initializeGrid(gridPlayer1, boardPlayer1, false, playerShips);
        initializeGrid(gridPlayer2, boardPlayer2, true, computerShips);

        // Load the image from the specified path
        String imagePath = "file:src/main/resources/net/rknabe/marioparty/assets/game5/H.jpg";
        Image image = new Image(imagePath);

        // Create a BackgroundImage object with the loaded image
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        // Set the background image of the game layout
        gameLayout.setBackground(new Background(backgroundImage));

        // Update the count of ships for both the player and the computer
        updateShipCount();
    }


    private void initializeGrid(GridPane grid, Board board, boolean showShips, List<Ship> ships) {
        // Place ships on the game board
        for (Ship ship : ships) {
            placeShipRandomly(ship, board);
        }

        // Iterate over the 10x10 grid
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Create final copies of i and j for use in lambda expression
                final int finalI = i;
                final int finalJ = j;

                // Load the image
                Image image;
                // load the yellow star image
                if (showShips && board.isOccupied(i, j)) {
                    image = new Image("file:src/main/resources/net/rknabe/marioparty/assets/game5/Stern_gelb.png");
                    //or cloud image
                } else {
                    image = new Image("file:src/main/resources/net/rknabe/marioparty/assets/game5/Wolke.png");
                }

                // Create a new Canvas
                Canvas canvas = new Canvas(25, 25);
                if (grid == gridPlayer1) {
                    canvasesPlayer1[i][j] = canvas;
                } else if (grid == gridPlayer2) {
                    canvasesPlayer2[i][j] = canvas;
                }

                // Get the GraphicsContext to draw on the Canvas
                GraphicsContext gc = canvas.getGraphicsContext2D();

                // Draw the image on the Canvas
                gc.drawImage(image, 0, 0, 25, 25);

                // Add a mouse click event handler to the canvas
                canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    // If a ship is present at the clicked position on player 1's board, change the image to a red star
                    if (boardPlayer1.isOccupied(finalI, finalJ)) {
                        Image hitImage = new Image("file:src/main/resources/net/rknabe/marioparty/assets/game5/Stern_rot.png");
                        gc.clearRect(0, 0, 25, 25);
                        gc.drawImage(hitImage, 0, 0, 25, 25);
                        //or clear the image
                    } else {
                        gc.clearRect(0, 0, 25, 25);
                    }

                    // Call the hit() method if a ship is present at the clicked position
                    Ship ship = boardPlayer1.getShipAt(finalI, finalJ);
                    if (ship != null) {
                        ship.hit();
                    }

                    // Let the computer take its turn
                    int[] computerMove = computerPlayer.takeTurn();
                    GraphicsContext gcComputer = getGraphicsContextAt(computerMove[0], computerMove[1], gridPlayer2);

                    // If a ship is present at the computer's clicked position, change the image to a red star
                    if (boardPlayer2.isOccupied(computerMove[0], computerMove[1])) {
                        Image hitImage = new Image("file:src/main/resources/net/rknabe/marioparty/assets/game5/Stern_rot.png");
                        gcComputer.clearRect(0, 0, 25, 25);
                        gcComputer.drawImage(hitImage, 0, 0, 25, 25);
                        // Otherwise, clear the image
                    } else {
                        gcComputer.clearRect(0, 0, 25, 25);
                    }
                    ship = boardPlayer2.getShipAt(computerMove[0], computerMove[1]);
                    if (ship != null) {
                        ship.hit();
                    }
                });
                // Add the canvas to the grid
                grid.add(canvas, i, j);
            }
        }
    }

    // This method creates a list of ships with lengths from 1 to 5
    private List<Ship> createShips() {
        // Erstellen Sie eine Liste von Schiffen
        List<Ship> ships = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ships.add(createShip(i));
        }
        return ships;
    }

    // This method creates a ship with a given length and a random orientation
    private Ship createShip(int length) {
        // Erstellen Sie ein Schiff mit gegebener Länge und zufälliger Ausrichtung
        Random random = new Random();
        String orientation = random.nextBoolean() ? "horizontal" : "vertical";
        return new Ship(length, orientation, this);
    }

    // This method places a ship at a random position on the board
    private void placeShipRandomly(Ship ship, Board board) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(10 - (ship.orientation.equals("horizontal") ? ship.length - 1 : 0));
            y = random.nextInt(10 - (ship.orientation.equals("vertical") ? ship.length - 1 : 0));
        } while (!canPlaceShipAtPosition(ship, board, x, y));
        ship.position = new int[]{x, y};
        board.placeShip(ship);
    }

    // This method checks if a ship can be placed at a given position on the board
    private boolean canPlaceShipAtPosition(Ship ship, Board board, int x, int y) {
        if (ship.orientation.equals("horizontal")) {
            for (int i = 0; i < ship.length; i++) {
                if (board.isOccupied(x + i, y)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < ship.length; i++) {
                if (board.isOccupied(x, y + i)) {
                    return false;
                }
            }
        }
        return true;
    }

    // This method checks if a ship can be placed at a given position on the board
    public void shipSunk(Ship ship) {
        // Entfernen Sie das Schiff aus der entsprechenden Liste
        if (playerShips.contains(ship)) {
            playerShips.remove(ship);
        } else if (computerShips.contains(ship)) {
            computerShips.remove(ship);
        }
        updateShipCount();
        // Überprüfen Sie, ob das Spiel vorbei ist
        checkGameOver();
    }
    // This method checks if the game is over and shows a game over message if it is
    private void checkGameOver() {
        if (allShipsSunk(playerShips)) {
            showGameOverMessage("Mario-team has won!");
        } else if (allShipsSunk(computerShips)) {
            showGameOverMessage("Bowser-team has won!");
        }
    }

    // This method checks if all ships in a list are sunk
    private boolean allShipsSunk(List<Ship> ships) {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
    // This method shows a game over message with a given text
    private void showGameOverMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Load the image based on the winner
        Image image;
        if (message.contains("Mario-team")) {
            image = new Image("file:src/main/resources/net/rknabe/marioparty/assets/game5/mario-dance.gif");
        } else {
            image = new Image("file:src/main/resources/net/rknabe/marioparty/assets/game5/bowser-dance.gif");
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);  // Set the width of the ImageView to 100
        imageView.setFitHeight(100); // Set the height of the ImageView to 100

        // Set the ImageView as the graphic for the alert
        alert.setGraphic(imageView);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            backToMenuClick();
        }
    }

    // This method updates the count of remaining ships for both players
    private void updateShipCount() {
        playerShipCountLabel.setText("Remaining ships: " + playerShips.size());
        playerShipCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        computerShipCountLabel.setText("Remaining ships: " + computerShips.size());
        computerShipCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
    }

    // This method returns the GraphicsContext of a Canvas at a given position in a GridPane
    private GraphicsContext getGraphicsContextAt(int x, int y, GridPane grid) {
        Canvas canvas;
        if (grid == gridPlayer1) {
            canvas = canvasesPlayer1[x][y];
        } else if (grid == gridPlayer2) {
            canvas = canvasesPlayer2[x][y];
        } else {
            throw new IllegalArgumentException("Invalid grid");
        }
        return canvas.getGraphicsContext2D();
    }







}






