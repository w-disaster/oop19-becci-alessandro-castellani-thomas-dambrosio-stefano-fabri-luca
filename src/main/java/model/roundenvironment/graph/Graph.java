package model.roundenvironment.graph;



import model.roundenvironment.coordinate.Pair;

/**
 * this interface models the barriers of the current round as a graph, where each non-present edge 
 * represent a placed barrier.
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
	 * computes BFS and checks if there's a path from node source to line destination
	 * @param source
	 * @param destination
	 * @return true if there's a possible path, false otherwise
	 */
	boolean containsPath(X source, int destination);
}
