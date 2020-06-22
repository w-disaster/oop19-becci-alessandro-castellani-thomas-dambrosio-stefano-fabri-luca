package controller;

import java.util.Iterator;
import controller.barrierPlacers.BarrierPlacerPowerUp;
import controller.playerMovers.PlayerMoverPowerUp;
import controllers.UIController;
import javafx.util.Pair;
import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.powerups.PowerUp;

public class PowerUpGameControllerImpl extends StandardGameControllerImpl implements PowerUpGameController {
	
	private Model<RoundPUpEnvironment> model;
	private UIController view;
	private Iterator<RoundPUpEnvironment> iterRounds;
	private BarrierPlacerPowerUp<RoundPUpEnvironment> placer;
	private PlayerMoverPowerUp<RoundPUpEnvironment> mover;
	private Pair<Player, PowerUp> powerUpPlayer;

	public PowerUpGameControllerImpl(UIController view) {
		super(view);
	}

	public void newPowerUpGame(String nicknamePlayer1, String nicknamePlayer2) {
		Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
		this.model = new ModelFactoryImpl().buildWithPowerUps(nicknamePlayer1, nicknamePlayer2);
		this.view.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.view.drawPowerUps(this.model.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList());
		this.iterRounds = this.model.getGameRoundEnvironments().iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next());
		this.mover = new PlayerMoverPowerUp<>(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp<>(this.model, this.view, this.iterRounds);
	}
	
	@Override
	public void movePlayer(Coordinate position) {
		for (PowerUp p : this.model.getCurrentRoundEnvironment().getRoundPowerUps().getPowerUpsAsList()) {
			if (p.getCoordinate().equals(position)) {
				this.powerUpPlayer = new Pair<>(this.model.getCurrentRoundEnvironment().getRoundPlayers()
						.getCurrentPlayer(), p);
			}
		}
		this.mover.movePlayer(position);
		if (this.powerUpPlayer.getKey().getCoordinate().equals(position)) {
			switch (this.powerUpPlayer.getValue().getType()) {
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
