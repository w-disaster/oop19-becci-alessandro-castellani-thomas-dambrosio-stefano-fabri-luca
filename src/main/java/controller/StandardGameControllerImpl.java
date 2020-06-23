package controller;

import java.util.*;

import controller.genericmove.barrierplacers.BarrierPlacer;
import controller.genericmove.barrierplacers.BarrierPlacerImpl;
import controller.genericmove.playermovers.PlayerMover;
import controller.genericmove.playermovers.PlayerMoverImpl;
import guicontrollers.MenuController;
import guicontrollers.UIController;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import savings.LoadGameImpl;
import savings.SaveGameImpl;

/**
 * controller for standard games
 * 
 * @author Thomas
 */
public class StandardGameControllerImpl implements StandardGameController {
	
	private Model<RoundEnvironment> model;
	private UIController view;
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
		this.model = new ModelFactoryImpl().buildStandard(nicknamePlayer1, nicknamePlayer2);
		this.iterRounds = this.model.getGameRoundEnvironments().iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setting current round (first)
		this.mover = new PlayerMoverImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
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
		this.mover = new PlayerMoverImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.view.changeSelectedLabel(players.getCurrentPlayer().getNickname());
		this.view.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid
	}
	
	public void saveGame() {
		SaveGameImpl saving = new SaveGameImpl(this.model, this.iterRounds);
		saving.save();
	}
	
	public void loadGame() {
		if (MenuController.to_load) {
			LoadGameImpl loading = new LoadGameImpl();
			this.model = loading.getModel();
			this.iterRounds = loading.getIterator();
		} else {
			System.out.println("There isn't a saved game!");
		}
		RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		Player player1 = players.getPlayers().get(0);
		Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.view.setNicknames(player1.getNickname(), player2.getNickname());
		this.view.changeSelectedLabel(players.getCurrentPlayer().getNickname());
		this.view.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers(), this.model.getCurrentRoundEnvironment().getRoundBarriers().getBarriersAsList()); //reset grid
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