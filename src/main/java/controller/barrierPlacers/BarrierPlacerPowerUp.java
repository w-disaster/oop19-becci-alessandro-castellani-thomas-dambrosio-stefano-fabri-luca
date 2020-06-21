package controller.barrierPlacers;

import java.util.Iterator;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundPUpEnvironment;


public class BarrierPlacerPowerUp extends BarrierPlacerImpl<RoundPUpEnvironment> {

	public BarrierPlacerPowerUp(Model<RoundPUpEnvironment> model, UIController view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
	}


}
