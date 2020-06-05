package controller;

import java.util.*;

import controllers.UIController;
import model.*;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;

/**
 * controller for standard games
 * 
 * @author Thomas
 */
public class StandardGameControllerImpl implements BarrierPlacer, PlayerMover {
	
	private Model<RoundEnvironment> model;
	private UIController view;
	private List<RoundEnvironment> listEnvironment;
	private Iterator<RoundEnvironment> iterRounds;
	private BarrierPlacer placer;
	private PlayerMover mover;
	private boolean gameRunning = false;
	
	public StandardGameControllerImpl(UIController view) {
		this.view = view;
	}

	public void newStandardGame(String nicknamePlayer1, String nicknamePlayer2) {
		this.gameRunning = true;
		this.listEnvironment = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			List<Player> playersList = new ArrayList<>();
			Player player1 = new PlayerImpl(nicknamePlayer1, new Coordinate(4,0), Optional.of(10), 8);
			Player player2 = new PlayerImpl(nicknamePlayer2, new Coordinate(4,8), Optional.of(10), 0);
			playersList.add(player1);
			playersList.add(player2);
			RoundBarriers barriers = new RoundBarriersImpl();
			RoundPlayers players = new RoundPlayersImpl(playersList);
			RoundEnvironment environment = new RoundEnvironmentImpl(barriers,players);
			this.listEnvironment.add(environment);
		}
		this.view.setupGrid(new Coordinate(4,0), new Coordinate(4,8));
		this.model = new ModelImpl<>(this.listEnvironment, Optional.empty());
		this.iterRounds = this.listEnvironment.iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setting current round (first)
		List<Player> turns = this.model.getCurrentRoundEnvironment().getRoundPlayers().getPlayers();
		this.mover = new PlayerMoverImpl(this.model, this.view, turns, this.iterRounds);
		this.placer = new BarrierPlacerImpl(this.model, this.view, turns);
	}
	
	@Override
	public void movePlayer(Coordinate position) {
		if (this.gameRunning) {
			this.mover.movePlayer(position);
		} else {
			System.out.println("Game not started!");
		}
	}

	@Override
	public void placeBarrier(Coordinate position, Orientation orientation) {
		if (this.gameRunning) {
			this.placer.placeBarrier(position, orientation);
		} else {
			System.out.println("Game not started!");
		}
	}
	
	public void nextRound() {
		List<Player> turns = this.model.getCurrentRoundEnvironment().getRoundPlayers().getPlayers();
		this.mover = new PlayerMoverImpl(this.model, this.view, turns, this.iterRounds);
		this.placer = new BarrierPlacerImpl(this.model, this.view, turns);
	}
	
	/**
	 * using it only for testing
	 * 
	 * @return the current model
	 */
	public Model<RoundEnvironment> getModel() {
		return this.model;
	}
}