package controller;

import java.util.Iterator;
import java.util.Optional;

import controller.genericmove.barrierplacers.BarrierPlacer;
import controller.genericmove.barrierplacers.BarrierPlacerImpl;
import controller.genericmove.playermovers.PlayerMover;
import controller.genericmove.playermovers.PlayerMoverImpl;
import javafx.util.Pair;
import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import savings.load.LoadGame;
import savings.load.LoadGameFactoryImpl;
import savings.save.SaveGame;
import savings.save.SaveGameImpl;
import view.game.ViewLogic;
import view.menu.MenuController;
import view.menu.MenuController.GameStatus;

public class StandardGameControllerImpl implements StandardGameController {
	
	private Model<RoundEnvironment> model;
	private final ViewLogic view;
	private Iterator<RoundEnvironment> iterRounds;
	private BarrierPlacer placer;
	private PlayerMover mover;
	
	public StandardGameControllerImpl(final ViewLogic view) {
		this.view = view;
	}

	public final void newStandardGame(final String nicknamePlayer1, final String nicknamePlayer2) {
		final Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION / 2, 0);
		final Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION / 2, Model.BOARD_DIMENSION - 1);
		this.view.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.model = new ModelFactoryImpl().buildStandard(nicknamePlayer1, nicknamePlayer2);
		this.iterRounds = this.model.getGameRoundEnvironments().iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setting current round (first)
		this.mover = new PlayerMoverImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
	}
	
	/**
	 * Invokes PlayerMover move.
	 */
	@Override
    public void invokeMove(final Coordinate position) {
		this.mover.movePlayer(position);
	}

	/**
	 * Invokes BarrierPlacer place.
	 */
	@Override
    public void invokePlace(final Coordinate position, final Orientation orientation) {
		this.placer.placeBarrier(position, orientation);
	}
	
	/**
	 * Invoke it to go to the next round.
	 */
	public void nextRound() {
		final RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		final Player player1 = players.getPlayers().get(0);
		final Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.view.changeSelectedLabel(players.getCurrentPlayer().getNickname());
		this.view.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid
	}
	
	/**
	 * Invoke it to save the game.
	 */
	public void saveGame() {
		final SaveGame saving = new SaveGameImpl<>(this.model);
		saving.save();
	}
	
	/**
	 * Invoke it to load a saved game.
	 */
	public void loadGame() {
		if (MenuController.getGameStatus().equals(GameStatus.LOADNORMAL)) {
			final LoadGame<RoundEnvironment> loading = new LoadGameFactoryImpl().buildNormal();
			this.model = loading.getModel();
			this.iterRounds = loading.getIterator();
		} else {
			System.out.println("There isn't a saved game!");
		}
		final RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		final Player player1 = players.getPlayers().get(0);
		final Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerImpl<RoundEnvironment>(this.model, this.view, this.iterRounds);
		this.view.setPlayer(Optional.of(new Pair<>(player1.getNickname(), player2.getNickname())));
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