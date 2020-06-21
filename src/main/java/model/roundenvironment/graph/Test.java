package model.roundenvironment.graph;

import java.util.Optional;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Node.Colour;

public class Test {

	public static void main(String[] args) {
		BarriersGraph barriers = new BarriersGraphImpl(9);
		
		barriers.add(new Pair<>(new Coordinate(4, 0), new Coordinate(3, 0)));
		barriers.add(new Pair<>(new Coordinate(4, 1), new Coordinate(3, 1)));
		barriers.add(new Pair<>(new Coordinate(4, 2), new Coordinate(3, 2)));
		barriers.add(new Pair<>(new Coordinate(4, 3), new Coordinate(3, 3)));
		barriers.add(new Pair<>(new Coordinate(4, 4), new Coordinate(3, 4)));
		barriers.add(new Pair<>(new Coordinate(4, 5), new Coordinate(3, 5)));
		barriers.add(new Pair<>(new Coordinate(4, 6), new Coordinate(3, 6)));
		barriers.add(new Pair<>(new Coordinate(4, 7), new Coordinate(3, 7)));

		
		System.out.println(barriers.bfs(new NodeImpl(new Coordinate(6, 4), Optional.empty(), Colour.WHITE), 0));
	}
	
}
