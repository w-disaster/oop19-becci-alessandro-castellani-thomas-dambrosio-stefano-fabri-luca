package model.roundenvironment;

import model.roundenvironment.barriers.*;
import model.roundenvironment.players.RoundPlayers;

public class RoundPUpEnvironmentImpl extends RoundEnvironmentImpl implements RoundPUpEnvironment{
	
	private RoundBarriers junk;
	
	public RoundPUpEnvironmentImpl(RoundBarriers roundBarriers, RoundPlayers roundPlayers) {
		super(roundBarriers, roundPlayers);
		this.junk = new RoundBarriersImpl();
	}

	public RoundPUpEnvironmentImpl(RoundBarriers roundBarriers, RoundPlayers roundPlayers, RoundBarriers junk) {
		super(roundBarriers, roundPlayers);
		this.junk = junk;
	}

	@Override
	public RoundBarriers randomMethod() {
		return this.junk;
	}

}
