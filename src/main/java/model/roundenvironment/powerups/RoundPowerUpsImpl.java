package model.roundenvironment.powerups;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Model;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp.Type;

public class RoundPowerUpsImpl implements RoundPowerUps {
	
	private List<PowerUp> powerUpList;
	
	public RoundPowerUpsImpl() {
		this.powerUpList = new ArrayList<>();
		for (int i = 0; i < BARRIER_POWERUP_NUMBER; i++) {
			PowerUp p = new PowerUpImpl(Type.PLUS_ONE_BARRIER, Model.BOARD_DIMENSION);
			if (!this.powerUpList.isEmpty() && this.powerUpList.stream().anyMatch(e -> e.getCoordinate().equals(p.getCoordinate()))) {
				i--;
			} else {
				this.powerUpList.add(p);
			}				
		}
		for (int i = 0; i < MOVE_POWERUP_NUMBER; i++) {
			PowerUp p = new PowerUpImpl(Type.PLUS_ONE_MOVE, Model.BOARD_DIMENSION);
			if (!this.powerUpList.isEmpty() && this.powerUpList.stream().anyMatch(e -> e.getCoordinate().equals(p.getCoordinate()))) {
				i--;
			} else {
				this.powerUpList.add(p);
			}				
		}
	}
	
	public RoundPowerUpsImpl(final List<PowerUp> powerUpList) {
		this.powerUpList = powerUpList;
	}
	
	@Override
	public List<PowerUp> getPowerUpsAsList() {
		return this.powerUpList;
	}

	@Override
	public void remove(PowerUp p) {
		this.powerUpList.remove(p);
	}

}
