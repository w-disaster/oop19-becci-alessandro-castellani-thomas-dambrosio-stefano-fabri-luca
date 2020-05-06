package model.roundenvironment.barriers;

import model.roundenvironment.coordinate.Coordinate;

public class BarrierImpl implements Barrier {

	private Coordinate coordinate;
	private BarrierType type;
	
	public BarrierImpl(Coordinate coordinate, BarrierType type) {
		super();
		this.coordinate = coordinate;
		this.type = type;
	}

	@Override
	public BarrierType getType() {
		return this.type;
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

}
