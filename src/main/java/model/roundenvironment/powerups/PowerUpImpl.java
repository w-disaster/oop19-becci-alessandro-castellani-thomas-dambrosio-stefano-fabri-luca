package model.roundenvironment.powerups;

import java.util.Random;

import model.roundenvironment.coordinate.Coordinate;

/**
 * Implementation of PowerUp.
 * 
 * @author Stefano
 */
public class PowerUpImpl implements PowerUp {

	private Random random;

	private Type type;

	private Coordinate coordinate;

	/**
	 * Instantiates a new powerUp.
	 *
	 * @param type the type
	 * @param boardDimension the board dimension
	 */
	public PowerUpImpl(final Type type, final int boardDimension) {
		this.random = new Random();
		this.type = type;
		// It prevents powerUps from being spawned in the top or bottom 2 rows
		this.coordinate = new Coordinate(2 + random.nextInt(boardDimension - 4), 2 + random.nextInt(boardDimension - 4));
	}

	/**
	 * Instantiates a new powerUp with given coordinates and type.
	 *
	 * @param coord the coord
	 * @param type the type
	 */
	public PowerUpImpl(final Coordinate coord, final Type type) {
		this.coordinate = coord;
		this.type = type;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	@Override
	public Type getType() {
		return this.type;
	}

	/**
	 * Gets the coordinate.
	 *
	 * @return the coordinate
	 */
	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return "PowerUp type: " + this.getType() + "\nPowerUp Coordinate: " + this.getCoordinate() + "\n";
	}
}
