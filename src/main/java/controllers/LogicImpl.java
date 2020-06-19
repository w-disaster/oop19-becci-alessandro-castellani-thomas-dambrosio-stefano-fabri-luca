package controllers;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

import controller.StandardGameControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

public class LogicImpl implements Logic{
	
	private UIController view;
	private Optional<String> player1;
	private Optional<String> player2;

	private Map<Coordinate, BorderPane> gridMap;
	
	//0 for vertical, 1 for horizontal
	private Optional<Integer> selectedBarrier;

	public LogicImpl(UIController view) {
		this.view = view;
    	this.gridMap = new HashMap<Coordinate, BorderPane>();
    	this.selectedBarrier = Optional.empty();
	}
	
	@Override
	public void setPlayers(Optional<String> player1, Optional<String> player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	@Override
	public BorderPane addPaneLogic(Coordinate position, StandardGameControllerImpl controller) {
		BorderPane pane = new BorderPane();
        pane.setOnMouseClicked(e -> {
        	this.setUpClickHandler(position, controller);
        });
        this.gridMap.put(position, pane);        
		return pane;
	}

	@Override
	public void setUpClickHandler(Coordinate position, StandardGameControllerImpl controller) {
        System.out.printf("Mouse clicked cell " + position.toString() + "\n");
        if (this.selectedBarrier.isEmpty()) {
        	controller.movePlayer(position);
        } else {
        	if(this.selectedBarrier.get().equals(0)) {
        		controller.placeBarrier(position, Orientation.VERTICAL);
        		System.out.printf("Barrier placement request: " + position.toString() + " Orientation: " + Orientation.VERTICAL + "\n");
        		
        	} else {
        		controller.placeBarrier(position, Orientation.HORIZONTAL);            		
        		System.out.printf("Barrier placement request: " + position.toString() + " Orientation: " + Orientation.HORIZONTAL + "\n");
        	}
        	this.selectedBarrier = Optional.empty();
        }
	}
	
	public void setSelectedBarrier(String type) {
		if (type.equals("vertical")) {
			this.selectedBarrier = Optional.of(0);
			this.drawTextLogic("barrier");
		} else if (type.equals("horizontal")) {
			this.selectedBarrier = Optional.of(1);
			this.drawTextLogic("barrier");
		}
	}
	
    public void clearGrid() {
    	this.gridMap.entrySet().forEach(e -> e.getValue().getChildren().remove(0, e.getValue().getChildren().size()));
    }
    
    public BorderPane getPaneByPosition(Coordinate position) {
    	return this.gridMap.get(position);
    }
    
    public void drawBarrierLogic(Barrier barrier, String player) {
    	BorderPane selected = this.gridMap.get(barrier.getCoordinate());
    	Pair<Double, Double> barrierSize = new Pair<>(selected.getWidth()/10, selected.getHeight()/10);
    	Rectangle verticalBarrier = new Rectangle(barrierSize.getKey(), barrierSize.getValue()*8);
    	verticalBarrier.getStyleClass().add("Barrier");
    	verticalBarrier.setFill(Color.ORANGE);
    	Rectangle horizontalBarrier = new Rectangle(barrierSize.getKey()*8, barrierSize.getValue());
    	horizontalBarrier.getStyleClass().add("Barrier");
    	horizontalBarrier.setFill(Color.ORANGE);
    	if (barrier.getOrientation().equals(Orientation.HORIZONTAL)) {
    		selected.setBottom(horizontalBarrier);
    		BorderPane.setAlignment(horizontalBarrier, Pos.CENTER);
    	} else if (barrier.getOrientation().equals(Orientation.VERTICAL)) {
    		selected.setRight(verticalBarrier);
    		BorderPane.setAlignment(verticalBarrier, Pos.CENTER);
    	}	
    }

	@Override
	public void drawTextLogic(String textToDisplay) {
    	String moveTutorial = "- Benvenuto su Quoridor! \n- Clicca su una casella adiacente alla tua per muovere la pedina\n"
    			+ "- Clicca su una barriera per posizionarla\n"
    			+ "- Puoi saltare l'avversario quando e` di fronte a te\n";
    	String barrierTutorial = "Per posizionare la barriera, clicca la casella dove vuoi posizionarla: \n"
    			+ "- La barriera verticale sara` posizionata a destra e nella cassela in basso\n"
    			+ "- La barriera orizzontale sara` piazzata in basso e nella casella a destra\n";
    	switch(textToDisplay) {
    		case "start" :
    			this.view.drawText(moveTutorial);
    			break;
    		case "barrier" :
    			this.view.drawText(barrierTutorial);		
    			break;
    		default :
    			this.view.drawText(textToDisplay);
    	}		
		
	}
}
