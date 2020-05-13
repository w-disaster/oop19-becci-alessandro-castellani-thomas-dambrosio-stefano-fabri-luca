package controller;

import java.util.*;

import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.Barrier.BarrierType;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class BarrierPlacerImpl extends MoveImpl implements BarrierPlacer {

	private Model<RoundEnvironment> model;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate newBarrierPosition;
	private BarrierType newBarrierType;

	public BarrierPlacerImpl(Model<RoundEnvironment> model, List<Player> turns) {
		super(model, turns);
		this.model = model;
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
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
		return this.players.getCurrentPlayer().getAvailableBarriers() > 0 ? true : false;
	}
	
	private boolean noStall() {
		return true; //need implementation
	}
	
}
