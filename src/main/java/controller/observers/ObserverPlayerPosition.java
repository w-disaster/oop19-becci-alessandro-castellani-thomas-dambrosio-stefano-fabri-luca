package controller.observers;

import model.roundenvironment.coordinate.Coordinate;
import view.game.ViewLogic;

public class ObserverPlayerPosition implements Observer {

    private final ViewLogic view;

    public ObserverPlayerPosition(final ViewLogic view) {
        this.view = view;
    }

    @Override
    public final void update(final Object position, final String player) {
        this.view.move((Coordinate) position, player);
    }

}
