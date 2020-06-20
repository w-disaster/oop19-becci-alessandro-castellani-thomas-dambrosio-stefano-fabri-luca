package controller;

import java.util.Iterator;

import controller.barrierPlacers.BarrierPlacer;
import controller.barrierPlacers.BarrierPlacerPowerUp;
import controller.playerMovers.PlayerMover;
import controller.playerMovers.PlayerMoverPowerUp;
import controllers.UIController;
import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import savings.LoadGame;
import savings.SaveGame;

public class PowerUpGameControllerImpl extends StandardGameControllerImpl implements BarrierPlacer, PlayerMover{
	
	private Model<RoundPUpEnvironment> model;
	private UIController view;
	private SaveGame saving;
	private LoadGame loading;
	private Iterator<RoundPUpEnvironment> iterRounds;
	private BarrierPlacer placer;
	private PlayerMover mover;

	public PowerUpGameControllerImpl(UIController view) {
		super(view);
	}

	public void newPowerUpGame(String nicknamePlayer1, String nicknamePlayer2) {
		Coordinate player1Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate player2Coordinate = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
		this.view.setupGrid(player1Coordinate, player2Coordinate, 10, 10);
		this.model = new ModelFactoryImpl().buildWithPowerUps(nicknamePlayer1, nicknamePlayer2);
		this.iterRounds = this.model.getGameRoundEnvironments().iterator();
		this.model.setCurrentRoundEnvironment(this.iterRounds.next()); //setting current round (first)
		this.mover = new PlayerMoverPowerUp(this.model, this.view, this.iterRounds);
		this.placer = new BarrierPlacerPowerUp(this.model, this.view, this.iterRounds);
	}
	
	@Override
	public void movePlayer(Coordinate position) {
		

	}

	@Override
	public void placeBarrier(Coordinate position, Orientation orientation) {
		// TODO Auto-generated method stub

	}

}
