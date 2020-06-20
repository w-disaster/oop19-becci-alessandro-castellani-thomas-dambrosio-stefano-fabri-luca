package model.roundenvironment;

import javafx.util.Pair;
import model.roundenvironment.barriers.*;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.powerups.PowerUp;
import model.roundenvironment.powerups.PowerUp.Type;
import model.roundenvironment.powerups.PowerUpImpl;

public class RoundPUpEnvironmentImpl extends RoundEnvironmentImpl implements RoundPUpEnvironment{
	
	private PowerUp powerUp;
	
	public RoundPUpEnvironmentImpl(final RoundBarriers roundBarriers, final RoundPlayers roundPlayers) {
		super(roundBarriers, roundPlayers);
		this.powerUp = new PowerUpImpl(roundBarriers, roundPlayers);
	}

//	public RoundPUpEnvironmentImpl(final RoundBarriers roundBarriers, final RoundPlayers roundPlayers, final RoundBarriers junk) {
//		super(roundBarriers, roundPlayers);
//		this.junk = junk;
//	}

	@Override
	public Pair<Type, Coordinate> getPowerUp() {
		return new Pair<>(this.powerUp.getType(), this.powerUp.getCoordinate());
	}

}
