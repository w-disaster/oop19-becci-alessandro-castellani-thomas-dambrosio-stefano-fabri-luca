package model.roundenvironment.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;

public class BarriersGraphImpl implements BarriersGraph {

	private List<Pair<Coordinate, Coordinate>> barriers;
	
	/**
	 * constructor of BarrierGraphImpl, given board dimension
	 * @param boardDimension
	 */
	public BarriersGraphImpl(int boardDimension) {
		this.barriers = new ArrayList<>();
		List<Coordinate> nodes = new ArrayList<>();
		for(int r = 0; r < boardDimension; r++) {
			for(int c = 0; c < boardDimension; c++) {
				nodes.add(new Coordinate(r, c));
			}
		}
		barriersGraphFromNodes(nodes);
	}
	
	/**
	 * private method to build the graph given the nodes, that in this case are the board coordinates
	 * @param nodes
	 */
	private void barriersGraphFromNodes(List<Coordinate> nodes) {
		for(Coordinate n : nodes) {
			for(Coordinate adj : Stream.of(new Coordinate(n.getX() - 1, n.getY()), new Coordinate(n.getX() + 1, n.getY()), 
					new Coordinate(n.getX(), n.getY() - 1), new Coordinate(n.getX(), n.getY() + 1))
					.collect(Collectors.toList())) {
				if(nodes.contains(adj)) {
					this.barriers.add(new Pair<>(n, adj));
				}
			}
		}
	}

	@Override
	public void add(Pair<Coordinate, Coordinate> barrier) {
		this.barriers.remove(barrier);
	}

	@Override
	public boolean containsBarrier(Pair<Coordinate, Coordinate> barrier) {
		return this.barriers.contains(barrier);
	}

	@Override
	public List<Pair<Coordinate, Coordinate>> getBarriersAsList() {
		return List.copyOf(this.barriers);
	}
	
}
