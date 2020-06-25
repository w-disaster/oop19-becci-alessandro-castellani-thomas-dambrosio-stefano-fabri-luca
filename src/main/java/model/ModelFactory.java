package model;

import java.util.List;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;

/**
 * A factory for creating Model objects.
 */
public interface ModelFactory {
	
	/**
	 * Builds a new model of a standard game from scratch.
	 *
	 * @param nickname1 the nickname 1
	 * @param nickname2 the nickname 2
	 * @return model of a standard game
	 */
	Model<RoundEnvironment> buildStandard(String nickname1, String nickname2);
	
	/**
	 * Builds a model of a game with power ups from scratch.
	 *
	 * @param nickname1 the nickname 1
	 * @param nickname2 the nickname 2
	 * @return model of game with power ups
	 */
	Model<RoundPUpEnvironment> buildWithPowerUps(String nickname1, String nickname2);
	
	/**
	 * Builds a model from saved game.
	 *
	 * @param <X> the generic type
	 * @param roundEnvironments the round environments
	 * @param boardDimension the board dimension
	 * @return the model
	 */
	<X extends RoundEnvironment> Model<X> buildFromExisting(List<X> roundEnvironments, int boardDimension);
	
}
