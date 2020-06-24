package model.roundenvironment.players;

import java.util.List;

/**
 * This interface models the players of a round and keeps track of the one of the current turn.
 * @author luca
 *
 */
public interface RoundPlayers {

	/**
	 * Gets the current player.
	 *
	 * @return the current player.
	 */
	Player getCurrentPlayer();
	
	/**
	 * Gets the players.
	 *
	 * @return game players
	 */
	List<Player> getPlayers();
	
	/**
	 * Sets player the current player.
	 *
	 * @param player the new current player
	 */
	void setCurrentPlayer(Player player);
	
	/**
	 * Sets the current round players.
	 *
	 * @param players the new players
	 */
	void setPlayers(List<Player> players);
	
}
