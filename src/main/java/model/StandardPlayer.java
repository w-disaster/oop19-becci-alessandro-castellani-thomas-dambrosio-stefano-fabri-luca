
package model;

public interface StandardPlayer {
	
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
	 * 
	 * @return the current position of the player.
	 */
	Coordinate getCoordinate();
	
	/**
	 * decreases the available barriers number.
	 */
	void placeBarrier();
	
	/**
	 * 
	 * @return true if this player wins.
	 */
	boolean isWinner();
	
}
