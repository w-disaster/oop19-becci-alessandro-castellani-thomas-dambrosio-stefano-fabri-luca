package controller.genericmove.barrierplacers;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

/**
 * This class allows players to place barriers on the board.
 * 
 */

public interface BarrierPlacer {

    void placeBarrier(Coordinate position, Orientation orientation);

}
