package model;
import java.util.List;
import java.util.Map;

import model.roundenvironment.BarrierEnvironment;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.*;

/**
 * An interface that describes Quoridor game model. It contains the environments each one respectively of a specific round,
 * the partial ranking of the players, and the board dimension that is the common field of all the environment objects. 
 * 
 * @author luca
 *
 * @param <X> type of environment.
 */
public interface Model<X> {
	
	/**
	 * return current turn environment
	 * @return
	 */
	X getCurrentRoundEnvironment();
	
	/**
	 * sets roundEnvironment as current round environment
	 * @param roundEnvironment
	 */
	void setCurrentRoundEnvironment(final X roundEnvironment);
	
	/**
	 * 
	 * @return game environments
	 */
	List<X> getGameRoundsEnvironments();
	
	/**
	 * 
	 * @return map containing player winners and losers collected till the current round
	 */
	Map<X, List<Pair<PlayerState, Player>>> getRanking();
	
	/**
	 * 
	 * @return game number of rounds
	 */
	Integer getNumberRounds();
	
	/**
	 * 
	 * @return game board dimension
	 */
	Integer getBoardDimension();
	
}
