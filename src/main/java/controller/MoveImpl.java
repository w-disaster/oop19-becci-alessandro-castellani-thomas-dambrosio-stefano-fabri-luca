package controller;

import java.util.*;

import model.*;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class MoveImpl implements Move {

	private RoundPlayers game;
	private List<Player> turns;
	private Iterator<Player> iter;
	
	public MoveImpl(RoundPlayers game, List<Player> turns) {
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
