package controller.genericmove.playermovers;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import view.game.ViewLogic;


public class PlayerMoverPowerUp<X extends RoundPUpEnvironment> extends PlayerMoverImpl<RoundPUpEnvironment> {

	public PlayerMoverPowerUp(Model<RoundPUpEnvironment> model, ViewLogic view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
	}
	
	public void doubleMove() {
		this.changeTurn();
	}

}
