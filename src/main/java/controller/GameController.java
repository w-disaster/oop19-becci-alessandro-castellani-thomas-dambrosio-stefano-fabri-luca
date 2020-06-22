package controller;

import controller.genericmove.barrierplacers.BarrierPlacer;
import controller.genericmove.playermovers.PlayerMover;

/**
 * class with void methods that view can invoke
 * 
 * @author Thomas
 */
public interface GameController extends BarrierPlacer, PlayerMover {

	public void nextRound();
	
	public void saveGame();
	
	public void loadGame();
}
