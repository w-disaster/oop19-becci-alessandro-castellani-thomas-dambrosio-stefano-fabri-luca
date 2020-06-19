package controllers;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

import controller.StandardGameControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;

public class LogicImpl implements Logic{
	
	private Optional<String> player1;
	private Optional<String> player2;

	private Map<Coordinate, BorderPane> gridMap;
	
	//0 for vertical, 1 for horizontal
	private Optional<Integer> selectedBarrier;

	public LogicImpl() {
    	gridMap = new HashMap<Coordinate, BorderPane>();
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
        System.out.println(position);
        System.out.println(pane);
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
		} else if (type.equals("horizontal")) {
			this.selectedBarrier = Optional.of(1);
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
    	Rectangle horizontalBarrier = new Rectangle(barrierSize.getKey()*8, barrierSize.getValue());
    	horizontalBarrier.getStyleClass().add("Barrier");
    	if (barrier.getOrientation().equals(Orientation.HORIZONTAL)) {
    		if (player.equals(this.player1.get())) {
    			horizontalBarrier.setFill(Color.BLUE);
    			selected.setBottom(horizontalBarrier);
    			BorderPane.setAlignment(horizontalBarrier, Pos.CENTER);
    		} else {
    			horizontalBarrier.setFill(Color.RED);
    			selected.setBottom(horizontalBarrier);	
    			BorderPane.setAlignment(horizontalBarrier, Pos.CENTER);
    		}
    	} else {
    		if (player.equals(this.player1.get())) {
    			verticalBarrier.setFill(Color.BLUE);
    			selected.setRight(verticalBarrier);
    			BorderPane.setAlignment(verticalBarrier, Pos.CENTER);
    		} else {
    			verticalBarrier.setFill(Color.RED);
    			selected.setRight(verticalBarrier);
    			BorderPane.setAlignment(verticalBarrier, Pos.CENTER);
    		}
    	}	
    }
}
