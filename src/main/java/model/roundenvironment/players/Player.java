package model.roundenvironment.players;

import model.roundenvironment.coordinate.Coordinate;

/**
 * This interface models a player of the game.
 *
 * @author luca
 */
public interface Player {
	
	/** Default barriers number. */
	public static final int DEFAULT_BARRIERS_NUMBER = 10;
	
	/**
	 * Gets the nickname.
	 *
	 * @return the name of the player.
	 */
	String getNickname();

	/**
	 * Gets the available barriers.
	 *
	 * @return number of the remaining available barriers.
	 */
	int getAvailableBarriers();
	
	/**
	 * Sets the available barriers number.
	 *
	 * @param availableBarriers the new available barriers
	 */
	void setAvailableBarriers(int availableBarriers);
	
	/**
	 * Gets the finish line.
	 *
	 * @return player finish line
	 */
	int getFinishLine();
	
	/**
	 * Checks if is winner.
	 *
	 * @return true if this player wins.
	 */
	boolean isWinner();
	
	/**
	 * Gets the coordinate.
	 *
	 * @return current position Coordinate
	 */
	Coordinate getCoordinate();
	
	/**
	 * sets coordinate as current position.
	 *
	 * @param coordinate the new coordinate
	 */
	void setCoordinate(Coordinate coordinate);
	
}
