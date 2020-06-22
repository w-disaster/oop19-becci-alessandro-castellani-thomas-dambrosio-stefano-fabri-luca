package tests;

//import static org.junit.Assert.*;
import model.Model;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.BarriersGraph;
import model.roundenvironment.graph.BarriersGraphImpl;

public class ModelTest {

	//@org.junit.Test
	/*public void checkPath() {
		BarriersGraph barriers = new BarriersGraphImpl(Model.BOARD_DIMENSION);*/
		
		/* I add all possible horizontal barriers between line 3 and line 4. The graph is bidirectional
		 * then I must delete 2 edges for each barrier.
		 * Note: adding those barriers imply there's not a path to the line 0 for all coordinates with line >= 4.
		 */
		/*for(int i = 0; i < Model.BOARD_DIMENSION; i++) {
			barriers.add(new Pair<>(new Coordinate(4, i), new Coordinate(3, i)));
			barriers.add(new Pair<>(new Coordinate(3, i), new Coordinate(4, i)));
		}*/
		
		/*assertTrue(barriers.containsPath(new Coordinate(3, 5), 0));
		assertFalse(barriers.containsPath(new Coordinate(4, 4), 0));*/
	//}
	
}
