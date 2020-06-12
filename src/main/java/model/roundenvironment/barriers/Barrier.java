package model.roundenvironment.barriers;

import model.roundenvironment.coordinate.Coordinate;

/**
 * This interface describes the single barrier entity.
 * @author luca
 *
 */
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
	 * piece of the barrier
	 * @author luca
	 *
	 */
	public enum Piece {
		HEAD, TAIL
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
	
	/**
	 * 
	 * @return the piece of this barrier
	 */
	Piece getPiece();
	
}

