package controller;

import java.util.*;

import controllers.UIController;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class BarrierPlacerImpl extends GenericMoveImpl implements BarrierPlacer {

	private Model<RoundEnvironment> model;
	private UIController view;
	private Observer observerBarrier;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate newBarrierPosition;
	private Orientation newBarrierOrientation;

	public BarrierPlacerImpl(Model<RoundEnvironment> model, UIController view, Iterator<RoundEnvironment> iterRounds, List<Player> roundWinner) {
		super(model, view, iterRounds, roundWinner);
		this.model = model;
		this.view = view;
		this.observerBarrier = new ObserverBarrierPosition(this.view);
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
	}
	
	@Override
	public void placeBarrier(Coordinate position, Orientation type) {
		this.newBarrierPosition = position;
		this.newBarrierOrientation = type;
		if (this.isEmptyPosition() && this.enoughBarriers() && this.checkPosition() && this.noStall()) {
			this.players.getCurrentPlayer().setAvailableBarriers(this.players.getCurrentPlayer().getAvailableBarriers() - 1);
			//to place barriers long 2 positions i have to add 2 barriers
			if (type.equals(Orientation.HORIZONTAL)) {
				this.barriers.add(new BarrierImpl(position, type));
				this.barriers.add(new BarrierImpl(new Coordinate(position.getX() + 1, position.getY()), type));
				this.observerBarrier.update(new BarrierImpl(position, type), this.players.getCurrentPlayer().getNickname());
				this.observerBarrier.update(new BarrierImpl(new Coordinate(position.getX() + 1, position.getY()), type), this.players.getCurrentPlayer().getNickname());
			} else {
				this.barriers.add(new BarrierImpl(position, type));
				this.barriers.add(new BarrierImpl(new Coordinate(position.getX(), position.getY() + 1), type));
				this.observerBarrier.update(new BarrierImpl(position, type), this.players.getCurrentPlayer().getNickname());
				this.observerBarrier.update(new BarrierImpl(new Coordinate(position.getX(), position.getY() + 1), type), this.players.getCurrentPlayer().getNickname());
			}
			this.changeTurn(this.players.getCurrentPlayer());
		} else {
			System.out.println("Bad move! Still your turn!");
		}
	}

	//this method also prevents placing of barriers over other barriers
	private boolean isEmptyPosition() {
		//barriers are memorized singularly so i need double checks to see if there's an empty position 
		if (this.barriers.contains(new BarrierImpl(this.newBarrierPosition, Orientation.HORIZONTAL))) { //se sono nel lato a destra (orizz) posso mettere la verticale
			if (this.barriers.contains(new BarrierImpl(new Coordinate(this.newBarrierPosition.getX() - 1, this.newBarrierPosition.getY()), Orientation.HORIZONTAL))) {
				if (this.newBarrierOrientation.equals(Orientation.HORIZONTAL)) { //here i can place a vertical barrier
					return false;
				}
			}
			if (this.barriers.contains(new BarrierImpl(new Coordinate(this.newBarrierPosition.getX() + 1, this.newBarrierPosition.getY()), Orientation.HORIZONTAL))) {
				return false;
			}
		}
		if (this.barriers.contains(new BarrierImpl(this.newBarrierPosition, Orientation.VERTICAL))) {
			if (this.barriers.contains(new BarrierImpl(new Coordinate(this.newBarrierPosition.getX(), this.newBarrierPosition.getY() - 1), Orientation.VERTICAL))) {
				if (this.newBarrierOrientation.equals(Orientation.VERTICAL)) { //here i can place an horizontal barrier
					return false;
				}
			}
			if (this.barriers.contains(new BarrierImpl(new Coordinate(this.newBarrierPosition.getX(), this.newBarrierPosition.getY() + 1), Orientation.VERTICAL))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean enoughBarriers() {
		return this.players.getCurrentPlayer().getAvailableBarriers() > 0 ? true : false;
	}
	
	private boolean checkPosition() {
		//barriers are long 2 positions so they can't be placed where x or y are 'boardDimension'
		if (this.newBarrierPosition.getX().equals(this.model.getBoardDimension() - 1)) {
			return false;
		}
		if (this.newBarrierPosition.getY().equals(this.model.getBoardDimension() - 1)) {
			return false;
		}
		return true;
	}
	
	private boolean noStall() {
		return true; //need implementation
	}
}
