package model;

import java.util.List;

public interface StandardGame<X extends StandardPlayer> {

	/**
	 * 
	 * @return the current player.
	 */
	X getCurrentPlayer();
	
	/**
	 * 
	 * @return game players
	 */
	Pair<X, X> getPlayers();
	
	/**
	 * 
	 * @return list of the players of the previous rounds
	 */
	List<Pair<X, X>> getPreviousPlayers();
	
	/**
	 * sets player the current player.
	 * @param player
	 */
	void setCurrentPlayer(X player);
	
	/**
	 * sets the current round players.
	 * @param players
	 */
	void setPlayers(Pair<X, X> players);
	
	/* Controller?
	void changeTurn();
	
	void changeRound();
	*/
	
}
