package view.game;

import java.util.List;
import java.util.Optional;

import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;

public interface ViewLogic {
	
	public void startGame();
	
	public void setPlayer(Optional<Pair<String, String>> result);
	
	public BorderPane addPaneLogic(Coordinate position);
	
	public void setUpClickHandler(Coordinate position);
	
	public void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2);
	
	public void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2, List<Barrier> barrierList);
	
	public void clearGrid();
	
	public void move(Coordinate position, String player);
	
	public void changeSelectedLabel(String player);
	
	public void setSelectedBarrier(String type);
	
	public void drawBarrier(Barrier barrier);
	
	public void drawBarriersOnLoad(List<Barrier> barrier);

	public void updateBarriersNumber(String nickname, int availableBarriers);
	
	public void drawPowerUps(List<PowerUp> powerUpsAsList);

	public void deletePowerUp(PowerUp p);
	
	public void endRound(String winner);
	
	public void endGame(String winner);
	
	public void saveGame();

	public void drawTextLogic(String textToDisplay);



}
