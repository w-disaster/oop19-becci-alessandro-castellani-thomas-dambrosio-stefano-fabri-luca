package model.roundenvironment.barriers;

import model.roundenvironment.coordinate.Coordinate;

public class BarrierImpl implements Barrier {

	private Coordinate coordinate;
	private Orientation type;
	
	public BarrierImpl(Coordinate coordinate, Orientation type) {
		super();
		this.coordinate = coordinate;
		this.type = type;
	}

	@Override
	public Orientation getOrientation() {
		return this.type;
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

}
