package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.roundenvironment.BarrierEnvironment;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerState;

public class ModelImpl<X> implements Model<X> {

	private Integer boardDimension;
	private X currentRoundEnvironment;
	private List<X> gameRoundsEnvironments;
	private Map<X, List<Pair<PlayerState, Player>>> ranking;
	
	/**
	 * Model from existing environments, one for each round, and eventually the dimension of the board
	 * @param gameRoundsEnvironments
	 * @param boardDimension
	 */
	public ModelImpl(List<X> gameRoundsEnvironments, Optional<Integer> boardDimension) {
		super();
		this.boardDimension = boardDimension.isEmpty() ? BOARD_DIMENSION : boardDimension.get();
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
	public Map<X, List<Pair<PlayerState, Player>>> getRanking() {
		return this.ranking;
	}

	@Override
	public Integer getNumberRounds() {
		return this.gameRoundsEnvironments.size();
	}

	@Override
	public Integer getBoardDimension() {
		return this.boardDimension;
	}

}
