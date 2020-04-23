package model;

import java.util.List;

public interface GridBarriers {

	/**
	 * type of the barriers
	 * @author luca
	 *
	 */
	public enum BarrierType {
		HORIZONTAL, VERTICAL
	}
			
	/**
	 * 
	 * @return the list of the barriers
	 */
	List<Pair<Pair<Integer, Integer>, BarrierType>> getBarriersList();	
	
	
}
