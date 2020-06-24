package model.roundenvironment.barriers;

import java.util.List;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.graph.Graph;

/**
 * This interface models round barriers.
 * @author luca
 *
 */
public interface RoundBarriers {

	/**
	 * Adds barrier in the game barriers.
	 *
	 * @param barrier the barrier
	 */
	void add(Barrier barrier);
			
	/**
	 * Contains.
	 *
	 * @param barrier the barrier
	 * @return true if barrier is contained in the game barriers
	 */
	boolean contains(Barrier barrier);
	
	/**
	 * Gets the barriers as list.
	 *
	 * @return immutable list of the round barriers
	 */
	List<Barrier> getBarriersAsList();
	
	/**
	 * Gets the barriers as graph.
	 *
	 * @return round barriers as graph
	 */
	Graph<Coordinate> getBarriersAsGraph(); 
}
