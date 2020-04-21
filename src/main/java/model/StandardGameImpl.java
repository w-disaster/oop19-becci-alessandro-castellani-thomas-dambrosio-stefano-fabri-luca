package model;

import java.util.List;

public class StandardGameImpl implements StandardGame<StandardPlayer>{

	public static final Integer GRIDBOUNDARY = 9; //qua o in StandardPlayer ? 
	private List<Pair<StandardPlayer, StandardPlayer>> previousPlayers;
	private StandardPlayer currentPlayer;
	private Pair<StandardPlayer, StandardPlayer> players;
	
	public StandardGameImpl(String nickname1, String nickname2) {
		super();
		this.setPlayers(new Pair<StandardPlayer, StandardPlayer>(new StandardPlayerImpl(nickname1, new Pair<>(0, GRIDBOUNDARY/2)), 
				new StandardPlayerImpl(nickname2, new Pair<>(GRIDBOUNDARY, GRIDBOUNDARY/2))));
		this.setCurrentPlayer(this.players.getX());
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
	public List<Pair<StandardPlayer, StandardPlayer>> getPreviousPlayers() {
		return this.previousPlayers;
	}
	
	public void setCurrentPlayer(StandardPlayer player) {
		this.currentPlayer = player;
	}

	@Override
	public void setPlayers(Pair<StandardPlayer, StandardPlayer> players) {
		this.players = players;
	}

}
