package model;

public interface StandardGame<X extends StandardPlayer> {

	/**
	 * 
	 * @return the current player position in the grid
	 */
	Pair<Integer, Integer> getCurrentPlayer();
	
	/**
	 * 
	 * @return the two (all) players positions in the grid
	 */
	Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getPlayersPositions();
	
	/**
	 * this method changes the turn
	 */
	void changeTurn();
	
}
