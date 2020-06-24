package model.roundenvironment;

import model.roundenvironment.barriers.RoundBarriers;

/**
 * This interface contains the method common to all the game modes.
 * @author luca
 *
 */
public interface BarrierEnvironment {
	
	/**
	 * Gets the round barriers.
	 *
	 * @return object describing the barriers of the current round
	 */
	RoundBarriers getRoundBarriers();

}
