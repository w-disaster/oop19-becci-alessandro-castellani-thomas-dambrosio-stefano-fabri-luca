package controller;

import java.util.*;

import model.*;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
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
	
	private RoundPlayers game = new RoundPlayersImpl();
	private RoundBarriers barriers = new RoundBarriersImpl();
	private BarrierPlacer placer;
	private PlayerMover mover;
	
	public StandardGameControllerImpl(String nicknamePlayer1, String nicknamePlayer2) {
		List<Player> playersList = new ArrayList<>();
		Player player1 = new PlayerImpl(nicknamePlayer1, new Coordinate(4,0), Optional.of(10), 8);
		Player player2 = new PlayerImpl(nicknamePlayer2, new Coordinate(4,8), Optional.of(10), 0);
		playersList.add(player1);
		playersList.add(player2);
		this.game.setPlayers(playersList);
		List<Player> turns = this.game.getPlayers();
		this.placer = new BarrierPlacerImpl(this.game, this.barriers, turns);
		this.mover = new PlayerMoverImpl(this.game, this.barriers, turns);
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
	 * @return the current game
	 */
	public RoundPlayers getGame() {
		return this.game;
	}
}