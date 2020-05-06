package controller;

import model.*;
import model.roundenvironment.barriers.Barrier.BarrierType;
import model.roundenvironment.coordinate.Coordinate;

/**
 * this class allow to place barriers on the board
 * 
 * @author Thomas
 */

public interface BarrierPlacer {

	public void placeBarrier(Coordinate position, BarrierType type);
	
}
