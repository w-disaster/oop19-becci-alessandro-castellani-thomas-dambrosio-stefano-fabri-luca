package model.roundenvironment.powerups;

import model.roundenvironment.coordinate.Coordinate;

public interface PowerUp {
	
	public enum Type {
		CLIMB_A_BARRIER, PLUS_ONE_BARRIER, PLUS_ONE_MOVE
	}
	
	Type getType();
	
	Coordinate getCoordinate();
}
