package model.roundenvironment.graph;

import java.util.List;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;

/**
 * this interface models the barriers of the current round as a graph, where each non-present edge 
 * represent a placed barrier.
 * @author luca
 *
 */
public interface BarriersGraph {
	
	/**
	 * adds a new barrier
	 * @param barrier
	 */
	void add(Pair<Coordinate, Coordinate> barrier);
	
	/**
	 * 
	 * @param barrier
	 * @return true if the graph contains the parameter's barrier
	 */
	boolean containsBarrier(Pair<Coordinate, Coordinate> barrier);
	
	/**
	 * 
	 * @return the graph as a list
	 */
	List<Pair<Coordinate, Coordinate>> getBarriersAsList();
	
	/**
	 * compute bfs and checks if there's a path from node source to line destination
	 * @param source
	 * @param destination
	 * @return
	 */
	boolean containsPath(Coordinate source, int destination);
}
