package controller;

import model.*;
import model.Barriers.BarrierType;

public class BarrierPlacerImpl implements BarrierPlacer {

	private StandardGame game = new StandardGameImpl();
	private Barriers barriers = new BarriersImpl();

	@Override
	public void placeBarrier(Pair<Integer, Integer> position, BarrierType type) {
		
	}

}
