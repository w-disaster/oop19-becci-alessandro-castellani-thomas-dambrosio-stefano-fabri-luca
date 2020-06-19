package model;


import java.util.List;

import model.roundenvironment.RoundEnvironment;

public interface ModelFactory {
	
	/**
	 * builds a new model from scratch
	 * @param nickname1
	 * @param nickname2
	 * @return model of a standard game
	 */
	Model<RoundEnvironment> buildFromScratch(String nickname1, String nickname2);
	
	Model<RoundEnvironment> buildWithPowerUp(String nicknamePlayer1, String nicknamePlayer2);
	
	/**
	 * builds a model from saved game
	 * @param <X>
	 * @param roundEnvironments
	 * @param boardDimension
	 * @return
	 */
	<X extends RoundEnvironment> Model<X> buildFromExisting(List<X> roundEnvironments, int boardDimension);
		
}
