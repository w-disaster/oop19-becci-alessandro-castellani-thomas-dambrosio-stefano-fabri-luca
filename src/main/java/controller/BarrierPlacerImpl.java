package controller;

import model.*;
import model.Barrier.BarrierType;

public class BarrierPlacerImpl extends MoveImpl implements BarrierPlacer {

	private StandardGame game = new StandardGameImpl();
	private GameBarriers barriers = new GameBarriersImpl();
	private Coordinate newBarrierPosition;
	private BarrierType newBarrierType;

	@Override
	public void placeBarrier(Coordinate position, BarrierType type) {
		this.newBarrierPosition = position;
		this.newBarrierType = type;
		if (this.isEmptyPosition() && this.enoughBarriers() && this.noStall()) {
			//decrementare barriere
			this.barriers.add(new BarrierImpl(position, type));
		}
	}

	private boolean isEmptyPosition() {
		return !this.barriers.contains(new BarrierImpl(this.newBarrierPosition, this.newBarrierType)) ? true : false;
	}
	
	private boolean enoughBarriers() {
		return this.game.getCurrentPlayer().getAvailableBarriers() > 0 ? true : false;
	}
	
	private boolean noStall() {
		return true; //need implementation
	}
	
}
