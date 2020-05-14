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

public class ModelImpl<X extends BarrierEnvironment> implements Model<X>{

	public static final int BOARD_DIMENSION = 9;
	public static final int NUMBER_ROUNDS = 3;
	private Integer boardDimension;
	private X currentRoundEnvironment;
	private List<X> gameRoundsEnvironments;
	private Map<X, List<Pair<PlayerState, Player>>> ranking;
	//private Integer numberRounds;
	
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
	public void setCurrentRoundEnvironment(final X roundEnvironment) {
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
