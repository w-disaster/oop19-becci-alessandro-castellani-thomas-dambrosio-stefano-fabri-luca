package controller;

import java.util.Iterator;
import java.util.Optional;

import controller.genericmove.barrierplacers.BarrierPlacerPowerUp;
import controller.genericmove.playermovers.PlayerMoverPowerUp;
import javafx.util.Pair;
import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.powerups.PowerUp;
import savings.load.LoadGame;
import savings.load.LoadGameFactoryImpl;
import savings.save.SaveGame;
import savings.save.SaveGamePUp;
import view.game.ViewLogic;
import view.menu.MenuController;
import view.menu.MenuController.GameStatus;

/**
 * The implementation for PowerUpGameControllerImpl.
 * 
 * @author Stefano
 * 
 */
public class PowerUpGameControllerImpl extends StandardGameControllerImpl implements PowerUpGameController {
	
	private Model<RoundPUpEnvironment> powerUpModel;
	private ViewLogic powerUpView;
	private Iterator<RoundPUpEnvironment> iterRounds;
	private BarrierPlacerPowerUp<RoundPUpEnvironment> placer;
	private PlayerMoverPowerUp<RoundPUpEnvironment> mover;

	/**
	 * Instantiates a new powerUpGameController implementation.
	 *
	 * @param view the view controller
	 */
	public PowerUpGameControllerImpl(ViewLogic view) {
		super(view);
		this.powerUpView = view;
	}

	/**
	 * Starts a new powerUp game.
	 *
	 * @param nicknamePlayer1 the nickname for player 1
	 * @param nicknamePlayer2 the nickname for player 2
	 */
	public void newPowerUpGame(final String nicknamePlayer1, final String nicknamePlayer2) {
		final Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		final Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
		this.powerUpModel = new ModelFactoryImpl().buildWithPowerUps(nicknamePlayer1, nicknamePlayer2);
		this.powerUpView.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.powerUpView.drawPowerUps(this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList());
		this.iterRounds = this.powerUpModel.getGameRoundEnvironments().iterator();
		this.powerUpModel.setCurrentRoundEnvironment(this.iterRounds.next());
		this.mover = new PlayerMoverPowerUp<>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp<>(this.powerUpModel, this.powerUpView, this.iterRounds);
	}
	
	/**
	 * Make a player move in the clicked position, checks and eventually applies powerUps.
	 *
	 * @param position the position
	 */
	@Override
	public void invokeMove(final Coordinate position) {
		final Player movingPlayer = this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer();
		// Moves the player
		this.mover.movePlayer(position);
		// If the player moved correctly
		if (!movingPlayer.equals(this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer())) {
			// Check if it's present a powerUp
			for (final PowerUp p : this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList()) {
				if (p.getCoordinate().equals(position)) {
					this.applyPowerUp(movingPlayer, p);
					break;
				}
			}
		}	
	}
	
	/**
	 * Apply the given powerUp to the given player.
	 *
	 * @param player the player
	 * @param powerUp the powerUp
	 */
	private void applyPowerUp(Player player, PowerUp powerUp) {
		switch (powerUp.getType()) {
		case PLUS_ONE_MOVE:
			this.mover.doubleMove();
			this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().remove(powerUp);
			this.powerUpView.deletePowerUp(powerUp);
			this.powerUpView.changeSelectedLabel(player.getNickname());
			break;
		case PLUS_ONE_BARRIER:
			this.placer.plusOneBarrier(player);
			this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().remove(powerUp);
			this.powerUpView.deletePowerUp(powerUp);
			this.powerUpView.updateBarriersNumber(player.getNickname(), player.getAvailableBarriers());
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * Invoke placement of a barrier.
	 *
	 * @param position the position
	 * @param orientation the orientation
	 */
	@Override
	public void invokePlace(final Coordinate position, final Orientation orientation) {
		this.placer.placeBarrier(position, orientation);
	}
	
	/**
	 * Change environment to the next round.
	 */
	public void nextRound() {
		final RoundPlayers players = this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers();
		final Player player1 = players.getPlayers().get(0);
		final Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverPowerUp<RoundPUpEnvironment>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp<RoundPUpEnvironment>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.powerUpView.changeSelectedLabel(players.getCurrentPlayer().getNickname());
		this.powerUpView.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid
		this.powerUpView.drawPowerUps(this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList());
	}
	
	/**
	 * Saves the game.
	 */
	public void saveGame() {
		final SaveGame saving = new SaveGamePUp(this.powerUpModel);
		saving.save();
	}
	
	/**
	 * Loads the game.
	 */
	public void loadGame() {
		if (MenuController.gameStatus.equals(GameStatus.LOADPOWERUP)) {
			final LoadGame<RoundPUpEnvironment> loading = new LoadGameFactoryImpl().buildPowerup();
			this.powerUpModel = loading.getModel();
			this.iterRounds = loading.getIterator();
		} else {
			System.out.println("There isn't a saved game!");
		}
		final RoundPlayers players = this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers();
		final Player player1 = players.getPlayers().get(0);
		final Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverPowerUp<RoundPUpEnvironment>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp<RoundPUpEnvironment>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.powerUpView.setPlayer(Optional.of(new Pair<>(player1.getNickname(), player2.getNickname())));
		this.powerUpView.changeSelectedLabel(players.getCurrentPlayer().getNickname());
		this.powerUpView.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), 
				player2.getAvailableBarriers(), this.powerUpModel.getCurrentRoundEnvironment().
				getRoundBarriers().getBarriersAsList()); //reset grid
		this.powerUpView.drawPowerUps(this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList());
	}
}

