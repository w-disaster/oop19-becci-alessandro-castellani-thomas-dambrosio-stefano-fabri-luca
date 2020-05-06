
package model.roundenvironment.players;

import model.roundenvironment.coordinate.Coordinate;

public interface Player {
	
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
