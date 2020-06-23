package model.roundenvironment.powerups;

import java.util.List;

import model.roundenvironment.coordinate.Coordinate;

public interface RoundPowerUps {
	
	public static final int POWERUP_NUMBER = 4;
	
	List<PowerUp> getPowerUpsAsList();
	
	void remove(PowerUp p);
	
}
