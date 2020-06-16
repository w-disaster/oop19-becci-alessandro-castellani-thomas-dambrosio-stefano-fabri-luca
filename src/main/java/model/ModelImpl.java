package model;

import java.util.List;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerState;

public class ModelImpl<X extends RoundEnvironment> implements Model<X> {

	private int boardDimension;
	private X currentRoundEnvironment;
	private List<X> gameRoundsEnvironments;
	private List<Pair<Player, PlayerState>> ranking;
	
	/**
	 * Model from existing environments, one for each round, and eventually the dimension of the board
	 * @param gameRoundsEnvironments
	 * @param boardDimension
	 */
	public ModelImpl(final List<X> gameRoundsEnvironments, final int boardDimension) {
		super();
		this.boardDimension = boardDimension;
		this.gameRoundsEnvironments = gameRoundsEnvironments;
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
	public List<X> getGameRoundsEnvironments() {
		return this.gameRoundsEnvironments;
	}

	@Override
	public List<Pair<Player, PlayerState>> getRanking() {
		return this.ranking;
	}

	@Override
	public int getNumberRounds() {
		return this.gameRoundsEnvironments.size();
	}

	@Override
	public int getBoardDimension() {
		return this.boardDimension;
	}

}
