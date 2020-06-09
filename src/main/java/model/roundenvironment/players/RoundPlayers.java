package model.roundenvironment.players;

import java.util.List;

/**
 * This interface models the players and manages the turns (setting the one that must play) 
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
	void setCurrentPlayer(final Player player);
	
	/**
	 * sets the current round players.
	 * @param players
	 */
	void setPlayers(final List<Player> players);
	
}
