package model.roundenvironment.graph;

import java.util.List;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.coordinate.Pair;

/**
 * This interface, that models the barriers of the current round as a graph with only edges, is a compromise due to a lack
 * of time and has the goal to check if there's a possible path for a player to reach its finish line. 
 * Each hole (non-present edge) represent a placed barrier.
 * Note: this graph has been built to only have the chance to remove edges, once the constructor sets them.
 *
 * @author luca
 * 
 * @param <X> the generic type
 */
public interface Graph<X> {
	
	/**
	 * Removes an edge.
	 *
	 * @param edge the edge
	 */
	void remove(Pair<X, X> edge);
	
	/**
	 * Gets the edges.
	 *
	 * @return list of the edges of the graph
	 */
	List<Pair<X, X>> getEdges();
	
	/**
	 * Converts barriers to edges to remove.
	 *
	 * @param barriers the barriers
	 * @return list of the edges to remove
	 */
	List<Pair<X, X>> barriersAsEdgesToRemove(List<Barrier> barriers);
	
	/**
	 * Computes BFS and checks if there's a path from node source to line destination 
	 * if we remove the edges provided by the parameter.
	 *
	 * @param edgesToRemove the edges to remove
	 * @param source the source
	 * @param destination the destination
	 * @return true, if successful
	 */
	boolean containsPath(List<Pair<X, X>> edgesToRemove, X source, int destination);
	
}
