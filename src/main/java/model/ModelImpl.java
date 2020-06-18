package model;

import java.util.ArrayList;
import java.util.List;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.Player;

public class ModelImpl<X extends RoundEnvironment> implements Model<X>{

	private List<X> gameRoundEnvironments;
	private int boardDimension;
	private X currentRoundEnvironment;
	private List<Player> winners;
	
	/**
	 * ModelImpl object from fields
	 * @param gameRoundEnvironments
	 * @param boardDimension
	 * @param currentRoundEnvironment
	 * @param winners
	 */
	public ModelImpl(List<X> gameRoundEnvironments, int boardDimension, X currentRoundEnvironment,
			List<Player> winners) {
		super();
		this.gameRoundEnvironments = gameRoundEnvironments;
		this.boardDimension = boardDimension;
		this.currentRoundEnvironment = currentRoundEnvironment;
		this.winners = winners;
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
