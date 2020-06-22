package controller.genericmove.playermovers;

import model.roundenvironment.coordinate.Coordinate;

/**
 * this class allows players to move on the board
 * 
 * @author Thomas
 */

public interface PlayerMover {
	
	public void movePlayer(Coordinate position);
	
}
