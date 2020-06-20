package model.roundenvironment.powerups;

import java.util.Random;

import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.RoundPlayers;

public class PowerUpImpl extends RoundEnvironmentImpl implements PowerUp {
	
	Random random;

	public PowerUpImpl(RoundBarriers roundBarriers, RoundPlayers roundPlayers) {
		super(roundBarriers, roundPlayers);
	}

	@Override
	public Type getType() {
		switch(random.nextInt(2)) {
		case '0':
			return Type.CLIMB_A_BARRIER;
		case '1':
			return Type.PLUS_ONE_BARRIER;
		case '2':
			return Type.PLUS_ONE_MOVE;	
		}
		return null;
	}

	@Override
	public Coordinate getCoordinate() {
		return new Coordinate(random.nextInt(8), random.nextInt(8));
	}

}
