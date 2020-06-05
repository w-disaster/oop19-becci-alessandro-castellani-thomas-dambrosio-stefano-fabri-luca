package controller;

import java.util.*;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;

public class GenericMoveImpl {

	private Model<RoundEnvironment> model;
	private RoundPlayers players;
	private List<Player> turns;
	private Iterator<Player> iterTurns;
	
	public GenericMoveImpl(Model<RoundEnvironment> model, List<Player> turns) {
		this.model = model;
		this.turns = turns;
		this.iterTurns = this.turns.iterator();
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.players.setCurrentPlayer(this.iterTurns.next());
	}
	
	public void changeTurn() {
		if (this.iterTurns.hasNext()) {
			this.players.setCurrentPlayer(this.iterTurns.next());
		} else {
			this.iterTurns = this.turns.iterator();
			this.players.setCurrentPlayer(this.iterTurns.next());
		}
	}

	public void restartTurns() {
		this.iterTurns = this.turns.iterator();
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.players.setCurrentPlayer(this.iterTurns.next());
	}
	
}
