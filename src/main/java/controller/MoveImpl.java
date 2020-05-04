package controller;

import java.util.*;

import model.*;

public class MoveImpl implements Move {

	private StandardGame game;
	private List<StandardPlayer> turns;
	private Iterator<StandardPlayer> iter;
	
	public MoveImpl(StandardGame game) {
		this.game = game;
		this.turns = this.game.getPlayers();
		this.iter = this.turns.iterator();
	}
	
	@Override
	public void changeTurn() {
		if (this.iter.hasNext()) {
			this.game.setCurrentPlayer(this.iter.next());
		} else {
			this.turns = this.game.getPlayers();
			this.iter = this.turns.iterator();
		}
	}

}
