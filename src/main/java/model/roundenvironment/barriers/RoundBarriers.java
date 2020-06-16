package model.roundenvironment.barriers;

import java.util.List;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.coordinate.Coordinate;

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
	
	/**
	 * 
	 * @return immutable list of the round barriers
	 */
	List<Barrier> getBarriersAsList();
	
}
