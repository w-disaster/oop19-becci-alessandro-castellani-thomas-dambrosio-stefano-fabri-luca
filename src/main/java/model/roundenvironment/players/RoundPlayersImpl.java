package model.roundenvironment.players;

import java.util.List;

public class RoundPlayersImpl implements RoundPlayers {
	
	public static final int STANDARD_DIMENSION = 9;
	private Player currentPlayer;
	private List<Player> players;
	private int boardDimension;
	
	@Override
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}
	
	@Override
	public int getBoardDimension() {
		return this.boardDimension;
	}
	
	@Override
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}

	@Override
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
}
