package controller.genericmove.barrierplacers;

import java.util.Iterator;

import guicontrollers.UIController;
import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.players.Player;


public class BarrierPlacerPowerUp<X extends RoundPUpEnvironment> extends BarrierPlacerImpl<RoundPUpEnvironment> {
	
	private Model<RoundPUpEnvironment> model;
	private Player currentPlayer;

	public BarrierPlacerPowerUp(Model<RoundPUpEnvironment> model, UIController view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
		this.model = model;
		this.currentPlayer = this.model.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer();
	}
	
	public void plusOneBarrier() {
		this.currentPlayer.setAvailableBarriers(this.currentPlayer.getAvailableBarriers()+1);
	}


}
