package model.roundenvironment.powerups;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Model;
import model.roundenvironment.coordinate.Coordinate;

public class RoundPowerUpsImpl implements RoundPowerUps {
	
	private List<PowerUp> powerUpList;
	
	public RoundPowerUpsImpl() {
		this.powerUpList = new ArrayList<>();
		for (int i = 0; i < POWERUP_NUMBER; i++) {
			this.powerUpList.add(new PowerUpImpl(Model.BOARD_DIMENSION));
		}
	}
	
	public RoundPowerUpsImpl(int numberOfPowerUps) {
		this.powerUpList = new ArrayList<>();
		for (int i = 0; i < numberOfPowerUps; i++) {
			this.powerUpList.add(new PowerUpImpl(Model.BOARD_DIMENSION));
		}
	}

	@Override
	public List<PowerUp> getPowerUpsAsList() {
		return this.powerUpList;
	}

	@Override
	public void remove(Coordinate coordinate) {
		PowerUp p = this.powerUpList.stream()
			.filter(e -> e.getCoordinate().equals(coordinate))
			.collect(Collectors.toList())
			.get(0);
		this.powerUpList.remove(p);
	}

}
