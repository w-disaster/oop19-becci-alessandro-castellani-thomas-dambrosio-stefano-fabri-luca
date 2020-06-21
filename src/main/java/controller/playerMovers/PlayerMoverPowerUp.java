package controller.playerMovers;

import java.util.Iterator;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundPUpEnvironment;


public class PlayerMoverPowerUp extends PlayerMoverImpl<RoundPUpEnvironment> {

	public PlayerMoverPowerUp(Model<RoundPUpEnvironment> model, UIController view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
	}

}
