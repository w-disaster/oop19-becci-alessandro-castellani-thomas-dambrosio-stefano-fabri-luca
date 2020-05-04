package controller;

import model.*;
import model.Barrier.BarrierType;

public class PlayerMoverImpl extends MoveImpl implements PlayerMover {

	private StandardGame game;
	private GameBarriers barriers;
	private Coordinate playerPosition;
	private Coordinate newPosition;
	
	public PlayerMoverImpl(StandardGame game, GameBarriers barriers) {
		super(game);
		this.barriers = barriers;
		this.playerPosition = this.game.getCurrentPlayer().getCoordinate();
	}
	
	@Override
	public void movePlayer(Coordinate newPosition) {
		this.newPosition = newPosition; //so i don't need to pass to all private methods a parameter
		if (this.adjacent() && this.noWall()) {
			this.playerPosition = newPosition;
			if (this.game.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println("Game Over! " + this.game.getCurrentPlayer() + " won!");
			}
			this.changeTurn();
		}
		System.out.println("Bad move! Still your turn!");
	}
	
	private boolean adjacent() {
		if (Math.abs(this.playerPosition.getX() - this.newPosition.getX()) + Math.abs(this.playerPosition.getY() - this.newPosition.getY()) == 1) {
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
