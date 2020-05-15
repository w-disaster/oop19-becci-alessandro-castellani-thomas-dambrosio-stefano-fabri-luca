package controller;

import java.util.*;

import model.*;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class PlayerMoverImpl extends MoveImpl implements PlayerMover {

	private Model<RoundEnvironment> model;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate playerPosition;
	private Coordinate newPosition;
	private List<RoundEnvironment> rounds;
	private Iterator<RoundEnvironment> iterRounds;
	
	public PlayerMoverImpl(Model<RoundEnvironment> model, List<Player> turns, List<RoundEnvironment> listEnvironment) {
		super(model, turns);
		this.model = model;
		this.rounds = listEnvironment;
		this.iterRounds = this.rounds.iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setto il round corrente
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
		this.playerPosition = this.players.getCurrentPlayer().getCoordinate();
	}
	
	@Override
	public void movePlayer(Coordinate newPosition) {
		this.newPosition = newPosition; //so i don't need to pass to all private methods a parameter
		if (this.adjacent() && this.noWall()) {
			this.playerPosition = newPosition;
			this.players.getCurrentPlayer().setCoordinate(this.playerPosition);
			if (this.players.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println("Game Over! " + this.players.getCurrentPlayer() + " won!");
				this.changeRound();
			}
			this.changeTurn();
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
	
	private void changeRound() {
		if (this.iterRounds.hasNext()) {
			this.model.setCurrentRoundEnvironment(this.iterRounds.next());
		} else {
			System.out.println("All rounds finished, game end");
		}
	}
}
