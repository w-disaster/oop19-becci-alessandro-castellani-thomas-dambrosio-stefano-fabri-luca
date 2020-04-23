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
	Pair<StandardPlayer, StandardPlayer> getPlayers();
	
	/**
	 * 
	 * @return the board dimension
	 */
	int getDimension();
	
	/**
	 * 
	 * @return list of the players of the previous rounds
	 */
	List<Pair<StandardPlayer, StandardPlayer>> getPreviousRoundsPlayers();
	
	/**
	 * sets player the current player.
	 * @param player
	 */
	void setCurrentPlayer(StandardPlayer player);
	
	/**
	 * sets the current round players.
	 * @param players
	 */
	void setPlayers(Pair<StandardPlayer, StandardPlayer> players);
	
}
