package model.roundenvironment;
import model.roundenvironment.powerups.RoundPowerUps;

/**
 * This Interface models a new round environment with powerUps.
 * 
 * @author Stefano
 */
public interface RoundPUpEnvironment extends RoundEnvironment {
	
	public RoundPowerUps getRoundPowerUps();
	
}
