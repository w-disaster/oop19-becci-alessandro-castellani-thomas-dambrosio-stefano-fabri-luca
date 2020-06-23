package model.roundenvironment.powerups;

import java.util.Random;

import model.roundenvironment.coordinate.Coordinate;


public class PowerUpImpl implements PowerUp{
	
	private Random random;
	private Type type;
	private Coordinate coordinate;
	
	public PowerUpImpl(Type type, int boardDimension) {
		this.random = new Random();
		this.type = type;
		this.coordinate = new Coordinate(2+random.nextInt(boardDimension-4), 2+random.nextInt(boardDimension-4));
	}
	
	public PowerUpImpl(final Coordinate coord, final Type type) {
		this.coordinate = coord;
		this.type = type;
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
		return "PowerUp type: " + this.getType() + "\nPowerUp Coordinate: " + this.getCoordinate() + "\n";
		
	}
}
