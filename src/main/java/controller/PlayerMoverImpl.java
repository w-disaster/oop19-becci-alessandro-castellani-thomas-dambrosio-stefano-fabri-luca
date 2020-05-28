package controller;

import java.util.*;
import java.util.function.BinaryOperator;

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
	private Iterator<RoundEnvironment> iterRounds;
	private RoundPlayers players;
	private RoundBarriers barriers;
	private Coordinate playerPosition;
	private Coordinate newPosition;
	private List<Player> roundWinner;
	
	public PlayerMoverImpl(Model<RoundEnvironment> model, UIController view, List<Player> turns, Iterator<RoundEnvironment> iterRounds) {
		super(model, turns);
		this.model = model;
		this.view = view;
		this.iterRounds = iterRounds;
		this.observerPlayer = new ObserverPlayerPosition(this.view);
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.barriers = this.model.getCurrentRoundEnvironment().getRoundBarriers();
		this.roundWinner = new ArrayList<>();
		this.playerPosition = this.players.getCurrentPlayer().getCoordinate();
	}
	
	@Override
	public void movePlayer(Coordinate newPosition) {
		this.newPosition = newPosition; //so i don't need to pass to all private methods a parameter
		if (this.adjacent() && this.noWall()) { //aggiungere che la posizione sia vuota
			System.out.println("Moving to position " + newPosition);
			this.playerPosition = newPosition;
			this.players.getCurrentPlayer().setCoordinate(this.playerPosition);
			this.observerPlayer.update(this.playerPosition); //update view
			if (this.players.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println(this.players.getCurrentPlayer().getNickname() + " won the round!");
				this.roundWinner.add(this.players.getCurrentPlayer()); //add the winner of the round
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
			//i need to check if a player have already won so i don't pass to the next round
			int nWins = Collections.frequency(this.roundWinner, this.players.getCurrentPlayer());
			if (nWins > 1) { //sostituire 1 con size/2
				System.out.println("Game Over!" + this.players.getCurrentPlayer().getNickname() + " won!");
				return;
			}
			this.model.setCurrentRoundEnvironment(this.iterRounds.next());
		} else {
			System.out.println("All rounds finished, game end");
			//now i check who won more rounds and set him winner of the game
			Player p = this.roundWinner.stream().peek(Player::getNickname)
												.reduce(BinaryOperator.maxBy(
									                    Comparator.comparingInt(o -> Collections.frequency(this.roundWinner, o))))
									            .orElse(null);
			System.out.println("Game Over!" + p.getNickname() + " won!");
			//setto p come winner finale
		}
	}
	
}
