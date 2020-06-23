package tests;

import java.util.List;

import model.Model;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.graph.BarriersGraph;


public class ModelTest {

	
	public static final void main(String args[]) {
		Graph<Coordinate> graph = new BarriersGraph<>(Model.BOARD_DIMENSION);
		
		/* I add all possible horizontal barriers between line 3 and line 4 except that in column 0. The graph is bidirectional
		 * then 2 edges for each barrier will be removed.
		 */
		for(int i = 1; i < Model.BOARD_DIMENSION; i++) {
			graph.remove(new Pair<>(new Coordinate(4, i), new Coordinate(3, i)));
		}
		
		//There must be a path
		System.out.println(graph.containsPath(List.of(), new Coordinate(0, 4), Model.BOARD_DIMENSION - 1));
		
		//Note: adding those barriers imply there's not a path to the line 0 for all coordinates with line >= 4.
		System.out.println(graph.containsPath(graph.barriersAsEdgesToRemove(List.of(new BarrierImpl(new Coordinate(4, 0), Orientation.HORIZONTAL, Piece.HEAD),
				new BarrierImpl(new Coordinate(3, 0), Orientation.HORIZONTAL, Piece.TAIL))), new Coordinate(0, 4), Model.BOARD_DIMENSION - 1));   
		
	}
	
}
