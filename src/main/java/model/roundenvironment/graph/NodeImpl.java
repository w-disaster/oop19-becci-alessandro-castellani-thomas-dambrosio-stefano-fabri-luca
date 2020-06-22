package model.roundenvironment.graph;

import model.roundenvironment.coordinate.Coordinate;

public class NodeImpl implements Node {

	private Coordinate coordinate;
	private Colour colour;
	
	public NodeImpl(Coordinate coordinate, Colour colour) {
		super();
		this.coordinate = coordinate;
		this.colour = colour;
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
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
