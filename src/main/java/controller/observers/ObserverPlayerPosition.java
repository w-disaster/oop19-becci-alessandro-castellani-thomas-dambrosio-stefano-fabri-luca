package controller.observers;

import model.roundenvironment.coordinate.Coordinate;
import view.game.ViewLogic;

public class ObserverPlayerPosition implements Observer {

	private ViewLogic view;
	
	public ObserverPlayerPosition(ViewLogic view) {
		this.view = view;
	}
	
	@Override
	public void update(Object position, String player) {
		this.view.move((Coordinate) position, player);
	}

}
