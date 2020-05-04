package controller;

import java.util.*;

import model.*;

public class MoveImpl implements Move {

	private StandardGame game = new StandardGameImpl();
	private List<StandardPlayer> turns = this.game.getPlayers();
	
	@Override
	public void changeTurn() {
		this.turns.remove(0); //remove the current player turn
		if (this.turns.isEmpty()) { //new circle of turns when one is finished
			this.turns = this.game.getPlayers();
		}
		this.game.setCurrentPlayer(this.turns.get(0));
	}

}
