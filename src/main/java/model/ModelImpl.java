package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerState;

public class ModelImpl<X extends RoundEnvironment> implements Model<X>{

	public static final int BOARD_DIMENSION = 9;
	private X currentRoundEnvironment;
	private List<X> gameRoundsEnvironments;
	private Map<Integer, List<Pair<PlayerState, Player>>> ranking;
	private Integer roundsNumber;
	private Integer boardDimension;
	
	@Override
	public X getCurrentRoundEnvironment() {
		return this.currentRoundEnvironment;
	}
	
	@Override
	public List<X> getGameRoundsEnvironments() {
		return this.gameRoundsEnvironments;
	}

	@Override
	public Map<Integer, List<Pair<PlayerState, Player>>> getRanking() {
		return this.ranking;
	}

	@Override
	public Integer getRoundsNumber() {
		return this.roundsNumber;
	}

	@Override
	public Integer getBoardDimension() {
		return this.boardDimension;
	}

}
