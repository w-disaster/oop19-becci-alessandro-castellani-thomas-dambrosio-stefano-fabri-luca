package model.roundenvironment.barriers;

/**
 * This interface contains the methods needed to implement game barriers.
 * @author luca
 *
 */
public interface RoundBarriers {

	/**
	 * adds barrier in the game barriers
	 * @param barrier
	 */
	void add(Barrier barrier);
			
	/**
	 * 
	 * @param barrier
	 * @return true if barrier is contained in the game barriers
	 */
	boolean contains(Barrier barrier);
	
}
