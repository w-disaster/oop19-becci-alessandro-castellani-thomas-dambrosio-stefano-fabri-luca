package model.roundenvironment.powerups;

import java.util.Random;

import model.roundenvironment.coordinate.Coordinate;


public class PowerUpImpl implements PowerUp{
	
	private Type type;
	private Coordinate coordinate;
	
	public PowerUpImpl(int boardDimension) {
		switch(new Random().nextInt(2)) {
		case '0':
			this.type = Type.CLIMB_A_BARRIER;
		case '1':
			this.type = Type.PLUS_ONE_BARRIER;
		case '2':
			this.type = Type.PLUS_ONE_MOVE;	
		}
		this.coordinate = new Coordinate(new Random().nextInt(boardDimension-1), new Random().nextInt(boardDimension-1));
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
}
