package controller;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

/**
 * controller class, it contains basic methods (except newGame, different implementation for each controller)
 * 
 * @author Thomas
 */
public interface GameController {

	public void invokeMove(Coordinate position);
	
	public void invokePlace(Coordinate position, Orientation orientation);
	
	public void nextRound();
	
	public void saveGame();
	
	public void loadGame();
}
