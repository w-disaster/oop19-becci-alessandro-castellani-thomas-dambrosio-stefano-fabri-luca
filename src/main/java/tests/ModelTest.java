package tests;

import java.util.stream.Collectors;

import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.coordinate.Coordinate;

public class ModelTest {

	public static void main() {
		containsBarrierTypeIndipendent(new RoundBarriersImpl(), new Coordinate(0, 0), Barrier.Orientation.HORIZONTAL);
	}
	
	private static boolean containsBarrierTypeIndipendent(RoundBarriers roundBarriers, Coordinate coordinate, Orientation orientation) {
		return roundBarriers.getBarriersAsList().stream()
				.filter(b -> b.getCoordinate().equals(coordinate) && b.getOrientation().equals(orientation))
				.collect(Collectors.toList())
				.size() > 0;
	}
	
}
