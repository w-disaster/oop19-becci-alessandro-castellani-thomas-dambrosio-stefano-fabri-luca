package controller;

import java.util.*;
import model.*;
import model.Barrier.BarrierType;

public class BarrierPlacerImpl extends MoveImpl implements BarrierPlacer {

	private StandardGame game;
	private GameBarriers barriers;
	private Coordinate newBarrierPosition;
	private BarrierType newBarrierType;

	public BarrierPlacerImpl(StandardGame game, GameBarriers barriers, List<StandardPlayer> turns) {
		super(game, turns);
		this.game = game;
		this.barriers = barriers;
	}
	
	@Override
	public void placeBarrier(Coordinate position, BarrierType type) {
		this.newBarrierPosition = position;
		this.newBarrierType = type;
		if (this.isEmptyPosition() && this.enoughBarriers() && this.noStall()) {
			//bisogna decrementare barriere
			this.barriers.add(new BarrierImpl(position, type));
			this.changeTurn();
		} else {
			System.out.println("Bad move! Still your turn!");
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
