package model.roundenvironment.powerups;

import java.util.Random;

import model.roundenvironment.coordinate.Coordinate;


public class PowerUpImpl implements PowerUp{
	
	private Random random;
	private Type type;
	private Coordinate coordinate;
	
	public PowerUpImpl(int boardDimension) {
		random = new Random();
		switch(random.nextInt(2)) {
		case 0:
			this.type = Type.CLIMB_A_BARRIER;
			break;
		case 1:
			this.type = Type.PLUS_ONE_BARRIER;
			break;
		default:
			break;
		}
		this.coordinate = new Coordinate(2+random.nextInt(boardDimension-2), 2+random.nextInt(boardDimension-2));
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	public String toString() {
		return "PowerUp type: " + this.getType() + "\n PowerUp Coordinate: " + this.getCoordinate() + "\n";
		
	}
}
