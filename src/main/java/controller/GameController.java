package controller;

import controller.genericmove.barrierplacers.BarrierPlacer;
import controller.genericmove.playermovers.PlayerMover;

/**
 * controller class, it contains basic methods (except newGame, different implementation for each controller)
 * 
 * @author Thomas
 */
public interface GameController extends BarrierPlacer, PlayerMover {

	public void nextRound();
	
	public void saveGame();
	
	public void loadGame();
}
