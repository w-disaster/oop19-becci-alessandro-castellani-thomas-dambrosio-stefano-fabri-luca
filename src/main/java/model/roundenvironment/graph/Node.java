package model.roundenvironment.graph;


import model.roundenvironment.coordinate.Coordinate;

public interface Node {
	
	public enum Colour {
		WHITE, GRAY, BLACK
	}

	Coordinate getCoordinate();	
	
	void setColour(Colour colour);
	
	Colour getcolour();
	
}
