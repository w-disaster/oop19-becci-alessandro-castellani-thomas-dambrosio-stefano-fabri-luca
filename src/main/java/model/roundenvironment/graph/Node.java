package model.roundenvironment.graph;

import model.roundenvironment.coordinate.Coordinate;

/**
 * Node to compute BFS
 * @author luca
 *
 */
public interface Node {
	
	/**
	 * The Enum Colour.
	 */
	public enum Colour {
		WHITE, GRAY, BLACK
	}

	/**
	 * Gets the coordinate.
	 *
	 * @return the coordinate
	 */
	Coordinate getCoordinate();	
	
	/**
	 * Sets the colour.
	 *
	 * @param colour the new colour
	 */
	void setColour(Colour colour);
	
	/**
	 * Gets the colour.
	 *
	 * @return the colour
	 */
	Colour getcolour();
	
}
