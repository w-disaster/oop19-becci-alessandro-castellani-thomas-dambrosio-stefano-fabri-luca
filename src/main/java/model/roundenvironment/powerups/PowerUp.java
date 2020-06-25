package model.roundenvironment.powerups;

import model.roundenvironment.coordinate.Coordinate;

/**
 * This inteface models a powerUp object.
 * 
 * @author Stefano
 */
public interface PowerUp{
	
	/**
	 * An enum that contains all types of powerUps.
	 */
	enum Type {
		CLIMB_A_BARRIER, PLUS_ONE_BARRIER, PLUS_ONE_MOVE
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	Type getType();
	
	/**
	 * Gets the coordinate.
	 *
	 * @return the coordinate
	 */
	Coordinate getCoordinate();
}
