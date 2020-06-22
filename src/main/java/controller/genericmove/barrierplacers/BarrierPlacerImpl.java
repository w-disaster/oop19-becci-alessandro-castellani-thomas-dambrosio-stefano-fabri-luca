package controller.genericmove.barrierplacers;

import java.util.*;

import com.google.common.graph.*;

import controller.genericmove.GenericMoveImpl;
import controller.observers.Observer;
import controller.observers.ObserverBarrierPosition;
import controllers.UIController;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.RoundPlayers;

public class BarrierPlacerImpl<X extends RoundEnvironment> extends GenericMoveImpl<X> implements BarrierPlacer {

	private Model<X> model;
	private UIController view;
	private Observer observerBarrier;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate newBarrierPosition;
	private Orientation newBarrierOrientation;

	public BarrierPlacerImpl(Model<X> model, UIController view, Iterator<X> iterRounds) {
		super(model, view, iterRounds);
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
		if (this.checkPlacement(this.newBarrierPosition, this.newBarrierOrientation)) {
			this.players.getCurrentPlayer().setAvailableBarriers(this.players.getCurrentPlayer().getAvailableBarriers() - 1);
			this.view.updateBarriersNumber(this.players.getCurrentPlayer().getNickname(), this.players.getCurrentPlayer().getAvailableBarriers());
			//to place barriers long 2 positions i have to add 2 barriers
			if (type.equals(Orientation.HORIZONTAL)) {
				this.barriers.add(new BarrierImpl(position, type, Piece.HEAD));
				this.barriers.add(new BarrierImpl(new Coordinate(position.getX() + 1, position.getY()), type, Piece.TAIL));
				this.observerBarrier.update(new BarrierImpl(position, type, Piece.HEAD), this.players.getCurrentPlayer().getNickname());
				this.observerBarrier.update(new BarrierImpl(new Coordinate(position.getX() + 1, position.getY()), type, Piece.TAIL), this.players.getCurrentPlayer().getNickname());
			} else {
				this.barriers.add(new BarrierImpl(position, type, Piece.HEAD));
				this.barriers.add(new BarrierImpl(new Coordinate(position.getX(), position.getY() + 1), type, Piece.TAIL));
				this.observerBarrier.update(new BarrierImpl(position, type, Piece.HEAD), this.players.getCurrentPlayer().getNickname());
				this.observerBarrier.update(new BarrierImpl(new Coordinate(position.getX(), position.getY() + 1), type, Piece.TAIL), this.players.getCurrentPlayer().getNickname());
			}
			this.changeTurn();
			this.view.changeSelectedLabel(this.players.getCurrentPlayer().getNickname());
		} else {
			System.out.println("Bad move! Still your turn!");
		}
	}
	
	protected boolean checkPlacement(Coordinate position, Orientation type) {
		return this.isEmptyPosition(position, type) && this.enoughBarriers() && this.checkEdge(position) && this.noStall() ? true : false;
	}

	//this method also prevents placing of barriers over other barriers
	private boolean isEmptyPosition(Coordinate newPosition, Orientation type) {
		//barriers are memorized singularly so i need double checks to see if there's an empty position
		if (this.barriers.contains(new BarrierImpl(newPosition, Orientation.HORIZONTAL, Piece.HEAD))) {
			System.out.println("Not empty!!");
			return false;
		}
		if (this.barriers.contains(new BarrierImpl(newPosition, Orientation.VERTICAL, Piece.HEAD))) {
			System.out.println("Not empty!!");
			return false;
		}
		if (this.barriers.contains(new BarrierImpl(newPosition, Orientation.HORIZONTAL, Piece.TAIL))) {
			if (type.equals(Orientation.HORIZONTAL)) { //here i can place an opposite orientated barrier
				System.out.println("Not empty!!");
				return false;
			} else {
				if (!this.checkEmptyNextPosition(newPosition, type)) {
					return false;
				}
			}
		}
		if (this.barriers.contains(new BarrierImpl(newPosition, Orientation.VERTICAL, Piece.TAIL))) {
			if (type.equals(Orientation.VERTICAL)) { //here i can place an opposite orientated barrier
				System.out.println("Not empty!!");
				return false;
			} else {
				if (!this.checkEmptyNextPosition(newPosition, type)) {
					return false;
				}
			}
		}
		//more checks if the position is empty
		if (!this.checkEmptyNextPosition(newPosition, type)) {
			return false;
		}
		return true;
	}
	
	private boolean checkEmptyNextPosition(Coordinate newPosition, Orientation type) {
		if (this.barriers.contains(new BarrierImpl(new Coordinate(newPosition.getX() + 1, newPosition.getY()), Orientation.HORIZONTAL, Piece.HEAD))) {
			if (type.equals(Orientation.HORIZONTAL)) { //here i can place a horizontal barrier
				System.out.println("Not empty!!");
				return false;
			}
		}
		if (this.barriers.contains(new BarrierImpl(new Coordinate(newPosition.getX(), newPosition.getY() + 1), Orientation.VERTICAL, Piece.HEAD))) {
			if (type.equals(Orientation.VERTICAL)) { //here i can place a horizontal barrier
				System.out.println("Not empty!!");
				return false;
			}
		}
		return true;
	}
	
	private boolean enoughBarriers() {
		return this.players.getCurrentPlayer().getAvailableBarriers() > 0 ? true : false;
	}
	
	private boolean checkEdge(Coordinate newPosition) {
		//barriers are long 2 positions so they can't be placed where x or y are 'boardDimension'
		if (newPosition.getX().equals(this.model.getBoardDimension() - 1)) {
			System.out.println("Can't place on the edge!!");
			return false;
		}
		if (newPosition.getY().equals(this.model.getBoardDimension() - 1)) {
			System.out.println("Can't place on the edge!!");
			return false;
		}
		return true;
	}
	
	private boolean noStall() {
		/*
		Graph<Coordinate> graph = new Grap<>();
		*/
		return true; //need implementation
	}
}
