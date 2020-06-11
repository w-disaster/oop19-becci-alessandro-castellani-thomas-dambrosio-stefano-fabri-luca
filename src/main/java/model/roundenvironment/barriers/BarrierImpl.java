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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BarrierImpl other = (BarrierImpl) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
