package model.roundenvironment.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Node.Colour;

/**
 * Bidirectional graph that models barriers as a pair of coordinates
 * @author luca
 *
 * @param <X>
 */
public class BarriersGraph<X> implements Graph<Coordinate> {

	private List<Pair<Coordinate, Coordinate>> edges;
	
	/**
	 * BarrierGraphImpl given board dimension
	 * @param boardDimension
	 */
	public BarriersGraph(int boardDimension) {
		this.edges = new ArrayList<>();
		List<Coordinate> nodes = new ArrayList<>();
		for(int r = 0; r < boardDimension; r++) {
			for(int c = 0; c < boardDimension; c++) {
				nodes.add(new Coordinate(c, r));
			}
		}
		edgesFromNodes(nodes);
	}
	
	/**
	 * BarrierGraphImpl given edges
	 * @param edges
	 */
	public BarriersGraph(List<Pair<Coordinate, Coordinate>> edges) {
		this.edges = edges;
	}

	@Override
	public void remove(Pair<Coordinate, Coordinate> edge) {
		this.edges.remove(edge);
		this.edges.remove(new Pair<>(edge.getY(), edge.getX()));
	}

	@Override
	public List<Pair<Coordinate, Coordinate>> getEdges() {
		return this.edges;
	}
	
	@Override
	public boolean containsPath(List<Pair<Coordinate, Coordinate>> edgesToRemove, Coordinate source, int destination) {
		List<Node> list = new ArrayList<>();
		
		List<Pair<Node, Node>> edges = edgesOfNodes(this.edges.stream()
				.filter(e -> !edgesToRemove.contains(e))
				.collect(Collectors.toList()));
		
		Node s = new NodeImpl(source, Colour.GRAY);
		list.add(s);
		
		// computing BFS
		while (!list.isEmpty()) {
			Node u = list.remove(0);
			for(Node v : adjNodes(edges, u)) {
				if(v.getCoordinate().getY().equals(destination)) {
					return true;
				}
				v.setColour(model.roundenvironment.graph.Node.Colour.GRAY);
				list.add(v);
			}
			u.setColour(Colour.BLACK);
		}
		return false;
	}
	
	@Override
	public List<Pair<Coordinate, Coordinate>> barriersAsEdgesToRemove(List<Barrier> barriers) {
		List<Pair<Coordinate, Coordinate>> edgesToRemove = new ArrayList<>();
		
		for(Barrier b : barriers) {
			Integer x = b.getCoordinate().getX();
			Integer y = b.getCoordinate().getY();
			
			if(b.getOrientation().equals(Orientation.HORIZONTAL)) {
				edgesToRemove.add(new Pair<>(new Coordinate(x, y), new Coordinate(x, y + 1)));
				edgesToRemove.add(new Pair<>(new Coordinate(x, y + 1), new Coordinate(x, y)));
			} else {
				edgesToRemove.add(new Pair<>(new Coordinate(x, y), new Coordinate(x + 1, y)));
				edgesToRemove.add(new Pair<>(new Coordinate(x + 1, y), new Coordinate(x, y)));
			}
		}
		
		edgesToRemove.forEach(e -> System.out.println(e));
		
		return edgesToRemove;
	}
	
	/**
	 * private method to build the graph given the nodes, that in this case are the board coordinates
	 * @param nodes
	 */
	private void edgesFromNodes(List<Coordinate> nodes) {
		for(Coordinate n : nodes) {
			Integer x = n.getX();
			Integer y = n.getY();
			for(Coordinate adj : Stream.of(new Coordinate(x - 1, y), new Coordinate(x + 1, y), 
					new Coordinate(x, y - 1), new Coordinate(x, y + 1))
					.collect(Collectors.toList())) {
				if(nodes.contains(adj)) {
					this.edges.add(new Pair<>(n, adj));
				}
			}
		}
	}
	
	/**
	 * 
	 * @return edges of nodes from edges of coordinates
	 */
	private static List<Pair<Node, Node>> edgesOfNodes(List<Pair<Coordinate, Coordinate>> edges){
		return 	edges.stream()
				.map(p -> new Pair<Node, Node>(new NodeImpl(p.getX(), Colour.WHITE), 
						new NodeImpl(p.getY(), Colour.WHITE)))
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param edges
	 * @param node
	 * @return adjacent nodes of node
	 */
	private List<Node> adjNodes(List<Pair<Node, Node>> edges, Node node){
		return edges.stream()
				.filter(p -> p.getX().getCoordinate().equals(node.getCoordinate()) && p.getY().getcolour().equals(Colour.WHITE))
				.map(p -> p.getY())
				.collect(Collectors.toList());
	}

}
