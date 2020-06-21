package controller.barrierPlacers;

import java.util.Iterator;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

public class BarrierPlacerPowerUp extends BarrierPlacerImpl<RoundPUpEnvironment> {

	public BarrierPlacerPowerUp(Model<RoundPUpEnvironment> model, UIController view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
	}


}
