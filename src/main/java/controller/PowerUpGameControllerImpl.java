package controller;

import java.util.Iterator;
import java.util.Optional;

import controller.genericmove.barrierplacers.BarrierPlacerPowerUp;
import controller.genericmove.playermovers.PlayerMoverPowerUp;
import guicontrollers.UIController;
import javafx.util.Pair;
import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.powerups.PowerUp;

public class PowerUpGameControllerImpl extends StandardGameControllerImpl implements PowerUpGameController {
	
	private Model<RoundPUpEnvironment> powerUpModel;
	private UIController powerUpView;
	private Iterator<RoundPUpEnvironment> iterRounds;
	private BarrierPlacerPowerUp<RoundPUpEnvironment> placer;
	private PlayerMoverPowerUp<RoundPUpEnvironment> mover;
	private Optional<Pair<Player, PowerUp>> powerUpPlayer;

	public PowerUpGameControllerImpl(UIController view) {
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
				Pair<Player, PowerUp> temp = new Pair<>(this.powerUpModel.getCurrentRoundEnvironment().getRoundPlayers()
						.getCurrentPlayer(), p);
				this.powerUpPlayer = Optional.of(temp);
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
					break;
				case PLUS_ONE_BARRIER:
					this.placer.plusOneBarrier();
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
	
}

