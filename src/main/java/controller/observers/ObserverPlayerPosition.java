package controller.observers;

import guicontrollers.UIController;
import model.roundenvironment.coordinate.Coordinate;

public class ObserverPlayerPosition implements Observer {

	private UIController view;
	
	public ObserverPlayerPosition(UIController view) {
		this.view = view;
	}
	
	@Override
	public void update(Object position, String player) {
		this.view.move((Coordinate) position, player);
	}

}
