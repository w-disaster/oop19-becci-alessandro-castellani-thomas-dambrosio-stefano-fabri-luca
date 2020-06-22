package model.roundenvironment.barriers;

import java.util.ArrayList;

import java.util.List;


import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.graph.GraphImpl;

/**
 * Round barriers class
 * @author luca
 *
 */
public class RoundBarriersImpl implements RoundBarriers {

	private List<Barrier> barriers;
	private Graph<Coordinate> graph;
	
	/**
	 * barriers for a new round
	 */
	public RoundBarriersImpl(int boardDimension) {
		super();
		this.barriers = new ArrayList<>();
		this.graph = new GraphImpl<>(boardDimension);
	}

	/**
	 * round barriers from fields
	 * @param barriers
	 * @param graph
	 */
	public RoundBarriersImpl(final List<Barrier> barriers, Graph<Coordinate> graph) {
		super();
		this.barriers = barriers;
		this.graph = graph;
	}

	@Override
	public void add(Barrier barrier) {
		this.barriers.add(barrier);
		if(barrier.getOrientation().equals(Orientation.HORIZONTAL)) {
			this.graph.remove(new Pair<>(new Coordinate(barrier.getCoordinate().getX(), barrier.getCoordinate().getY()), 
						new Coordinate(barrier.getCoordinate().getX() + 1, barrier.getCoordinate().getY())));
		} else {
			this.graph.remove(new Pair<>(new Coordinate(barrier.getCoordinate().getX(), barrier.getCoordinate().getY()), 
					new Coordinate(barrier.getCoordinate().getX(), barrier.getCoordinate().getY() + 1)));
		}
	}

	@Override
	public boolean contains(Barrier barrier) {
		return this.barriers.contains(barrier);
	}

	@Override
	public List<Barrier> getBarriersAsList() {
		return List.copyOf(this.barriers);
	}

	@Override
	public Graph<Coordinate> getBarriersAsGraph() {
		return this.graph;
	}
	
}
