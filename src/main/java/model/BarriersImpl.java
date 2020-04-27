package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarriersImpl implements Barriers{

	private List<Pair<Coordinate, BarrierType>> barriers;
	
	public BarriersImpl() {
		super();
		this.barriers = new ArrayList<>();
	}

	public BarriersImpl(List<Pair<Coordinate, BarrierType>> barriers) {
		super();
		this.barriers = barriers;
	}
	
	@Override
	public void add(Coordinate coordinate, BarrierType type) {
		this.barriers.add(new Pair<>(coordinate, type));
	}
	
	@Override
	public List<Pair<Coordinate, BarrierType>> getBarriersList() {
		return Collections.unmodifiableList(this.barriers);
	}

}
