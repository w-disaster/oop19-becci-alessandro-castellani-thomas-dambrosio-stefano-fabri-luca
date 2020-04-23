package model;

import java.util.List;

public class StandardGameImpl implements StandardGame{
	
	public static final int DEFAULT_DIMENSION = 9;
	private List<Pair<StandardPlayer, StandardPlayer>> previousRoundsPlayers;
	private StandardPlayer currentPlayer;
	private Pair<StandardPlayer, StandardPlayer> players;
	private final int dimension;
	
	public StandardGameImpl() {
		this.dimension = DEFAULT_DIMENSION;
	}

	@Override
	public StandardPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public Pair<StandardPlayer, StandardPlayer> getPlayers() {
		return this.players;
	}
	
	@Override
	public List<Pair<StandardPlayer, StandardPlayer>> getPreviousRoundsPlayers() {
		return this.previousRoundsPlayers;
	}
	
	@Override
	public int getDimension() {
		return this.dimension;
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
