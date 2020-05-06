package model.roundenvironment.barriers;

import model.roundenvironment.coordinate.Coordinate;

public interface Barrier {

	/**
	 * type of the barriers
	 * @author luca
	 *
	 */
	public enum BarrierType {
		HORIZONTAL, VERTICAL
	}

	/**
	 * 
	 * @return the type of the barrier
	 */
	BarrierType getType();
	
	/**
	 * 
	 * @return barrier coordinate
	 */
	Coordinate getCoordinate();
}

