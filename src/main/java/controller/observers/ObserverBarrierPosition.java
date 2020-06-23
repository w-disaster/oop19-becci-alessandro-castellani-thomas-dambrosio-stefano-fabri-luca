package controller.observers;

import model.roundenvironment.barriers.Barrier;
import view.game.ViewController;
import view.game.ViewLogic;

public class ObserverBarrierPosition implements Observer {

private ViewLogic view;
	
	public ObserverBarrierPosition(ViewLogic view) {
		this.view = view;
	}
	
	@Override
	public void update(Object barrier, String player) {
		this.view.drawBarrier((Barrier) barrier);
	}

}
