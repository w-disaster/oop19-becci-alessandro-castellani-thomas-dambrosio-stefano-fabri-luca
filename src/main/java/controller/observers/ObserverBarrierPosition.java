package controller.observers;

import model.roundenvironment.barriers.Barrier;
import view.game.ViewLogic;

public class ObserverBarrierPosition implements Observer {

private final ViewLogic view;
	
	public ObserverBarrierPosition(final ViewLogic view) {
		this.view = view;
	}
	
	@Override
	public void update(final Object barrier, final String player) {
		this.view.drawBarrier((Barrier) barrier);
	}

}
