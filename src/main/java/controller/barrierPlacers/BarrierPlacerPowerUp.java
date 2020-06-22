package controller.barrierPlacers;

import java.util.Iterator;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.players.Player;


public class BarrierPlacerPowerUp<X extends RoundPUpEnvironment> extends BarrierPlacerImpl<RoundPUpEnvironment> {
	
	private Model<X> model;
	private Player currentPlayer;

	public BarrierPlacerPowerUp(Model<RoundPUpEnvironment> model, UIController view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
		this.currentPlayer = this.model.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer();
	}
	
	public void plusOneBarrier() {
		this.currentPlayer.setAvailableBarriers(this.currentPlayer.getAvailableBarriers()+1);
	}


}
