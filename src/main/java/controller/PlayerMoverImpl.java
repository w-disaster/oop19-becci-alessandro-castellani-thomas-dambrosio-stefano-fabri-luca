package controller;

import model.*;
import model.Barrier.BarrierType;

public class PlayerMoverImpl extends MoveImpl implements PlayerMover {

	private StandardGame game = new StandardGameImpl();
	private GameBarriers barriers = new GameBarriersImpl();
	private Coordinate playerPosition = this.game.getCurrentPlayer().getCoordinate();
	private Coordinate newPosition;
	
	@Override
	public void movePlayer(Coordinate newPosition) {
		this.newPosition = newPosition; //so i don't need to pass at all private methods a parameter
		if (this.adjacent(this.playerPosition, this.newPosition) && this.noWall()) {
			this.playerPosition = newPosition;
			if (this.game.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println("Game Over! " + this.game.getCurrentPlayer() + " won!");
			}
			//change turn
		}
	}
	
	private boolean adjacent(Coordinate currentp, Coordinate newp) {
		if (Math.abs(currentp.getX() - newp.getX()) + Math.abs(currentp.getY() - newp.getY()) == 1) {
			return true;
		}
		return false;
	}
	
	private boolean noWall() {
		//i need to find in which direction the player wants to move in order to check if there's a wall
		if (this.newPosition.getX().equals(this.playerPosition.getX() + 1)) {
			if (this.barriers.contains(new BarrierImpl(this.playerPosition, BarrierType.VERTICAL))) {
				return false;
			}
		}
		if (this.newPosition.getX().equals(this.playerPosition.getX() - 1)) {
			if (this.barriers.contains(new BarrierImpl(new Coordinate(this.playerPosition.getX() - 1, this.playerPosition.getY()), BarrierType.VERTICAL))) {
				return false;
			}
		}
		if (this.newPosition.getY().equals(this.playerPosition.getY() + 1)) {
			if (this.barriers.contains(new BarrierImpl(this.playerPosition, BarrierType.HORIZONTAL))) {
				return false;
			}
		}
		if (this.newPosition.getY().equals(this.playerPosition.getY() - 1)) {
			if (this.barriers.contains(new BarrierImpl(new Coordinate(this.playerPosition.getX(), this.playerPosition.getY() - 1), BarrierType.HORIZONTAL))) {
				return false;
			}
		}
		return true;
	}
}
