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
import savings.LoadGame;
import savings.SaveGame;

/**
 * controller for standard games
 * 
 * @author Thomas
 */
public class StandardGameControllerImpl implements BarrierPlacer, PlayerMover {
	
	private Model<RoundEnvironment> model;
	private UIController view;
	private SaveGame saving;
	private LoadGame loading;
	private List<RoundEnvironment> listEnvironment;
	private Iterator<RoundEnvironment> iterRounds;
	private List<Player> roundWinner;
	private BarrierPlacer placer;
	private PlayerMover mover;
	private boolean gameRunning = false;
	
	public StandardGameControllerImpl(UIController view) {
		this.view = view;
	}

	public void newStandardGame(String nicknamePlayer1, String nicknamePlayer2) {
		this.gameRunning = true;
		Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
		this.view.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.model = new ModelFactoryImpl().standardModel(nicknamePlayer1, nicknamePlayer2);
		this.iterRounds = this.listEnvironment.iterator();
		this.roundWinner = new ArrayList<>();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setting current round (first)
		this.mover = new PlayerMoverImpl(this.model, this.view, this.iterRounds, this.roundWinner);
		this.placer = new BarrierPlacerImpl(this.model, this.view, this.iterRounds, this.roundWinner);
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
		RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		Player player1 = players.getPlayers().get(0);
		Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverImpl(this.model, this.view, this.iterRounds, this.roundWinner);
		this.placer = new BarrierPlacerImpl(this.model, this.view, this.iterRounds, this.roundWinner);
		this.view.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid
	}
	
	public void saveGame() {
		this.saving = new SaveGame(this.model, this.view, this.iterRounds);
		this.saving.save();
	}
	
	public void loadGame() {
		this.gameRunning = true;
		this.loading = new LoadGame();
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