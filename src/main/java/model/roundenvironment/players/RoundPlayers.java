package model.roundenvironment.players;

import java.util.List;

public interface RoundPlayers {

	/**
	 * 
	 * @return the current player.
	 */
	Player getCurrentPlayer();
	
	/**
	 * 
	 * @return game players
	 */
	List<Player> getPlayers();
	
	/**
	 * 
	 * @return board dimension
	 */
	int getBoardDimension();
	
	/**
	 * sets player the current player.
	 * @param player
	 */
	void setCurrentPlayer(final Player player);
	
	/**
	 * sets the current round players.
	 * @param players
	 */
	void setPlayers(final List<Player> players);
	
}
