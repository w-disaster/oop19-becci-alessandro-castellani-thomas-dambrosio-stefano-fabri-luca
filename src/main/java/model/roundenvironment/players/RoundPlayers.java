package model.roundenvironment.players;

import java.util.List;

/**
 * This interface models the players of a round and keeps track of the current one.
 * @author luca
 *
 */
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
	 * sets player the current player.
	 * @param player
	 */
	void setCurrentPlayer(Player player);
	
	/**
	 * sets the current round players.
	 * @param players
	 */
	void setPlayers(List<Player> players);
	
}
