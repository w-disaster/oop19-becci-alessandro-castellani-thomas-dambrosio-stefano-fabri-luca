package controller.genericmove.barrierplacers;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

/**
 * this class allows players to place barriers on the board
 * 
 * @author Thomas
 */

public interface BarrierPlacer {

	public void placeBarrier(Coordinate position, Orientation orientation);
	
}
