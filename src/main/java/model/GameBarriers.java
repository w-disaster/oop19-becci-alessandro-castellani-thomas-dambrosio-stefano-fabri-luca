package model;

public interface GameBarriers {

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
