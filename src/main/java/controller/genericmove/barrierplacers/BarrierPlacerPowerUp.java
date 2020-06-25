package controller.genericmove.barrierplacers;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.players.Player;
import view.game.ViewLogic;

/**
 * The Class BarrierPlacer with a function for powerUp.
 *
 * @param <X> the generic type
 * 
 * @author Stefano
 */
public class BarrierPlacerPowerUp<X extends RoundPUpEnvironment> extends BarrierPlacerImpl<RoundPUpEnvironment> {

    private Model<RoundPUpEnvironment> model;
	private Player currentPlayer;

	/**
	 * Instantiates a new barrier placer power up.
	 *
	 * @param model the model
	 * @param view the view
	 * @param iterRounds the iterator of rounds
	 */
	public BarrierPlacerPowerUp(final Model<RoundPUpEnvironment> model, final ViewLogic view,
		final Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
		this.model = model;
		this.currentPlayer = this.model.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer();
	}

	/**
	 * Adds a barrier to the given player.
	 *
	 * @param player the player
	 */
	public void plusOneBarrier(final Player player) {
		player.setAvailableBarriers(this.currentPlayer.getAvailableBarriers() + 1);
	}
}
