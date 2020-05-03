package controller;

import model.*;
import model.Barrier.BarrierType;

/**
 * this class allow to place barriers on the board
 * 
 * @author Thomas
 */

public interface BarrierPlacer {

	public void placeBarrier(Coordinate position, BarrierType type);
	
}
