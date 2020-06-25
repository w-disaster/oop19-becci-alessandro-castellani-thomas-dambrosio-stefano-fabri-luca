package model.roundenvironment.powerups;

import java.util.List;

/**
 * The Interface models the powerUps in a round.
 * 
 * @author Stefano
 */
public interface RoundPowerUps {
	
	/** The barrier powerup number. */
	int BARRIER_POWERUP_NUMBER = 3;
	
	/** The move powerup number. */
	int MOVE_POWERUP_NUMBER = 3;
	
	List<PowerUp> getPowerUpsAsList();
	
	void remove(PowerUp p);
	
}
