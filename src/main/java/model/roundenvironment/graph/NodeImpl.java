package model.roundenvironment.graph;

import java.util.Optional;

import model.roundenvironment.coordinate.Coordinate;

public class NodeImpl implements Node {

	private Coordinate coordinate;
	private Optional<Integer> distance;
	private Colour colour;
	
	public NodeImpl(Coordinate coordinate, Optional<Integer> distance, Colour colour) {
		super();
		this.coordinate = coordinate;
		this.distance = distance;
		this.colour = colour;
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	@Override
	public void setDistance(Optional<Integer> distance) {
		this.distance = distance;
	}

	@Override
	public Optional<Integer> getDistance() {
		return this.distance;
	}

	@Override
	public void setColour(Colour colour) {
		this.colour = colour;
	}

	@Override
	public Colour getcolour() {
		return this.colour;
	}

}
