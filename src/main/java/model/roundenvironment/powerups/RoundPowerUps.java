package model.roundenvironment.powerups;

import java.util.List;

import model.roundenvironment.coordinate.Coordinate;

public interface RoundPowerUps {
	
	public static final int BARRIER_POWERUP_NUMBER = 3;
	
	public static final int MOVE_POWERUP_NUMBER = 3;
	
	List<PowerUp> getPowerUpsAsList();
	
	void remove(PowerUp p);
	
}
