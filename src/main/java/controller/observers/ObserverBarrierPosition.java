package controller.observers;

import guicontrollers.UIController;
import model.roundenvironment.barriers.Barrier;

public class ObserverBarrierPosition implements Observer {

private UIController view;
	
	public ObserverBarrierPosition(UIController view) {
		this.view = view;
	}
	
	@Override
	public void update(Object barrier, String player) {
		this.view.drawBarrier((Barrier) barrier);
	}

}
