package model.roundenvironment.barriers;

import model.roundenvironment.coordinate.Coordinate;

public interface Barrier {

	/**
	 * orientarion of the barrier
	 * @author luca
	 *
	 */
	public enum Orientation {
		HORIZONTAL, VERTICAL
	}

	/**
	 * 
	 * @return the orientation of the barrier
	 */
	Orientation getOrientation();
	
	/**
	 * 
	 * @return coordinate that represents the head of the barrier
	 */
	Coordinate getCoordinate();
}

