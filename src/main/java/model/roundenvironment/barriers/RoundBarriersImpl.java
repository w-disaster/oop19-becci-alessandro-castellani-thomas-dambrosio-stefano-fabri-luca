package model.roundenvironment.barriers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.coordinate.Coordinate;

/**
 * Round barriers class
 * @author luca
 *
 */
public class RoundBarriersImpl implements RoundBarriers {

	private List<Barrier> barriers;
	
	/**
	 * barriers for a new round
	 */
	public RoundBarriersImpl() {
		super();
		this.barriers = new ArrayList<>();
	}

	/**
	 * barriers from an existing round
	 * @param barriers
	 */
	public RoundBarriersImpl(final List<Barrier> barriers) {
		super();
		this.barriers = barriers;
	}

	@Override
	public void add(Barrier barrier) {
		this.barriers.add(barrier);
	}

	@Override
	public boolean contains(Barrier barrier) {
		return this.barriers.contains(barrier);
	}

	@Override
	public Barrier getBarrierFrom(Coordinate coordinate, Orientation orientation) {
		return this.barriers.stream()
				.filter(b -> b.getCoordinate().equals(coordinate) && b.getOrientation().equals(orientation))
				.collect(Collectors.toList())
				.get(0);
	}
	
}
