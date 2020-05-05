package controller;

import java.util.*;

import model.*;
import model.Barrier.BarrierType;

/**
 * controller for standard games
 * 
 * @author Thomas
 */
public class StandardGameControllerImpl implements BarrierPlacer, PlayerMover {
	
	private StandardGame game = new StandardGameImpl();
	private GameBarriers barriers = new GameBarriersImpl();
	private BarrierPlacer placer;
	private PlayerMover mover;
	
	public StandardGameControllerImpl(String nicknamePlayer1, String nicknamePlayer2) {
		List<StandardPlayer> playersList = new ArrayList<>();
		StandardPlayer player1 = new StandardPlayerImpl(nicknamePlayer1, new Coordinate(4,0), Optional.of(10), 8);
		StandardPlayer player2 = new StandardPlayerImpl(nicknamePlayer2, new Coordinate(4,8), Optional.of(10), 0);
		playersList.add(player1);
		playersList.add(player2);
		this.game.setPlayers(playersList);
		List<StandardPlayer> turns = this.game.getPlayers();
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
	public StandardGame getGame() {
		return this.game;
	}
}