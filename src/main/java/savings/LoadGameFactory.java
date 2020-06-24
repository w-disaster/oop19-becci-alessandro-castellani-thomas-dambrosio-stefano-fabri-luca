package savings;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;

public interface LoadGameFactory {

	/**
	 * Builds a new Load Game for Normal Game
	 */
	public LoadGame<RoundEnvironment> buildNormal();
	
	/**
	 * Builds a new Load Game for PowerUp Game
	 */
	public LoadGame<RoundPUpEnvironment> buildPowerup();
	
}
