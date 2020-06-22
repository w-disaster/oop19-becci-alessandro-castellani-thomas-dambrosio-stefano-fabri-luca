package tests;

import model.Model;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.graph.GraphImpl;


public class ModelTest {

	
	public void checkPath() {
		Graph<Coordinate> barriers = new GraphImpl<>(Model.BOARD_DIMENSION);
		
		/* I add all possible horizontal barriers between line 3 and line 4. The graph is bidirectional
		 * then I must delete 2 edges for each barrier.
		 * Note: adding those barriers imply there's not a path to the line 0 for all coordinates with line >= 4.
		 */
		for(int i = 0; i < Model.BOARD_DIMENSION; i++) {
			barriers.remove(new Pair<>(new Coordinate(4, i), new Coordinate(3, i)));
		}
		
		System.out.println(barriers.containsPath(new Coordinate(3, 5), 0));
		System.out.println(barriers.containsPath(new Coordinate(4, 4), 0));
	}
	
}
