package controller;

import model.*;
import model.Barriers.BarrierType;

public class StandardGameController implements BarrierPlacer, PlayerMover{

	private BarrierPlacer placer = new BarrierPlacerImpl();
	private PlayerMover mover = new PlayerMoverImpl();
	
	@Override
	public void movePlayer(Pair<Integer, Integer> position) {
		this.mover.movePlayer(position);
	}

	@Override
	public void placeBarrier(Pair<Integer, Integer> position, BarrierType type) {
		this.placer.placeBarrier(position, type);
	}
}