package controllers;

import java.util.List;
import java.util.Optional;

import controller.GameController;
import controller.StandardGameControllerImpl;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;

public interface Logic {
	
	public BorderPane addPaneLogic(Coordinate position, GameController controller);
	
	public void setUpClickHandler(Coordinate position, GameController controller);
	
	public void setSelectedBarrier(String type);
	
	public void clearGrid();

	public void drawBarriersOnLoad(List<Barrier> barrier);
	
	public BorderPane getPaneByPosition(Coordinate position);

	public void setPlayers(Optional<String> player1, Optional<String> player2);

	public void drawBarrierLogic(Barrier barrier);

	public void drawTextLogic(String textToDisplay);

	public void drawPowerUps(List<PowerUp> powerUpsAsList);



}
