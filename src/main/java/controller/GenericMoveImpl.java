package controller;

import java.util.*;
import java.util.function.BinaryOperator;

import controllers.UIController;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import savings.SaveLeaderBoard;

public class GenericMoveImpl {

	private Model<RoundEnvironment> model;
	private UIController view;
	private SaveLeaderBoard leaderboard;
	private RoundPlayers players;
	private List<Player> turns;
	private Iterator<Player> iterTurns;
	private Iterator<RoundEnvironment> iterRounds;
	private List<Player> roundWinner;
	
	public GenericMoveImpl(Model<RoundEnvironment> model, UIController view, Iterator<Player> iterTurns, Iterator<RoundEnvironment> iterRounds, List<Player> roundWinner) {
		this.model = model;
		this.view = view;
		this.leaderboard = new SaveLeaderBoard();
		this.iterRounds = iterRounds;
		this.iterTurns = iterTurns;
		this.roundWinner = roundWinner;
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.turns = this.players.getPlayers();
		this.players.setCurrentPlayer(this.iterTurns.next());
	}
	
	protected void changeTurn() {
		if (this.iterTurns.hasNext()) {
			this.players.setCurrentPlayer(this.iterTurns.next());
		} else {
			this.iterTurns = this.turns.iterator();
			this.players.setCurrentPlayer(this.iterTurns.next());
		}
	}
	
	protected void changeRound() {
		if (this.iterRounds.hasNext()) {
			//i need to check if a player have already won so i don't pass to the next round
			String currentPlayer = this.players.getCurrentPlayer().getNickname();
			long nWins = this.roundWinner.stream().peek(Player::getNickname)
													.filter(p -> p.getNickname().compareTo(currentPlayer) == 0)
													.count();
			if (nWins > this.model.getGameRoundsEnvironments().size()/2) { //if the current player won half of the rounds he won the game
				System.out.println("Game Over!" + currentPlayer + " won!");
				this.leaderboard.updateLeaderBoard(currentPlayer);
				this.view.endGame(currentPlayer);
			}
			this.model.setCurrentRoundEnvironment(this.iterRounds.next());
			this.view.endRound(currentPlayer);
			
		} else {
			System.out.println("All rounds finished, game over");
			//now i check who won more rounds and set him winner of the game
			Player p = this.roundWinner.stream().peek(Player::getNickname)
												.reduce(BinaryOperator.maxBy(
									                    Comparator.comparingInt(o -> Collections.frequency(this.roundWinner, o))))
									            .orElse(null);
			System.out.println("Game Over!" + p.getNickname() + " won!");
			this.leaderboard.updateLeaderBoard(p.getNickname());
			this.view.endGame(p.getNickname());
		}
	}
	
	protected void addWinner(Player player) {
		this.roundWinner.add(player);
	}
}
