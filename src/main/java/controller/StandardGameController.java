package controller;

import controller.barrierPlacers.BarrierPlacer;
import controller.playerMovers.PlayerMover;

/**
 * class with void methods that view can invoke
 * 
 * @author Thomas
 */
public interface StandardGameController extends BarrierPlacer, PlayerMover {

	public void nextRound();
	
	public void saveGame();
	
	public void loadGame();
}
