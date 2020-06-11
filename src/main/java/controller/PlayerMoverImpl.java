package controller;

import java.util.*;
import controllers.UIController;
import model.*;
import model.roundenvironment.barriers.BarrierImpl;
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
	
	public PlayerMoverImpl(Model<RoundEnvironment> model, UIController view, Iterator<RoundEnvironment> iterRounds, List<Player> roundWinner) {
		super(model, view, iterRounds, roundWinner);
		this.model = model;
		this.view = view;
		this.observerPlayer = new ObserverPlayerPosition(this.view);
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
	}
	
	@Override
	public void movePlayer(Coordinate newPosition) {
		this.playerPosition = this.players.getCurrentPlayer().getCoordinate();
		this.newPosition = newPosition; //so i don't need to pass to all private methods a parameter
		if (this.adjacent() && this.noWall() && this.isEmptyPosition()) {
			System.out.println(this.players.getCurrentPlayer().getNickname() + " position is: " + this.players.getCurrentPlayer().getCoordinate());
			System.out.println("Moving to position " + newPosition);
			this.playerPosition = newPosition;
			this.players.getCurrentPlayer().setCoordinate(this.playerPosition);
			this.observerPlayer.update(this.playerPosition, this.players.getCurrentPlayer().getNickname()); //update view
			if (this.players.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println(this.players.getCurrentPlayer().getNickname() + " won the round!");
				this.addWinner((this.players.getCurrentPlayer())); //add the winner of the round
				this.changeRound();
			}
			this.changeTurn(this.players.getCurrentPlayer());
			this.playerPosition = this.players.getCurrentPlayer().getCoordinate();
		} else {
			System.out.println("Bad move! Still your turn!");
		}
	}
	
	private boolean adjacent() {
		if (Math.abs(this.playerPosition.getX() - this.newPosition.getX()) + Math.abs(this.playerPosition.getY() - this.newPosition.getY()) == 1) {
			return true;
		}
		return false;
	}
	
	private boolean noWall() {
		//i need to find in which direction the player wants to move in order to check if there's a wall
		if (this.newPosition.getX().equals(this.playerPosition.getX() + 1)) {
			if (this.barriers.contains(new BarrierImpl(this.playerPosition, Orientation.VERTICAL))) {
				return false;
			}
		}
		if (this.newPosition.getX().equals(this.playerPosition.getX() - 1)) {
			if (this.barriers.contains(new BarrierImpl(this.newPosition, Orientation.VERTICAL))) {
				return false;
			}
		}
		if (this.newPosition.getY().equals(this.playerPosition.getY() + 1)) {
			if (this.barriers.contains(new BarrierImpl(this.playerPosition, Orientation.HORIZONTAL))) {
				return false;
			}
		}
		if (this.newPosition.getY().equals(this.playerPosition.getY() - 1)) {
			if (this.barriers.contains(new BarrierImpl(this.newPosition, Orientation.HORIZONTAL))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isEmptyPosition() {
		//new position must be empty
		for (Player p : this.players.getPlayers()) {
			if (!p.equals(this.players.getCurrentPlayer()) && p.getCoordinate().equals(this.newPosition)) {
				System.out.println("Can't get on a player!!");
				return false;
			}
		}
		return true;
	}
}
