package controller;

/**
 * this class implements methods that BarrierPlacer and PlayerMover both need
 * 
 * @author Thomas
 */
public interface Move {

	/**
	 * change turn (implemented for n players)
	 */
	public void changeTurn();
	
}
