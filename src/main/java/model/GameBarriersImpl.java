package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBarriersImpl implements GameBarriers {

	private List<Barrier> barriers;
	
	public GameBarriersImpl() {
		super();
		this.barriers = new ArrayList<>();
	}

	public GameBarriersImpl(List<Barrier> barriers) {
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
	
}
