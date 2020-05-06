package model;
import java.util.List;
import java.util.Map;

import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.*;

public interface Model<X> {
	
	/**
	 * return current turn environment
	 * @return
	 */
	X getCurrentRoundEnvironment();
	
	/**
	 * 
	 * @return game environments
	 */
	List<X> getGameRoundsEnvironments();
	
	/**
	 * 
	 * @return map containing player winners and losers collected till the current round
	 */
	Map<Integer, List<Pair<PlayerState, Player>>> getRanking();
	
	/**
	 * 
	 * @return game rounds number
	 */
	Integer getRoundsNumber();
	
	/**
	 * 
	 * @return game board dimension
	 */
	Integer getBoardDimension();
	
}
