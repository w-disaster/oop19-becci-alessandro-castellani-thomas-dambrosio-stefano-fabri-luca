package view.game;

import java.util.List;
import java.util.Optional;

import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;

/**
 * The Interface for the view controller.
 * 
 * @author Stefano
 */
public interface ViewLogic {

	void startGame();

	void setPlayer(Optional<Pair<String, String>> result);

	BorderPane addPaneLogic(Coordinate position);

	void setUpClickHandler(Coordinate position);

	void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2);

	void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2, List<Barrier> barrierList);

	void move(Coordinate position, String player);

	void changeSelectedLabel(String player);

	void setSelectedBarrier(String type);

	void drawBarrier(Barrier barrier);

	void updateBarriersNumber(String nickname, int availableBarriers);

	void drawPowerUps(List<PowerUp> powerUpsAsList);

	void deletePowerUp(PowerUp p);

	void endRound(String winner);

	void endGame(String winner);

	void saveGame();

	void drawTextLogic(String textToDisplay);

}
