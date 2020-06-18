package controller;

import java.util.*;

import controllers.MenuController;
import controllers.UIController;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
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
	private Iterator<RoundEnvironment> iterRounds;
	private BarrierPlacer placer;
	private PlayerMover mover;
	
	public StandardGameControllerImpl(UIController view) {
		this.view = view;
	}

	public void newStandardGame(String nicknamePlayer1, String nicknamePlayer2) {
		Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
		this.view.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.model = new ModelFactoryImpl().buildFromScratch(nicknamePlayer1, nicknamePlayer2);
		this.iterRounds = this.model.getGameRoundEnvironments().iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setting current round (first)
		this.mover = new PlayerMoverImpl(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl(this.model, this.view, this.iterRounds);
	}
	
	@Override
	public void movePlayer(Coordinate position) {
		this.mover.movePlayer(position);
	}

	@Override
	public void placeBarrier(Coordinate position, Orientation orientation) {
		this.placer.placeBarrier(position, orientation);
	}
	
	public void nextRound() {
		RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		Player player1 = players.getPlayers().get(0);
		Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverImpl(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl(this.model, this.view, this.iterRounds);
		this.view.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid
	}
	
	public void saveGame() {
		this.saving = new SaveGame(this.model, this.iterRounds);
		this.saving.save();
	}
	
	public void loadGame() {
		/*if (MenuController.to_load) {
			this.loading = new LoadGame();
			this.model = this.loading.getResource().getModel();
			this.iterRounds = this.loading.getResource().getRoundIterator();
		} else {
			System.out.println("There isn't a saved game!");
		}
		RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		Player player1 = players.getPlayers().get(0);
		Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverImpl(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl(this.model, this.view, this.iterRounds);
		this.view.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid*/
	}
	
	/**
	 * using it only for testing
	 * 
	 * @return the current model
	 */
	
	/*
	public Model<RoundEnvironment> getModel() {
		return this.model;
	}
	*/
}