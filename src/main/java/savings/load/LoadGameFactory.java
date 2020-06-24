package savings.load;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;

/**
 * A factory for creating LoadGame objects.
 * @author Alessandro Becci
 */
public interface LoadGameFactory {

	/**
	 * Builds a new Load Game for Normal Game.
	 *
	 * @return the load game
	 */
	public LoadGame<RoundEnvironment> buildNormal();
	
	/**
	 * Builds a new Load Game for PowerUp Game.
	 *
	 * @return the load game
	 */
	public LoadGame<RoundPUpEnvironment> buildPowerup();
	
}
