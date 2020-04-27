package model;

import java.util.List;

public interface StandardGame {

	/**
	 * 
	 * @return the current player.
	 */
	StandardPlayer getCurrentPlayer();
	
	/**
	 * 
	 * @return game players
	 */
	List<StandardPlayer> getPlayers();
	
	/**
	 * 
	 * @return board dimension
	 */
	int getBoardDimension();
	
	/**
	 * sets player the current player.
	 * @param player
	 */
	void setCurrentPlayer(StandardPlayer player);
	
	/**
	 * sets the current round players.
	 * @param players
	 */
	void setPlayers(List<StandardPlayer> players);
	
}
