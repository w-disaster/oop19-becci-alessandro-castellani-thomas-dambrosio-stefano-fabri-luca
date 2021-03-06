package controller.genericmove.playermovers;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import controller.genericmove.GenericMove;
import controller.observers.Observer;
import controller.observers.ObserverPlayerPosition;
import model.Model;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import view.game.ViewLogic;

public class PlayerMoverImpl<X extends RoundEnvironment> extends GenericMove<X> implements PlayerMover {

	private final Model<X> model;
	private final ViewLogic view;
	private final Observer observerPlayer;
	private final RoundPlayers players;
	private final RoundBarriers barriers;
	private Coordinate playerPosition;
	private Coordinate newPosition;
	
	public PlayerMoverImpl(final Model<X> model, final ViewLogic view, final Iterator<X> iterRounds) {
		super(model, view, iterRounds);
		this.model = model;
		this.view = view;
		this.observerPlayer = new ObserverPlayerPosition(this.view);
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
	}
	
	@Override
    public final void movePlayer(final Coordinate clickedPosition) {
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

	/**
	 * @param playerPosition
	 * @return list of positions where the player can move
	 */
	private List<Coordinate> checkMove(final Coordinate playerPosition) {
		final List<Coordinate> moves = new ArrayList<>();
		final List<Coordinate> movesJump = new ArrayList<>();
		//first i find where can the player normally go (adj and no walls)
		for (int y = 0; y < Model.BOARD_DIMENSION; y++) {
			for (int x = 0; x < Model.BOARD_DIMENSION; x++) {
				final Coordinate testCoord = new Coordinate(x, y);
				if (this.adjacent(playerPosition, testCoord)) {
					moves.add(testCoord);
				}
			}
		}
		for (final Coordinate c : List.copyOf(moves)) {
			if (!this.noWall(playerPosition, c)) {
				moves.remove(moves.indexOf(c));
			}
		}
		//if he can jump i find the new positions
		if (this.canJump(moves)) {
			for (int y = 0; y < Model.BOARD_DIMENSION; y++) {
				for (int x = 0; x < Model.BOARD_DIMENSION; x++) {
					final Coordinate testCoord = new Coordinate(x, y);
					if (this.adjacent(this.getOtherPlayer(this.players.getCurrentPlayer()).get().getCoordinate(), testCoord)) {
						movesJump.add(testCoord);
					}
				}
			}
			//before removing the positions where there are walls i will remove the other player position and find the two "side jump" positions
			this.getEmptyPositions(movesJump);
			final List<Coordinate> sideJumps = new ArrayList<>();
			if (playerPosition.getX().equals(Model.BOARD_DIMENSION - 1) || playerPosition.getX().equals(0)) { //if i am on the edges
				for (final Coordinate c : movesJump) {
					if (!c.getX().equals(0) && !c.getX().equals(Model.BOARD_DIMENSION - 1)) {
						sideJumps.add(c);
					}
				}
			} else {
				for (final Coordinate c1 : movesJump) {
					for (final Coordinate c2 : movesJump) {
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
			for (final Coordinate c : movesJump) {
				if (!sideJumps.contains(c)) {
					straightJump = Optional.of(c);
				}
			}
			//removing the position where there's a wall
			for (final Coordinate c : List.copyOf(movesJump)) {
				if (!this.noWall(this.getOtherPlayer(this.players.getCurrentPlayer()).get().getCoordinate(), c)) {
					movesJump.remove(movesJump.indexOf(c));
				}
			}
			//if the normal jump position is still present i have to disable "side jump" positions
			if (!straightJump.equals(Optional.empty())) {
				if (movesJump.contains(straightJump.get())) {
					for (final Coordinate c : List.copyOf(movesJump)) {
						if (sideJumps.contains(c)) {
							movesJump.remove(c);
						}
					}
				}
			} else {
				this.getEmptyPositions(moves);
				if (!(moves.size() == 1)) { //if you can only move backwards you can side jump on the finish line
					for (final Coordinate c : List.copyOf(movesJump)) {
						if (sideJumps.contains(c)) {
							movesJump.remove(c);
						}
					}
				}
			}
		}
		this.getEmptyPositions(moves); //removing other player position
		moves.addAll(movesJump);
		return moves;
	}
	
	private boolean adjacent(final Coordinate coord1, final Coordinate coord2) {
		if (Math.abs(coord1.getX() - coord2.getX()) + Math.abs(coord1.getY() - coord2.getY()) == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param startPosition
	 * @param destPosition
	 * @return true if between the two coordinate there isn't a wall
	 */
	private boolean noWall(final Coordinate startPosition, final Coordinate destPosition) {
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
	
	/**
	 * @param positions
	 * @return true if the player can jump
	 */
	private boolean canJump(final List<Coordinate> positions) {
		return positions.contains(this.getOtherPlayer(this.players.getCurrentPlayer()).get().getCoordinate());
	}
	
	/**
	 * @param coords
	 * 
	 * This method removes the positions occupied by the other player in coords
	 */
	private void getEmptyPositions(final List<Coordinate> coords) {
		for (final Coordinate c : List.copyOf(coords)) {
			if (c.equals(this.players.getPlayers().get(0).getCoordinate()) || c.equals(this.players.getPlayers().get(1).getCoordinate())) {
				coords.remove(coords.indexOf(c));
			}
		}
	}
	
	/**
	 * @param currentPlayer
	 * 
	 * This method changes the position of the player that wants to move
	 */
	private void changePosition(final Player currentPlayer) {
		for (final Player p : this.players.getPlayers()) {
			if (currentPlayer.getNickname().compareTo(p.getNickname()) == 0) {
				p.setCoordinate(this.newPosition);
			}
		}
	}
}
