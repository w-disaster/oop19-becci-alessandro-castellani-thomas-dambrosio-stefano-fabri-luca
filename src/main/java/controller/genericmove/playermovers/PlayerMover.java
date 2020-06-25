package controller.genericmove.playermovers;

import model.roundenvironment.coordinate.Coordinate;

/**
 * This class allows players to move on the board.
 * 
 */

public interface PlayerMover {

    void movePlayer(Coordinate position);

}
