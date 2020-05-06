package model.junk;

import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public interface PlayersFactory {

	/**
	 * 
	 * @param nickname1
	 * @param nickame2
	 * @return StandardGame with players in default positions with respectively nicknames.
	 */
	RoundPlayers getSGbyName(final String nickname1, final String nickame2);
	
	/**
	 * 
	 * @param players
	 * @return StandardGame with given players
	 */
	RoundPlayers getSGbyPlayers(final Pair<Player, Player> players);
	
	/**
	 * 
	 * @param dimension
	 * @param nickname1
	 * @param nickname2
	 * @return StandardPlayer with given board dimension and nicknames
	 */
	RoundPlayers getSGbyDimensionNicknames(final int boardDimension, final String nickname1, final String nickname2);
	
	/**
	 * 
	 * @param dimension
	 * @param players
	 * @return StandardGame with given board dimension and players.
	 */
	RoundPlayers getSGByDimensionPlayers(final int boardDimension, final Pair<Player, Player> players);
}
