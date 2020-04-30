package controller;

import model.*;

public class PlayerMoverImpl implements PlayerMover {

	private StandardGame game = new StandardGameImpl();
	private Barriers barriers = new BarriersImpl();
	private Pair<Integer,Integer> playerPosition = this.game.getCurrentPlayer().getCoordinate();
	private Pair<Integer,Integer> newPosition;
	
	@Override
	public void movePlayer(Pair<Integer, Integer> newPosition) {
		this.newPosition = newPosition; //so i don't need to pass at all private methods a parameter
		if (this.adjacent() && this.noWall()) {
			this.playerPosition = newPosition;
			if (this.game.getCurrentPlayer().isWinner()) { //when the player change position i check if he won
				System.out.println("Game Over! " + this.game.getCurrentPlayer() + " won!");
			}
			this.changeTurn();
		}
	}
	
	private boolean adjacent() {
		//need implementation
		return true;
	}
	
	private boolean noWall() {
		//need implementation
		return true;
	}
	
	private void changeTurn() {
		//need implementation
	}
}
