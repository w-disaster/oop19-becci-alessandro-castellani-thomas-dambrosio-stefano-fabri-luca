package view.game;

import java.util.List;
import java.util.Optional;

import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;

/**
 * The Interface for the view controller.
 * 
 * @author Stefano
 */
public interface ViewLogic {

    /**
     * Starts the game in the controller.
     */
	void startGame();

    /**
     * Set the players nicknames.
     * 
     * @param Optional<Pair<String, String>> An optional containing a pair with nicknames
     * 
     */
	void setPlayer(Optional<Pair<String, String>> players);

    /**
     * Adds the pane to the grid.
     *
     * @param position the position
     * @return the pane to add to the grid
     */
	BorderPane addPaneLogic(Coordinate position);

    /**
     * Sets the up click handler.
     *
     * @param position the position in which the click handler will be setted
     */
	void setUpClickHandler(Coordinate position);

    /**
     * Clears the grid and set player in the given coordinate, it also update barriers number.
     *
     * @param player1pos the player 1 position
     * @param player2pos the player 2 position
     * @param barriersP1 the barrier number of player 1
     * @param barriersP2 the barrier number of player 2
     */
	void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2);

    /**
     * SetupGrid but with a list of barriers to draw in the grid.
     *
     * @param player1pos the player 1 position
     * @param player2pos the player 2 position
     * @param barriersP1 the barrier number of player 1
     * @param barriersP2 the barrier number of player 2
     * @param barrierList the barrier list
     */
	void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2, List<Barrier> barrierList);

    /**
     * Move the player in the given position.
     *
     * @param position the position
     * @param player the player
     */
	void move(Coordinate position, String player);

    /**
     * Change selected label.
     *
     * @param player the current player 
     */
	void changeSelectedLabel(String player);

    /**
     * Sets the selected barrier, it is used to get which type of barrier has been clicked.
     *
     * @param type the selected barrier type, 
     */
	void setSelectedBarrier(String type);

    /**
     * Draw a barrier.
     *
     * @param barrier the barrier to draw
     */
	void drawBarrier(Barrier barrier);

    /**
     * Update barriers number.
     *
     * @param player the player
     * @param barriersNumber the barriers number
     */
	void updateBarriersNumber(String nickname, int availableBarriers);

    /**
     * Draw power ups.
     *
     * @param powerUpsAsList the power ups as list
     */
	void drawPowerUps(List<PowerUp> powerUpsAsList);

    /**
     * Delete power up.
     *
     * @param p the powerUp to delete
     */
	void deletePowerUp(PowerUp p);

    /**
     * Calls the endRound method in view and also calls the controller method for changing round.
     *
     * @param winner the winner
     */
	void endRound(String winner);

    /**
     * Calls the endGame method in view.
     *
     * @param winner the winner
     */
	void endGame(String winner);

    /**
     * Calls the saveGame method in controller.
     */
	void saveGame();

    /**
     * Draw text in the textAreas by calling the respective method in view. 
     *
     * @param textToDisplay the text to display
     */
	void drawTextLogic(String textToDisplay);

}
