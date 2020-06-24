package model;
import java.util.List;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.*;

/**
 * An interface that describes Quoridor game model. It contains the environments, each one respectively of a specific round,
 * the current round and the partial winners collected until the current one.
 * 
 * @author luca
 *
 * @param <X> type of environment.
 */
public interface Model<X extends RoundEnvironment> {
	
	/** Standard board dimension (9x9). */
	public static final int BOARD_DIMENSION = 9;
	
	/** Standard game's number of rounds. */
	public static final int NUMBER_OF_ROUNDS = 3;
	
	/**
	 * Gets the current round environment.
	 *
	 * @return current Round Environment
	 */
	X getCurrentRoundEnvironment();
	
	/**
	 * sets roundEnvironment as current round environment.
	 *
	 * @param roundEnvironment the new current round environment
	 */
	void setCurrentRoundEnvironment(X roundEnvironment);
	
	/**
	 * Gets the game round environments.
	 *
	 * @return game environments
	 */
	List<X> getGameRoundEnvironments();
	
	/**
	 * Gets the winners.
	 *
	 * @return list containing player winners collected till the current round
	 */
	List<Player> getWinners();
	
	/**
	 * Gets the number rounds.
	 *
	 * @return game number of rounds
	 */
	int getNumberRounds();
	
	/**
	 * Gets the board dimension.
	 *
	 * @return game board dimension
	 */
	int getBoardDimension();
	
}
