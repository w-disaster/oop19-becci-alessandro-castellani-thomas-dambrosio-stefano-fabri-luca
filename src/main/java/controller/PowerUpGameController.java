package controller;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

/**
 * This is the controller's interface for powerUp type game.
 * 
 * @author Stefano
 */
public interface PowerUpGameController extends GameController {

	/**
	 * Starts a new powerUp game.
	 *
	 * @param nicknamePlayer1 the nickname player 1
	 * @param nicknamePlayer2 the nickname player 2
	 */
	void newPowerUpGame(String nicknamePlayer1, String nicknamePlayer2);
	
    /**
     * Make a player move in the clicked position, checks and eventually applies powerUps.
     *
     * @param position the position
     */
	void invokeMove(Coordinate position);
	
	/**
	 * Place a barrier in the given position with the given orientation.
	 *
	 * @param position the position
	 * @param orientation the orientation
	 */
	void invokePlace(Coordinate position, Orientation orientation);
	
	/**
	 * Next round.
	 */
	void nextRound();
	
	/**
	 * Save game.
	 */
	void saveGame();
	
	/**
	 * Load game.
	 */
	void loadGame();
}