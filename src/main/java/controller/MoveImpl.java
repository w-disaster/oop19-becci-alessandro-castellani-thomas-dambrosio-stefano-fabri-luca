package controller;

import java.util.*;

import model.*;

public class MoveImpl implements Move {

	private StandardGame game;
	private List<StandardPlayer> turns;
	private Iterator<StandardPlayer> iter;
	
	public MoveImpl(StandardGame game, List<StandardPlayer> turns) {
		this.game = game;
		this.turns = turns;
		this.iter = this.turns.iterator();
		this.game.setCurrentPlayer(this.iter.next());
	}
	
	@Override
	public void changeTurn() {
		if (this.iter.hasNext()) {
			this.game.setCurrentPlayer(this.iter.next());
		} else {
			this.iter = this.turns.iterator();
			this.game.setCurrentPlayer(this.iter.next());
		}
	}

}
