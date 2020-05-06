package model.roundenvironment.barriers;

public interface RoundBarriers {

	/**
	 * adds barrier in the game barriers
	 * @param barrier
	 */
	void add(final Barrier barrier);
			
	/**
	 * 
	 * @param barrier
	 * @return true if barrier is contained in the game barriers
	 */
	boolean contains(final Barrier barrier);
	
}
