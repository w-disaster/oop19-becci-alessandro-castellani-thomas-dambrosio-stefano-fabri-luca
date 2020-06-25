package controller;

import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

/**
 * Controller class, it contains basic methods (except newGame, different implementation for each controller).
 * 
 */
public interface GameController {

    void invokeMove(Coordinate position);

    void invokePlace(Coordinate position, Orientation orientation);

    void nextRound();

    void saveGame();

    void loadGame();
}
