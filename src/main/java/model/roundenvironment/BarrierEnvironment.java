package model.roundenvironment;

import model.roundenvironment.barriers.RoundBarriers;

/**
 * An interface which contains the common method to all the game modes.
 * @author luca
 *
 */
public interface BarrierEnvironment {
	
	/**
	 * 
	 * @return object describing the barriers of the current round
	 */
	RoundBarriers getRoundBarriers();

}
