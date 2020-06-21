package model.roundenvironment.graph;

import java.util.Optional;

import model.roundenvironment.coordinate.Coordinate;

public interface Node {
	
	public enum Colour {
		WHITE, GRAY, BLACK
	}

	Coordinate getCoordinate();
	
	void setDistance(Optional<Integer> distance);
	
	Optional<Integer> getDistance();
	
	void setColour(Colour colour);
	
	Colour getcolour();
	
}
