package controller.genericmove.playermovers;

import java.util.Iterator;

import guicontrollers.UIController;
import model.Model;
import model.roundenvironment.RoundPUpEnvironment;


public class PlayerMoverPowerUp<X extends RoundPUpEnvironment> extends PlayerMoverImpl<RoundPUpEnvironment> {

	public PlayerMoverPowerUp(Model<RoundPUpEnvironment> model, UIController view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
	}
	
	public void doubleMove() {
		this.changeRound();
	}
	
	public void moveAndClimbABarrier() {
		
	}

}
