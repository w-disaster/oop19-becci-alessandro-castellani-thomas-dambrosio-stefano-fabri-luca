package model;

import java.util.List;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.Player;

public class ModelImpl<X extends RoundEnvironment> implements Model<X> {

	private int boardDimension;
	private X currentRoundEnvironment;
	private List<X> gameRoundEnvironments;
	private List<Player> winners;
	
	/**
	 * Model from existing environments, one for each round, and eventually the dimension of the board
	 * @param gameRoundsEnvironments
	 * @param boardDimension
	 */
	public ModelImpl(final List<X> gameRoundEnvironments, final int boardDimension) {
		super();
		this.boardDimension = boardDimension;
		this.gameRoundEnvironments = gameRoundEnvironments;
	}

	@Override
	public X getCurrentRoundEnvironment() {
		return this.currentRoundEnvironment;
	}
	
	@Override
	public void setCurrentRoundEnvironment(X roundEnvironment) {
		this.currentRoundEnvironment = roundEnvironment;
	}
	
	@Override
	public List<X> getGameRoundEnvironments() {
		return this.gameRoundEnvironments;
	}

	@Override
	public List<Player> getWinners() {
		return this.winners;
	}

	@Override
	public int getNumberRounds() {
		return this.gameRoundEnvironments.size();
	}

	@Override
	public int getBoardDimension() {
		return this.boardDimension;
	}
	
}
