package model;

import java.util.List;

public class StandardGameImpl implements StandardGame {
	
	public static final int STANDARD_DIMENSION = 9;
	private StandardPlayer currentPlayer;
	private List<StandardPlayer> players;
	private int boardDimension;
	
	@Override
	public StandardPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public List<StandardPlayer> getPlayers() {
		return this.players;
	}
	
	@Override
	public int getBoardDimension() {
		return this.boardDimension;
	}
	
	@Override
	public void setCurrentPlayer(StandardPlayer player) {
		this.currentPlayer = player;
	}

	@Override
	public void setPlayers(List<StandardPlayer> players) {
		this.players = players;
	}
	
}
