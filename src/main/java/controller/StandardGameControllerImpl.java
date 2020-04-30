package controller;

import java.util.*;

import model.*;
import model.Barriers.BarrierType;

public class StandardGameControllerImpl implements BarrierPlacer, PlayerMover {

	private StandardGame game = new StandardGameImpl();
	private BarrierPlacer placer = new BarrierPlacerImpl();
	private PlayerMover mover = new PlayerMoverImpl();
	
	public StandardGameControllerImpl(String nicknamePlayer1, String nicknamePlayer2) {
		List<StandardPlayerImpl> playersList = new ArrayList<>();
		StandardPlayer player1 = new StandardPlayerImpl(nicknamePlayer1, new Pair<>(0,4), 10);
		StandardPlayer player2 = new StandardPlayerImpl(nicknamePlayer2, new Pair<>(8,4), 10);
		playersList.add(player1);
		playersList.add(player2);
		this.game.setPlayers(playersList);
	}
	
	@Override
	public void movePlayer(Pair<Integer, Integer> position) {
		this.mover.movePlayer(position);
	}

	@Override
	public void placeBarrier(Pair<Integer, Integer> position, BarrierType type) {
		this.placer.placeBarrier(position, type);
	}
}