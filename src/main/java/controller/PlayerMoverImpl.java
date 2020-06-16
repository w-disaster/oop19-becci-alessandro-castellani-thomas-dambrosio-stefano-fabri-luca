package controller;

import java.util.*;
import controllers.UIController;
import model.*;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
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
	private Direction direction;
	
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
		if (this.adjacent() && this.noWall()) {
			//System.out.println(this.players.getCurrentPlayer().getNickname() + " position is: " + this.players.getCurrentPlayer().getCoordinate());
			//System.out.println("Moving to position " + newPosition);
			/*
			if (this.canJump()) {
				switch(this.direction) {
				case RIGHT:
					this.newPosition.setX(this.newPosition.getX() + 1);
					break;
				case LEFT:
					this.newPosition.setX(this.newPosition.getX() - 1);
					break;
				case UP:
					this.newPosition.setX(this.newPosition.getY() + 1);
					break;
				case DOWN:
					this.newPosition.setX(this.newPosition.getY() - 1);
					break;
				}
			}
			*/
			if (this.noWall()) {
				this.playerPosition = newPosition;
				this.players.getCurrentPlayer().setCoordinate(this.playerPosition);
				this.observerPlayer.update(this.playerPosition, this.players.getCurrentPlayer().getNickname()); //update view
				if (this.players.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
					System.out.println(this.players.getCurrentPlayer().getNickname() + " won the round!");
					this.addWinner((this.players.getCurrentPlayer())); //add the winner of the round
					this.changeRound();
				}
				this.changeTurn(this.players.getCurrentPlayer());
				this.view.changeSelectedLabel(this.players.getCurrentPlayer().getNickname());
				this.playerPosition = this.players.getCurrentPlayer().getCoordinate();
			}
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
		if (this.newPosition.getX() > this.playerPosition.getX()) {
			this.direction = Direction.RIGHT;
			if (this.containsBarrierTypeIndipendent(this.barriers, this.playerPosition, Orientation.VERTICAL)) {
				return false;
			}
		}
		if (this.newPosition.getX() < this.playerPosition.getX()) {
			this.direction = Direction.LEFT;
			if (this.containsBarrierTypeIndipendent(this.barriers, this.newPosition, Orientation.VERTICAL)) { 
				return false;
			}
		}
		if (this.newPosition.getY() > this.playerPosition.getY()) {
			this.direction = Direction.UP;
			if (this.containsBarrierTypeIndipendent(this.barriers, this.playerPosition, Orientation.HORIZONTAL)) {
				return false;
			}
		}
		if (this.newPosition.getY() < this.playerPosition.getY()) {
			this.direction = Direction.DOWN;
			if (this.containsBarrierTypeIndipendent(this.barriers, this.newPosition, Orientation.HORIZONTAL)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean canJump() {
		//new position must be empty
		for (Player p : this.players.getPlayers()) {
			if (!p.equals(this.players.getCurrentPlayer()) && p.getCoordinate().equals(this.newPosition)) {
				System.out.println("You are going on a player! Let's jump!!!");
				return true;
			}
		}
		return false;
	}
}
