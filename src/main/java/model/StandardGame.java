package model;

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
