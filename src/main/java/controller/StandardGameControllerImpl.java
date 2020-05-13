package controller;

import java.util.*;

import model.*;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.RoundEnvironment;
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
	
	private Model<RoundEnvironment> model = new ModelImpl<>();
	private RoundPlayers players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
	private BarrierPlacer placer;
	private PlayerMover mover;
	
	public StandardGameControllerImpl(String nicknamePlayer1, String nicknamePlayer2) {
		List<Player> playersList = new ArrayList<>();
		Player player1 = new PlayerImpl(nicknamePlayer1, new Coordinate(4,0), Optional.of(10), 8);
		Player player2 = new PlayerImpl(nicknamePlayer2, new Coordinate(4,8), Optional.of(10), 0);
		playersList.add(player1);
		playersList.add(player2);
		this.players.setPlayers(playersList);
		List<Player> turns = this.players.getPlayers();
		this.placer = new BarrierPlacerImpl(this.model, turns);
		this.mover = new PlayerMoverImpl(this.model, turns);
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
	public Model<RoundEnvironment> getGame() {
		return this.model;
	}
}