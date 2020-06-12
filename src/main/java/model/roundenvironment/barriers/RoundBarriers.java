package model.roundenvironment.barriers;

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
	 * @param coordinate
	 * @param orientation
	 * @return barrier with Coordinate coordinate and Orientation orientation
	 */
	Barrier getBarrierFrom(Coordinate coordinate, Orientation orientation);
	
}
