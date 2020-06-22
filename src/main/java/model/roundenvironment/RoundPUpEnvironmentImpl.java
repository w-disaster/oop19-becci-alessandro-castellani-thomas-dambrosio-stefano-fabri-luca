package model.roundenvironment;

import model.roundenvironment.barriers.*;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.powerups.RoundPowerUps;

public class RoundPUpEnvironmentImpl extends RoundEnvironmentImpl implements RoundPUpEnvironment{
	
	private RoundPowerUps roundPowerUps;
	
	public RoundPUpEnvironmentImpl(final RoundBarriers roundBarriers, final RoundPlayers roundPlayers, final RoundPowerUps roundPowerUps) {
		super(roundBarriers, roundPlayers);
		System.out.println(roundPowerUps);
		this.roundPowerUps = roundPowerUps;
	}

	@Override
	public RoundPowerUps getRoundPowerUps() {
		return this.roundPowerUps;
	}

}
