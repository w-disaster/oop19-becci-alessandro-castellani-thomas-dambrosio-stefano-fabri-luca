package model.roundenvironment;

import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.players.RoundPlayers;

/**
 * Class of a standard round environment.
 * @author luca
 *
 */
public class RoundEnvironmentImpl implements RoundEnvironment {

	private RoundBarriers roundBarriers;
	private RoundPlayers roundPlayers;
	
	/**
	 * Round environment from existing barriers and players.
	 * @param roundBarriers
	 * @param roundPlayers
	 */
	public RoundEnvironmentImpl(final RoundBarriers roundBarriers, final RoundPlayers roundPlayers) {
		super();
		this.roundBarriers = roundBarriers;
		this.roundPlayers = roundPlayers;
	}

	@Override
	public RoundBarriers getRoundBarriers() {
		return this.roundBarriers;
	}

	@Override
	public RoundPlayers getRoundPlayers() {
		return this.roundPlayers;
	}

}
