package net.rknabe.marioparty.game5;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import net.rknabe.marioparty.GameController;
import javafx.scene.Node;

import java.net.URL;
import java.util.*;

public class Game5Controller extends GameController implements Initializable {
    @FXML
    private GridPane gridPlayer1, gridPlayer2;
    private Board boardPlayer1, boardPlayer2;
    private ComputerPlayer computerPlayer;
    private List<Ship> playerShips;  // Declare playerShips as an instance variable
    private List<Ship> computerShips;
    private Map<Button, Ship>buttonToShipMap;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardPlayer1 = new Board();
        boardPlayer2 = new Board();
        computerPlayer = new ComputerPlayer(boardPlayer1);

        // Create a list of ships
        playerShips = new ArrayList<>();
        computerShips = new ArrayList<>();

        buttonToShipMap = new HashMap<>();

        // Call the placeShipsRandomly method to place ships randomly on the boards
        placeShipsRandomly();

        initializeGrid(gridPlayer1, boardPlayer1, true);
        initializeGrid(gridPlayer2, boardPlayer2, false);
    }

    public void placeShipsRandomly() {
        Random random = new Random();
        for (int size = 1; size <= 5; size++) {
            Ship playerShip = new Ship(size, boardPlayer1);
            Ship computerShip = new Ship(size, boardPlayer2);

            // Set the orientation of the ships
            playerShip.orientation = playerShip.getRandomOrientation();
            computerShip.orientation = computerShip.getRandomOrientation();

            // Try to place the ships
            boolean playerShipPlaced = false;
            boolean computerShipPlaced = false;
            while (!playerShipPlaced || !computerShipPlaced) {
                if (!playerShipPlaced) {
                    playerShip.position = playerShip.getRandomPosition();
                    if (boardPlayer1.canPlaceShip(playerShip)) {
                        boardPlayer1.placeShip(playerShip);
                        playerShipPlaced = true;
                    }
                }
                if (!computerShipPlaced) {
                    computerShip.position = computerShip.getRandomPosition();
                    if (boardPlayer2.canPlaceShip(computerShip)) {
                        boardPlayer2.placeShip(computerShip);
                        computerShipPlaced = true;
                    }
                }
            }

            playerShips.add(playerShip);
            computerShips.add(computerShip);

            // Add each field of the ship to the buttonToShipMap
            for (int i = 0; i < size; i++) {
                addShipToButtonMap(playerShip, gridPlayer1, i);
                addShipToButtonMap(computerShip, gridPlayer2, i);
            }
        }
    }

    private void addShipToButtonMap(Ship ship, GridPane grid, int i) {
        int x = ship.position[0] + (ship.orientation.equals("horizontal") ? i : 0);
        int y = ship.position[1] + (ship.orientation.equals("vertical") ? i : 0);
        Button button = getButtonAt(grid, x, y);
        if (button != null) {
            buttonToShipMap.put(button, ship);
        }
    }



    private void initializeGrid(GridPane grid, Board board, boolean showShips) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i;
                int y = j;

                Button button = new Button();
                button.setPrefWidth(50);  // Set the preferred width to 50
                button.setPrefHeight(50); // Set the preferred height to 50

                //show or hide ships
                if (showShips && board.isOccupied(i, j)) {
                    button.setStyle("-fx-background-color: black;");
                }



                button.setOnAction(event -> {

                    // click = shoot on the board
                    boolean hit = board.shoot(x, y);

                    // If a ship was hit, color the button red. Otherwise, color it dark gray.
                    if (hit) {
                        button.setStyle("-fx-background-color: red;");
                    } else {
                        button.setStyle("-fx-background-color: darkgray;");
                    }

                    // if the player schoots on the board of computer, the computer will take a turn
                    if (grid == gridPlayer2) {
                        int[] computerShot = computerPlayer.takeTurn();
                        boolean computerHit = boardPlayer1.shoot(computerShot[0], computerShot[1]);

                        System.out.println("Computer shot at coordinates: " + computerShot[0] + ", " + computerShot[1]);
                        System.out.println("Was the shot a hit? " + computerHit);

                        // Find the button corresponding to the computer's shot
                        for (Node node : gridPlayer1.getChildren()) {
                            if (GridPane.getColumnIndex(node) == computerShot[0] && GridPane.getRowIndex(node) == computerShot[1]) {
                                Button computerButton = (Button) node;

                                // If the computer hit a ship, color the button red. Otherwise, color it dark gray.
                                if (computerHit) {
                                    computerButton.setStyle("-fx-background-color: red;");
                                } else {
                                    computerButton.setStyle("-fx-background-color: darkgray;");
                                }
                            }
                        }
                    }

                    // das Ship-Objekt wird abgerufen, das mit dem angeklickten Button verknüpft ist
                    Ship clickedship = buttonToShipMap.get(button);

                    if (clickedship != null) {

                        // clicked schip wird als getroffen markiert und die Anzahl der Treffer wird erhöht
                        clickedship.hit();

                        // Wenn das Schiff versenkt ist, wird es aus der Liste der Schiffe entfernt
                        if (clickedship.isSunk()) {

                            // Wenn das Schiff des Spielers getroffen wurde
                            if (playerShips.contains(clickedship)) {
                                //wird es aus der Liste der Spieler entfernt
                                checkAndRemoveSunkShip(playerShips, clickedship);

                                //überprüft, ob das Schiff erfolgreich aus der Liste der Schiffe des Spielers entfernt wurde.
                                if (isShipRemoved(playerShips, clickedship)) {
                                    System.out.println("The player's ship of length " + clickedship.length + " has been removed from the list");
                                } else {
                                    System.out.println("The player's ship is still in the list");
                                }

                                //Hier wird überprüft, ob alle Schiffe des Spielers gesunken sind.
                                // Wenn ja, wird das Spiel beendet und eine Nachricht ausgegeben, dass der Computer gewonnen hat.
                                if (areAllShipsSunk(playerShips)) {
                                    endGame("Computer hat gewonnen");
                                }


                            } else if (computerShips.contains(clickedship)) {
                                checkAndRemoveSunkShip(computerShips, clickedship);


                                if (isShipRemoved(computerShips, clickedship)) {
                                    System.out.println("The computer's ship of length " + clickedship.length + " has been removed from the list");
                                } else {
                                    System.out.println("The computer's ship is still in the list");
                                }


                                if (areAllShipsSunk(computerShips)) {
                                    endGame("Du hast gewonnen");
                                }
                            }
                        }
                    }
                });
                grid.add(button, i, j);
            }
        }
        // Debugging-Ausgabe hinzufügen
        for (Map.Entry<Button, Ship> entry : buttonToShipMap.entrySet()) {
            Button button = entry.getKey();
            Ship ship = entry.getValue();
            System.out.println("Button hashcode: " + button.hashCode() + ", Ship position: " + Arrays.toString(ship.position));
        }

    }

    private Button getButtonAt(GridPane grid, int x, int y) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                return (Button) node;
            }
        }
        return null;
    }


    private void endGame(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spielende");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private boolean isShipRemoved(List<Ship> ships, Ship shipToRemove) {
        for (Ship ship : ships) {
            if (ship == shipToRemove) {
                return false;
            }
        }
        return true;
    }
    private void checkAndRemoveSunkShip(List<Ship> ships, Ship shipToCheck) {
        if (shipToCheck.isSunk()) {
            ships.remove(shipToCheck);
        }
    }
    //depnding on the ship that is hit, remove it from the list of ships
    private boolean areAllShipsSunk(List<Ship> ships) {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                System.out.println("Ship is not sunk: " + ship);
                return false;
            }
        }
        return true;
    }
}
