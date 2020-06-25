package model.roundenvironment;

import model.roundenvironment.barriers.*;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.powerups.RoundPowerUps;

/**
 * The implementation of RoundPUpEnvironment.
 * 
 * @author Stefano 
 */
public class RoundPUpEnvironmentImpl extends RoundEnvironmentImpl implements RoundPUpEnvironment{
	
	private final RoundPowerUps roundPowerUps;
	
	/**
	 * Instantiates a new roundPUpEnvironment implementation
	 *
	 * @param roundBarriers the round barriers
	 * @param roundPlayers the round players
	 * @param roundPowerUps the round power ups
	 */
	public RoundPUpEnvironmentImpl(final RoundBarriers roundBarriers, final RoundPlayers roundPlayers, final RoundPowerUps roundPowerUps) {
		super(roundBarriers, roundPlayers);
		this.roundPowerUps = roundPowerUps;
	}

	@Override
	public RoundPowerUps getRoundPowerUps() {
		return this.roundPowerUps;
	}

}
