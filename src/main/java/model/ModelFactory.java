package model;


import java.util.List;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;

public interface ModelFactory {
	
	/**
	 * builds a new model of a standard game from scratch
	 * @param nickname1
	 * @param nickname2
	 * @return model of a standard game
	 */
	Model<RoundEnvironment> buildStandard(String nickname1, String nickname2);
	
	/**
	 * builds a model of a game with power ups from scratch
	 * @param nicknamePlayer1
	 * @param nicknamePlayer2
	 * @return model of game with power ups
	 */
	Model<RoundPUpEnvironment> buildWithPowerUps(String nicknamePlayer1, String nicknamePlayer2);
	
	/**
	 * builds a model from saved game
	 * @param <X>
	 * @param roundEnvironments
	 * @param boardDimension
	 * @return
	 */
	<X extends RoundEnvironment> Model<X> buildFromExisting(List<X> roundEnvironments, int boardDimension);
		
}
