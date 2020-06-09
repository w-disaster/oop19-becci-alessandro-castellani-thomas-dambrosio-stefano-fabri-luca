package model.roundenvironment.barriers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundBarriersImpl implements RoundBarriers {

	private List<Barrier> barriers;
	
	public RoundBarriersImpl() {
		super();
		this.barriers = new ArrayList<>();
	}

	public RoundBarriersImpl(List<Barrier> barriers) {
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
