package model;

public interface StandardGameFactory {

	/**
	 * 
	 * @param nickname1
	 * @param nickame2
	 * @return StandardGame with players in default positions with respectively nicknames.
	 */
	StandardGame getSGbyName(final String nickname1, final String nickame2);
	
	/**
	 * 
	 * @param players
	 * @return StandardGame with given players
	 */
	StandardGame getSGbyPlayers(final Pair<StandardPlayer, StandardPlayer> players);
	
	/**
	 * 
	 * @param dimension
	 * @param nickname1
	 * @param nickname2
	 * @return StandardPlayer with given board dimension and nicknames
	 */
	StandardGame getSGbyDimensionNicknames(final int boardDimension, final String nickname1, final String nickname2);
	
	/**
	 * 
	 * @param dimension
	 * @param players
	 * @return StandardGame with given board dimension and players.
	 */
	StandardGame getSGByDimensionPlayers(final int BoardDimension, final Pair<StandardPlayer, StandardPlayer> players);
}
