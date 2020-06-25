package controller.genericmove.playermovers;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import view.game.ViewLogic;

/**
 * The Class PlayerMoverPowerUp.
 *
 * @param <X> the generic type
 * 
 * @author Stefano
 */
public class PlayerMoverPowerUp<X extends RoundPUpEnvironment> extends PlayerMoverImpl<RoundPUpEnvironment> {

	/**
	 * Instantiates a new player mover power up.
	 *
	 * @param model the model
	 * @param view the view
	 * @param iterRounds the iter rounds
	 */
	public PlayerMoverPowerUp(final Model<RoundPUpEnvironment> model, final ViewLogic view,
			final Iterator<RoundPUpEnvironment> iterRounds) {
		super(model, view, iterRounds);
	}

	/**
	 * Double move powerUp.
	 */
	public void doubleMove() {
		this.changeTurn();
	}
}