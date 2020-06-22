package model.roundenvironment.graph;

import java.util.List;

import model.roundenvironment.coordinate.Pair;

/**
 * this interface, that models the barriers of the current round as a graph with only edges, has the goal to check if 
 * there's a possible path for a player to reach its finish line. 
 * Each hole (non-present edge) represent a placed barrier.
 * Note: this graph has been built to only have the chance to remove edges, once the constructor sets them.
 * @author luca
 *
 */
public interface Graph<X> {
	
	/**
	 * removes an edge
	 * @param edge
	 */
	void remove(Pair<X, X> edge);
	
	/**
	 * 
	 * @return list of the edges of the graph
	 */
	List<Pair<X, X>> getEdges();
	
	/**
	 * computes BFS and checks if there's a path from node source to line destination
	 * @param source
	 * @param destination
	 * @return true if there's a possible path, false otherwise
	 */
	boolean containsPath(X source, int destination);
}
