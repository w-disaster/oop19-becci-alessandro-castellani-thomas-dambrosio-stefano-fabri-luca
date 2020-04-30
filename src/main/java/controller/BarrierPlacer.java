package controller;

import model.*;
import model.Barriers.BarrierType;

public interface BarrierPlacer {

	public void placeBarrier(Pair<Integer,Integer> position, BarrierType type);
	
}
