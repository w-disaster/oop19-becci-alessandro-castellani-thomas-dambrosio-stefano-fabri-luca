package model.roundenvironment.players;

import model.roundenvironment.coordinate.Coordinate;

/**
 * This interface models a player of the game
 * @author luca
 *
 */
public interface Player {
	
	/**
	 * Default barriers number 
	 */
	public static final int DEFAULT_BARRIERS_NUMBER = 10;
	
	/**
	 * 
	 * @return the name of the player.
	 */
	String getNickname();

	/**
	 * 
	 * @return number of the remaining available barriers.
	 */
	int getAvailableBarriers();
	
	/**
	 * decreases the available barriers number.
	 */
	void setAvailableBarriers(int availableBarriers);
	
	/**
	 * 
	 * @return player finish line
	 */
	int getFinishLine();
	
	/**
	 * 
	 * @return true if this player wins.
	 */
	boolean isWinner();
	
	/**
	 * 
	 * @return current position Coordinate
	 */
	Coordinate getCoordinate();
	
	/**
	 * sets coordinate as current position
	 * @param coordinate
	 */
	void setCoordinate(Coordinate coordinate);
	
}
