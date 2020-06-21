package model.roundenvironment.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Node.Colour;

public class BarriersGraphImpl implements BarriersGraph {

	private List<Pair<Coordinate, Coordinate>> barriers;
	
	/**
	 * constructor of BarrierGraphImpl, given board dimension
	 * @param boardDimension
	 */
	public BarriersGraphImpl(int boardDimension) {
		this.barriers = new ArrayList<>();
		List<Coordinate> nodes = new ArrayList<>();
		for(int r = 0; r < boardDimension; r++) {
			for(int c = 0; c < boardDimension; c++) {
				nodes.add(new Coordinate(r, c));
			}
		}
		barriersGraphFromNodes(nodes);
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
					this.barriers.add(new Pair<>(n, adj));
				}
			}
		}
	}

	@Override
	public void add(Pair<Coordinate, Coordinate> barrier) {
		this.barriers.remove(barrier);
	}

	@Override
	public boolean containsBarrier(Pair<Coordinate, Coordinate> barrier) {
		return this.barriers.contains(barrier);
	}

	@Override
	public List<Pair<Coordinate, Coordinate>> getBarriersAsList() {
		return List.copyOf(this.barriers);
	}

	@Override
	public boolean bfs(Node source, int destination) {
		List<Node> list = new ArrayList<>();
		List<Pair<Node, Node>> edges = edgesFromBarriers();
		
		source.setColour(model.roundenvironment.graph.Node.Colour.GRAY);
		source.setDistance(Optional.of(0));
		list.add(source);
		
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
	
	private List<Pair<Node, Node>> edgesFromBarriers(){
		return this.barriers.stream()
				.map(p -> new Pair<Node, Node>(new NodeImpl(p.getX(), Optional.empty(), Colour.WHITE), 
						new NodeImpl(p.getY(), Optional.empty(), Colour.WHITE)))
				.collect(Collectors.toList());
	}
	
	private List<Node> adjNodes(List<Pair<Node, Node>> edges, Node node){
		return edges.stream()
				.filter(p -> p.getX().getCoordinate().equals(node.getCoordinate()) && p.getY().getcolour().equals(Colour.WHITE))
				.map(p -> p.getY())
				.collect(Collectors.toList());
	}
}
