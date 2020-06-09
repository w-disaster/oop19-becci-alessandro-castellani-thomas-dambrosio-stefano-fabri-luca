package model.roundenvironment;

import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.players.RoundPlayers;

public class RoundEnvironmentImpl implements RoundEnvironment {

	private RoundBarriers roundBarriers;
	private RoundPlayers roundPlayers;
	
	public RoundEnvironmentImpl(RoundBarriers roundBarriers, RoundPlayers roundPlayers) {
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
