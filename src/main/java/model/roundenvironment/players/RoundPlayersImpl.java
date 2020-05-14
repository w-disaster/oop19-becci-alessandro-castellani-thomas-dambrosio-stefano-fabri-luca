package model.roundenvironment.players;

import java.util.List;

public class RoundPlayersImpl implements RoundPlayers {
	
	private Player currentPlayer;
	private List<Player> players;
	
	public RoundPlayersImpl(List<Player> players) {
		super();
		this.players = players;
	}

	@Override
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
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
