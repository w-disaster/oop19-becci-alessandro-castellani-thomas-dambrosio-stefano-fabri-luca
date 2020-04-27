package model;

import java.util.List;

public interface Barriers {

	/**
	 * type of the barriers
	 * @author luca
	 *
	 */
	public enum BarrierType {
		HORIZONTAL, VERTICAL
	}
	
	/**
	 * adds a barrier in the List
	 * @param coordinate
	 * @param type
	 */
	void add(Coordinate coordinate, BarrierType type);
			
	/**
	 * 
	 * @return the list of the barriers
	 */
	List<Pair<Coordinate, BarrierType>> getBarriersList();		
	
}
