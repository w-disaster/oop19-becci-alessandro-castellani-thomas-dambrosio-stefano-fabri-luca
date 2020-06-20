package controller.playerMovers;

import java.util.*;

import controller.GenericMoveImpl;
import controller.observers.Observer;
import controller.observers.ObserverPlayerPosition;
import controllers.UIController;
import model.*;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class PlayerMoverImpl extends GenericMoveImpl implements PlayerMover {

	private Model<RoundEnvironment> model;
	private UIController view;
	private Observer observerPlayer;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate playerPosition;
	private Coordinate newPosition;
	
	public PlayerMoverImpl(Model<RoundEnvironment> model, UIController view, Iterator<RoundEnvironment> iterRounds) {
		super(model, view, iterRounds);
		this.model = model;
		this.view = view;
		this.observerPlayer = new ObserverPlayerPosition(this.view);
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
	}
	
	@Override
	public void movePlayer(Coordinate clickedPosition) {
		this.playerPosition = this.players.getCurrentPlayer().getCoordinate();
		//this.newPosition will be the final position (it may change while clickedPosition will remain the clicked position)
		this.newPosition = new Coordinate(clickedPosition.getX(), clickedPosition.getY());
		if (this.checkMove(this.playerPosition).contains(clickedPosition)) {
			this.players.getCurrentPlayer().setCoordinate(this.newPosition);
			this.observerPlayer.update(this.newPosition, this.players.getCurrentPlayer().getNickname()); //update view
			if (this.players.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println(this.players.getCurrentPlayer().getNickname() + " won the round!");
				this.model.getWinners().add(((this.players.getCurrentPlayer()))); //add the winner of the round
				this.changeRound();
			}
			this.changeTurn(this.players.getCurrentPlayer());
			this.view.changeSelectedLabel(this.players.getCurrentPlayer().getNickname());
		} else {
			System.out.println("Bad move! Still your turn!");
		}
	}
	
	protected List<Coordinate> checkMove(Coordinate playerPosition) {
		List<Coordinate> moves = new ArrayList<>();
		List<Coordinate> movesJump = new ArrayList<>();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Coordinate testCoord = new Coordinate(x,y);
				if (this.adjacent(playerPosition, testCoord)) {
					moves.add(testCoord);
				}
				if (this.canJump()) {
					if (this.adjacent(this.getOtherPlayer().get().getCoordinate(), testCoord)) {
						movesJump.add(testCoord);
					}
				}
			}
		}
		this.getEmptyPositions(moves);
		this.getEmptyPositions(movesJump);
		for (Coordinate c : List.copyOf(moves)) {
			if (!this.noWall(playerPosition, c)) {
				moves.remove(moves.indexOf(c));
			}
		}
		if (this.canJump()) {
			for (Coordinate c : List.copyOf(movesJump)) {
				if (!this.noWall(this.getOtherPlayer().get().getCoordinate(), c)) {
					movesJump.remove(movesJump.indexOf(c));
				}
			}
		}
		moves.addAll(movesJump);
		return moves;
	}

	private boolean adjacent(Coordinate coord1, Coordinate coord2) {
		if (Math.abs(coord1.getX() - coord2.getX()) + Math.abs(coord1.getY() - coord2.getY()) == 1) {
			return true;
		}
		return false;
	}
	
	private boolean noWall(Coordinate startPosition, Coordinate destPosition) {
		//i need to find in which direction the player wants to move in order to check if there's a wall
		if (destPosition.getX() > startPosition.getX()) {
			if (this.containsBarrierTypeIndipendent(this.barriers, startPosition, Orientation.VERTICAL)) {
				return false;
			}
		}
		if (destPosition.getX() < startPosition.getX()) {
			if (this.containsBarrierTypeIndipendent(this.barriers, destPosition, Orientation.VERTICAL)) { 
				return false;
			}
		}
		if (destPosition.getY() > startPosition.getY()) {
			if (this.containsBarrierTypeIndipendent(this.barriers, startPosition, Orientation.HORIZONTAL)) {
				return false;
			}
		}
		if (destPosition.getY() < startPosition.getY()) {
			if (this.containsBarrierTypeIndipendent(this.barriers, destPosition, Orientation.HORIZONTAL)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean canJump() {
		return this.adjacent(this.players.getPlayers().get(0).getCoordinate(), this.players.getPlayers().get(1).getCoordinate());
	}
	
	private Optional<Player> getOtherPlayer() {
		for (Player p : this.players.getPlayers()) {
			if (!p.equals(this.players.getCurrentPlayer())) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
	
	private void getEmptyPositions(List<Coordinate> coords) {
		for (Coordinate c : List.copyOf(coords)) {
			if (c.equals(this.players.getPlayers().get(0).getCoordinate()) || c.equals(this.players.getPlayers().get(1).getCoordinate())) {
				coords.remove(coords.indexOf(c));
			}
		}
	}
}
