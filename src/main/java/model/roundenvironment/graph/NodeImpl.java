package model.roundenvironment.graph;

import model.roundenvironment.coordinate.Coordinate;

/**
 * The NodeImpl class
 * @author luca
 *
 */
public class NodeImpl implements Node {

	private Coordinate coordinate;
	private Colour colour;
	
	/**
	 * Instantiates a new Node
	 * @param coordinate
	 * @param colour
	 */
	public NodeImpl(final Coordinate coordinate, final Colour colour) {
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
