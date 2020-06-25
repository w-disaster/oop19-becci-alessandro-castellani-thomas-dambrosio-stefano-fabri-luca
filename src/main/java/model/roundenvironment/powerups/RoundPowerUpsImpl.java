package model.roundenvironment.powerups;

import java.util.ArrayList;
import java.util.List;
import model.Model;
import model.roundenvironment.powerups.PowerUp.Type;

/**
 * Implementation of RoundPowerUps.
 * 
 * @author Stefano
 */
public class RoundPowerUpsImpl implements RoundPowerUps {

    private final List<PowerUp> powerUpList;

	/**
	 * Instantiates a new roundPowerUps implementation.
	 */
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

	/**
	 * Instantiates a new roundPowerUp given a powerUp list.
	 *
	 * @param powerUpList the powerUp list
	 */
	public RoundPowerUpsImpl(final List<PowerUp> powerUpList) {
		this.powerUpList = powerUpList;
	}

	@Override
	public final List<PowerUp> getPowerUpsAsList() {
		return this.powerUpList;
	}

	@Override
    public final void remove(final PowerUp p) {
		this.powerUpList.remove(p);
	}
}