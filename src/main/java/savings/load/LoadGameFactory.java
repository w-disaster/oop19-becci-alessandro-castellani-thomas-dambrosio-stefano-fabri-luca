package savings.load;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;

/**
 * A factory for creating LoadGame objects.
 */
public interface LoadGameFactory {

	/**
	 * Builds a new Load Game for Normal Game.
	 *
	 * @return the load game
	 */
	LoadGame<RoundEnvironment> buildNormal();
	
	/**
	 * Builds a new Load Game for PowerUp Game.
	 *
	 * @return the load game
	 */
	LoadGame<RoundPUpEnvironment> buildPowerup();
	
}
