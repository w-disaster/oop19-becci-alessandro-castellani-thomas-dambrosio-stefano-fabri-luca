package controller.genericmove.playermovers;

import java.util.*;

import controller.genericmove.GenericMoveImpl;
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

public class PlayerMoverImpl<X extends RoundEnvironment> extends GenericMoveImpl<X> implements PlayerMover {

	private Model<X> model;
	private UIController view;
	private Observer observerPlayer;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate playerPosition;
	private Coordinate newPosition;
	
	public PlayerMoverImpl(Model<X> model, UIController view, Iterator<X> iterRounds) {
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
			this.changePosition(this.players.getCurrentPlayer());
			this.players.getCurrentPlayer().setCoordinate(this.newPosition);
			this.observerPlayer.update(this.newPosition, this.players.getCurrentPlayer().getNickname()); //update view
			if (this.players.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println(this.players.getCurrentPlayer().getNickname() + " won the round!");
				this.model.getWinners().add(this.players.getCurrentPlayer()); //add the winner of the round
				this.changeRound();
			}
			this.changeTurn();
			this.view.changeSelectedLabel(this.players.getCurrentPlayer().getNickname());
		} else {
			System.out.println("Bad move! Still your turn!");
		}
	}

	protected List<Coordinate> checkMove(Coordinate playerPosition) {
		List<Coordinate> moves = new ArrayList<>();
		List<Coordinate> movesJump = new ArrayList<>();
		//first i find where can the player normally go (adj and no walls)
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Coordinate testCoord = new Coordinate(x,y);
				if (this.adjacent(playerPosition, testCoord)) {
					moves.add(testCoord);
				}
			}
		}
		for (Coordinate c : List.copyOf(moves)) {
			if (!this.noWall(playerPosition, c)) {
				moves.remove(moves.indexOf(c));
			}
		}
		//if he can jump i find the new positions
		if (this.canJump(moves)) {
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					Coordinate testCoord = new Coordinate(x,y);
					if (this.adjacent(this.getOtherPlayer().get().getCoordinate(), testCoord)) {
						movesJump.add(testCoord);
					}
				}
			}
			//before removing the positions where there are walls i will remove the other player position and find the two "side jump" positions
			this.getEmptyPositions(movesJump);
			List<Coordinate> sideJumps = new ArrayList<>();
			if (playerPosition.getX().equals(Model.BOARD_DIMENSION - 1) || playerPosition.getX().equals(0)) { //if i am on the edges
				for (Coordinate c : movesJump) {
					if (!c.getX().equals(0) && !c.getX().equals(Model.BOARD_DIMENSION - 1)) {
						sideJumps.add(c);
					}
				}
			} else {
				for (Coordinate c1 : movesJump) {
					for (Coordinate c2 : movesJump) {
						if (!c1.equals(c2)) {
							if ((c1.getX().equals(c2.getX())) || (c1.getY().equals(c2.getY()))) {
								sideJumps.add(c1);
								sideJumps.add(c2);
								break;
							}
						}
					}
				}
			}
			//need to find which position is the straight jump
			Optional<Coordinate> straightJump = Optional.empty();
			for (Coordinate c : movesJump) {
				if (!sideJumps.contains(c)) {
					straightJump = Optional.of(c);
				}
			}
			//removing the position where there's a wall
			for (Coordinate c : List.copyOf(movesJump)) {
				if (!this.noWall(this.getOtherPlayer().get().getCoordinate(), c)) {
					movesJump.remove(movesJump.indexOf(c));
				}
			}
			//if the normal jump position is still present i have to disable "side jump" positions
			if (!straightJump.equals(Optional.empty())) {
				if (movesJump.contains(straightJump.get())) {
					for (Coordinate c : List.copyOf(movesJump)) {
						if (sideJumps.contains(c)) {
							movesJump.remove(c);
						}
					}
				}
			} else {
				for (Coordinate c : List.copyOf(movesJump)) {
					if (sideJumps.contains(c)) {
						movesJump.remove(c);
					}
				}
			}
		}
		this.getEmptyPositions(moves); //removing other player position
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
	
	private boolean canJump(List<Coordinate> positions) {
		return positions.contains(this.getOtherPlayer().get().getCoordinate());
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
	
	private void changePosition(Player currentPlayer) {
		for (Player p : this.players.getPlayers()) {
			if (currentPlayer.getNickname().compareTo(p.getNickname()) == 0) {
				p.setCoordinate(this.newPosition);
			}
		}
	}
}