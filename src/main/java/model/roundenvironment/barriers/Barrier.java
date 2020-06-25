package model.roundenvironment.barriers;

import model.roundenvironment.coordinate.Coordinate;

/**
 * This interface describes the single barrier entity.
 * @author luca
 *
 */
public interface Barrier {

    /**
     * Orientation of the barrier.
     *
     * @author luca
     */
    public enum Orientation {
		HORIZONTAL, VERTICAL
	}
	
	/**
	 * Piece of the barrier.
	 *
	 * @author luca
	 */
	public enum Piece {
		HEAD, TAIL
	}

	/**
	 * Gets the orientation.
	 *
	 * @return the orientation of the barrier
	 */
	Orientation getOrientation();
	
	/**
	 * Gets the coordinate.
	 *
	 * @return coordinate that represents the head of the barrier
	 */
	Coordinate getCoordinate();
	
	/**
	 * Gets the piece.
	 *
	 * @return the piece of this barrier
	 */
	Piece getPiece();
	
}

