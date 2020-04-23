package model;

public class StandardGameImpl implements StandardGame{
	
	private StandardPlayer currentPlayer;
	private Pair<StandardPlayer, StandardPlayer> players;

	@Override
	public StandardPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public Pair<StandardPlayer, StandardPlayer> getPlayers() {
		return this.players;
	}
	
	@Override
	public void setCurrentPlayer(StandardPlayer player) {
		this.currentPlayer = player;
	}

	@Override
	public void setPlayers(Pair<StandardPlayer, StandardPlayer> players) {
		this.players = players;
	}

}
