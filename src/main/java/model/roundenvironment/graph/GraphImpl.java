package model.roundenvironment.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Node.Colour;

public class GraphImpl<X> implements Graph<Coordinate> {

	private List<Pair<Coordinate, Coordinate>> edges;
	
	/**
	 * BarrierGraphImpl given board dimension
	 * @param boardDimension
	 */
	public GraphImpl(int boardDimension) {
		this.edges = new ArrayList<>();
		List<Coordinate> nodes = new ArrayList<>();
		for(int r = 0; r < boardDimension; r++) {
			for(int c = 0; c < boardDimension; c++) {
				nodes.add(new Coordinate(r, c));
			}
		}
		barriersGraphFromNodes(nodes);
	}
	
	/**
	 * BarrierGraphImpl given edges
	 * @param edges
	 */
	public GraphImpl(List<Pair<Coordinate, Coordinate>> edges) {
		this.edges = edges;
	}
	
	/**
	 * private method to build the graph given the nodes, that in this case are the board coordinates
	 * @param nodes
	 */
	private void barriersGraphFromNodes(List<Coordinate> nodes) {
		for(Coordinate n : nodes) {
			for(Coordinate adj : Stream.of(new Coordinate(n.getX() - 1, n.getY()), new Coordinate(n.getX() + 1, n.getY()), 
					new Coordinate(n.getX(), n.getY() - 1), new Coordinate(n.getX(), n.getY() + 1))
					.collect(Collectors.toList())) {
				if(nodes.contains(adj)) {
					this.edges.add(new Pair<>(n, adj));
				}
			}
		}
	}

	@Override
	public void remove(Pair<Coordinate, Coordinate> edge) {
		this.edges.remove(edge);
		this.edges.remove(new Pair<>(edge.getY(), edge.getX()));
	}

	@Override
	public boolean containsPath(Coordinate source, int destination) {
		List<Node> list = new ArrayList<>();
		List<Pair<Node, Node>> edges = edgesFromBarriers();
		
		Node s = new NodeImpl(source, Optional.of(0), Colour.GRAY);
		list.add(s);
		
		// computing BFS
		while (!list.isEmpty()) {
			Node u = list.remove(0);
			for(Node v : adjNodes(edges, u)) {
				if(v.getCoordinate().getX().equals(destination)) {
					return true;
				}
				v.setColour(model.roundenvironment.graph.Node.Colour.GRAY);
				v.setDistance(Optional.of(u.getDistance().get() + 1));
				list.add(v);
			}
			u.setColour(Colour.BLACK);
		}
		return false;
	}
	
	/**
	 * 
	 * @return edges from barriers
	 */
	private List<Pair<Node, Node>> edgesFromBarriers(){
		return this.edges.stream()
				.map(p -> new Pair<Node, Node>(new NodeImpl(p.getX(), Optional.empty(), Colour.WHITE), 
						new NodeImpl(p.getY(), Optional.empty(), Colour.WHITE)))
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
