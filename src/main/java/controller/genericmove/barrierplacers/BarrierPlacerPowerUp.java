package controller.genericmove.barrierplacers;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.players.Player;
import view.game.ViewLogic;


public class BarrierPlacerPowerUp<X extends RoundPUpEnvironment> extends BarrierPlacerImpl<RoundPUpEnvironment> {
	
	private Model<RoundPUpEnvironment> model;
	private Player currentPlayer;

	public BarrierPlacerPowerUp(Model<RoundPUpEnvironment> model, ViewLogic view,
			Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
		this.model = model;
		this.currentPlayer = this.model.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer();
	}
	
	public void plusOneBarrier(Player player) {
		player.setAvailableBarriers(this.currentPlayer.getAvailableBarriers()+1);
	}


}
