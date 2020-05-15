package controller;

import java.util.*;
import model.*;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.barriers.Barrier.BarrierType;
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
	private BarrierPlacer placer;
	private PlayerMover mover;
	
	public StandardGameControllerImpl(String nicknamePlayer1, String nicknamePlayer2) {
		List<Player> playersList = new ArrayList<>();
		Player player1 = new PlayerImpl(nicknamePlayer1, new Coordinate(4,0), Optional.of(10), 8);
		Player player2 = new PlayerImpl(nicknamePlayer2, new Coordinate(4,8), Optional.of(10), 0);
		playersList.add(player1);
		playersList.add(player2);
		List<RoundEnvironment> listEnvironment = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RoundBarriers barriers = new RoundBarriersImpl();
			RoundPlayers players = new RoundPlayersImpl(playersList);
			RoundEnvironment environment = new RoundEnvironmentImpl(barriers,players);
			listEnvironment.add(environment);
		}
		this.model = new ModelImpl<>(listEnvironment, Optional.empty());
		List<Player> turns = this.model.getCurrentRoundEnvironment().getRoundPlayers().getPlayers();
		this.mover = new PlayerMoverImpl(this.model, turns, listEnvironment);
		this.placer = new BarrierPlacerImpl(this.model, turns);
	}
	
	@Override
	public void movePlayer(Coordinate position) {
		this.mover.movePlayer(position);
	}

	@Override
	public void placeBarrier(Coordinate position, BarrierType type) {
		this.placer.placeBarrier(position, type);
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