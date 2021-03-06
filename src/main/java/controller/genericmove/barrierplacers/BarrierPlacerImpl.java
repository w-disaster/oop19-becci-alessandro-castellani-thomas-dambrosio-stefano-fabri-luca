package controller.genericmove.barrierplacers;

import java.util.List;
import java.util.Iterator;
import controller.genericmove.GenericMove;
import controller.observers.Observer;
import controller.observers.ObserverBarrierPosition;
import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.RoundPlayers;
import view.game.ViewLogic;

public class BarrierPlacerImpl<X extends RoundEnvironment> extends GenericMove<X> implements BarrierPlacer {

	private final Model<X> model;
	private final ViewLogic view;
	private final Observer observerBarrier;
	private final RoundPlayers players;
	private final RoundBarriers barriers;
	private Coordinate newBarrierPosition;
	private Orientation newBarrierOrientation;

	public BarrierPlacerImpl(final Model<X> model, final ViewLogic view, final Iterator<X> iterRounds) {
		super(model, view, iterRounds);
		this.model = model;
		this.view = view;
		this.observerBarrier = new ObserverBarrierPosition(this.view);
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
	}
	
	@Override
    public final void placeBarrier(final Coordinate position, final Orientation type) {
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
	
	/**
	 * @param position
	 * @param type
	 * @return true if all checks return true
	 */
	private boolean checkPlacement(final Coordinate position, final Orientation type) {
		return this.isEmptyPosition(position, type) && this.enoughBarriers() && this.checkEdge(position) && this.noStall(position, type) ? true : false;
	}

	/**
	 * @param newPosition
	 * @param type
	 * @return true if the player wants to place the barrier in an empty position
	 */
	private boolean isEmptyPosition(final Coordinate newPosition, final Orientation type) {
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
	
	/**
	 * @param newPosition
	 * @param type
	 * @return true if the next position (based on vertical/horizontal barriers) is empty
	 */
	private boolean checkEmptyNextPosition(final Coordinate newPosition, final Orientation type) {
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
	
	/**
	 * @return true if the player have barriers to place
	 */
	private boolean enoughBarriers() {
		return this.players.getCurrentPlayer().getAvailableBarriers() > 0 ? true : false;
	}
	
	/**
	 * @param newPosition
	 * @return true if the barrier isn't placed on an edge
	 */
	private boolean checkEdge(final Coordinate newPosition) {
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
	
	/**
	 * @return true if the player have at least a path to follow to win
	 */
	private boolean noStall(final Coordinate position, final Orientation type) {
		if (type.equals(Orientation.HORIZONTAL)) {
			if (this.barriers.getBarriersAsGraph().containsPath(this.barriers.getBarriersAsGraph().barriersAsEdgesToRemove(List.of(new BarrierImpl(position, type, Piece.HEAD), 
					new BarrierImpl(new Coordinate(position.getX() + 1, position.getY()), type, Piece.TAIL))), this.players.getCurrentPlayer().getCoordinate(), this.players.getCurrentPlayer().getFinishLine())) {
				if (this.barriers.getBarriersAsGraph().containsPath(this.barriers.getBarriersAsGraph().barriersAsEdgesToRemove(List.of(new BarrierImpl(position, type, Piece.HEAD), 
						new BarrierImpl(new Coordinate(position.getX() + 1, position.getY()), type, Piece.TAIL))), this.getOtherPlayer(this.players.getCurrentPlayer()).get().getCoordinate(), this.getOtherPlayer(this.players.getCurrentPlayer()).get().getFinishLine())) {
					return true;
				}
			}
			return false;
		} else {
			if (this.barriers.getBarriersAsGraph().containsPath(this.barriers.getBarriersAsGraph().barriersAsEdgesToRemove(List.of(new BarrierImpl(position, type, Piece.HEAD), 
					new BarrierImpl(new Coordinate(position.getX(), position.getY() + 1), type, Piece.TAIL))), this.players.getCurrentPlayer().getCoordinate(), this.players.getCurrentPlayer().getFinishLine())) {
				if (this.barriers.getBarriersAsGraph().containsPath(this.barriers.getBarriersAsGraph().barriersAsEdgesToRemove(List.of(new BarrierImpl(position, type, Piece.HEAD), 
						new BarrierImpl(new Coordinate(position.getX(), position.getY() + 1), type, Piece.TAIL))), this.getOtherPlayer(this.players.getCurrentPlayer()).get().getCoordinate(), this.getOtherPlayer(this.players.getCurrentPlayer()).get().getFinishLine())) {
					return true;
				}
			}
			return false;
		}
	}
}
