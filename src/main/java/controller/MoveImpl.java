package controller;

import java.util.*;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class MoveImpl implements Move {

	private Model<RoundEnvironment> model;
	private RoundPlayers players;
	private List<Player> turns;
	private Iterator<Player> iter;
	
	public MoveImpl(Model<RoundEnvironment> model, List<Player> turns) {
		this.model = model;
		this.turns = turns;
		this.iter = this.turns.iterator();
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.players.setCurrentPlayer(this.iter.next());
	}
	
	@Override
	public void changeTurn() {
		if (this.iter.hasNext()) {
			this.players.setCurrentPlayer(this.iter.next());
		} else {
			this.iter = this.turns.iterator();
			this.players.setCurrentPlayer(this.iter.next());
		}
	}

}
