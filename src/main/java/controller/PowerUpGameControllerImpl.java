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
import savings.LoadGame;
import savings.LoadGamePUp;
import savings.SaveGame;
import savings.SaveGamePUp;
import view.game.ViewLogic;
import view.menu.MenuController;
import view.menu.MenuController.GameStatus;

public class PowerUpGameControllerImpl extends StandardGameControllerImpl implements PowerUpGameController {
	
	private Model<RoundPUpEnvironment> powerUpModel;
	private ViewLogic powerUpView;
	private Iterator<RoundPUpEnvironment> iterRounds;
	private BarrierPlacerPowerUp<RoundPUpEnvironment> placer;
	private PlayerMoverPowerUp<RoundPUpEnvironment> mover;
	private Optional<Pair<Player, PowerUp>> powerUpPlayer;

	public PowerUpGameControllerImpl(ViewLogic view) {
		super(view);
		this.powerUpView = view;
	}

	public void newPowerUpGame(String nicknamePlayer1, String nicknamePlayer2) {
		Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
		this.powerUpModel = new ModelFactoryImpl().buildWithPowerUps(nicknamePlayer1, nicknamePlayer2);
		this.powerUpView.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.powerUpView.drawPowerUps(this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList());
		this.iterRounds = this.powerUpModel.getGameRoundEnvironments().iterator();
		this.powerUpModel.setCurrentRoundEnvironment(this.iterRounds.next());
		this.mover = new PlayerMoverPowerUp<>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp<>(this.powerUpModel, this.powerUpView, this.iterRounds);
	}
	
	@Override
	public void movePlayer(Coordinate position) {
		for (PowerUp p : this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList()) {
			if (p.getCoordinate().equals(position)) {
				this.powerUpPlayer = Optional.of(new Pair<>(this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers()
						.getCurrentPlayer(), p));
				System.out.println(this.powerUpPlayer.get());
				break;
			} else {
				this.powerUpPlayer = Optional.empty();
			}
		}
		this.mover.movePlayer(position);
		if (this.powerUpPlayer.isPresent()) {
			if (this.powerUpPlayer.get().getKey().getCoordinate().equals(position)) {
				switch (this.powerUpPlayer.get().getValue().getType()) {
				case PLUS_ONE_MOVE:
					this.mover.doubleMove();
					this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().remove(this.powerUpPlayer.get().getValue());
					this.powerUpView.changeSelectedLabel(this.powerUpPlayer.get().getKey().getNickname());
					this.powerUpView.deletePowerUp(this.powerUpPlayer.get().getValue());
					break;
				case PLUS_ONE_BARRIER:
					this.placer.plusOneBarrier(this.powerUpPlayer.get().getKey());
					this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().remove(this.powerUpPlayer.get().getValue());
					this.powerUpView.updateBarriersNumber(this.powerUpPlayer.get().getKey().getNickname(), 
							this.powerUpPlayer.get().getKey().getAvailableBarriers());
					this.powerUpView.deletePowerUp(this.powerUpPlayer.get().getValue());
					break;
				default:
					break;
				}
			}
		}	
	}
	
	@Override
	public void placeBarrier(Coordinate position, Orientation orientation) {
		this.placer.placeBarrier(position, orientation);
	}
	
	public void nextRound() {
		RoundPlayers players = this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers();
		Player player1 = players.getPlayers().get(0);
		Player player2 = players.getPlayers().get(1);
		this.mover = new PlayerMoverPowerUp<RoundPUpEnvironment>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp<RoundPUpEnvironment>(this.powerUpModel, this.powerUpView, this.iterRounds);
		this.powerUpView.changeSelectedLabel(players.getCurrentPlayer().getNickname());
		this.powerUpView.setupGrid(player1.getCoordinate(), player2.getCoordinate(), player1.getAvailableBarriers(), player2.getAvailableBarriers()); //reset grid
		this.powerUpView.drawPowerUps(this.powerUpModel.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList());
	}
	
	public void saveGame() {
		SaveGame saving = new SaveGamePUp(this.powerUpModel);
		saving.save();
	}
	
	public void loadGame() {
		if (MenuController.gameStatus.equals(GameStatus.LOADPOWERUP)) {
			LoadGame<RoundPUpEnvironment> loading = new LoadGamePUp();
			this.powerUpModel = loading.getModel();
			this.iterRounds = loading.getIterator();
		} else {
			System.out.println("There isn't a saved game!");
		}
		RoundPlayers players = this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers();
		Player player1 = players.getPlayers().get(0);
		Player player2 = players.getPlayers().get(1);
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

