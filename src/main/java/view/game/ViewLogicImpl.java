package view.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.GameController;
import controller.PowerUpGameController;
import controller.PowerUpGameControllerImpl;
import controller.StandardGameController;
import controller.StandardGameControllerImpl;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;
import view.menu.MenuController;
import view.menu.MenuController.GameStatus;
import view.scenechanger.ScenesItem;

public class ViewLogicImpl implements ViewLogic{
	
	private GameController controller;
	
	private ViewController view;

	private Map<Coordinate, BorderPane> gridMap;
	
	private List<Rectangle> verticalBarrierList;
	private List<Rectangle> horizontalBarrierList;

	private Optional<Integer> selectedBarrier;

	private String player1;
	private String player2;

	public ViewLogicImpl(ViewController viewController) {
		this.view = viewController;
		if (MenuController.gameStatus.equals(GameStatus.NORMAL)
				|| MenuController.gameStatus.equals(GameStatus.LOADNORMAL)) {
			this.controller = new StandardGameControllerImpl(this);						
		} else if (MenuController.gameStatus.equals(GameStatus.POWERUP)
				|| MenuController.gameStatus.equals(GameStatus.LOADPOWERUP)) {
			this.controller = new PowerUpGameControllerImpl(this);				
		}
    	this.gridMap = new HashMap<Coordinate, BorderPane>();
    	this.verticalBarrierList = new ArrayList<>();
    	this.horizontalBarrierList = new ArrayList<>();
    	this.selectedBarrier = Optional.empty();
	}
	
	/**
	 * Starts the game in the controller.
	 */
	public void startGame() {
	    switch(MenuController.gameStatus) {
		case LOADNORMAL:
			this.controller.loadGame();
			break;
		case LOADPOWERUP:
			this.controller.loadGame();
			break;
		case NORMAL:
			((StandardGameController) this.controller).newStandardGame(player1, player2);	    
			break;
		case POWERUP:
			((PowerUpGameController) this.controller).newPowerUpGame(player1, player2);	    	
			break;
		default:
			break;
	    }
	}
	
	/**
	 * Set the players nicknames.
	 * 
	 * @param Optional<Pair<String, String>> An optional containing a pair with nicknames
	 * 
	 */
	@Override
	public void setPlayer(Optional<Pair<String, String>> result) {
		// If you leave it empty it automatically set default nicknames
    	if (result.get().getKey().equals("")) {
    		this.player1 = "Player 1";
    	} else {
    		this.player1 = result.get().getKey();    		
    	}
    	
    	if (result.get().getValue().equals("")) {
    		this.player2 = "Player 2";
    	} else {
    		this.player2 = result.get().getValue();    		
    	}
    	
    	// Nicknames can't be the same
    	if (player1.equals(player2)) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("ERROR");
    		alert.setContentText("You can't use the same name!");
    		alert.showAndWait();
    		this.view.returnToMainMenu();
    	}
    	this.view.setNicknames(player1, player2);
	}

	/**
	 * Adds the pane logic.
	 *
	 * @param position the position
	 * @return the pane to add to the grid
	 */
	@Override
	public BorderPane addPaneLogic(Coordinate position) {
		BorderPane pane = new BorderPane();
        pane.setOnMouseClicked(e -> {
        	this.setUpClickHandler(position);
        });
        this.gridMap.put(position, pane);        
		return pane;
	}
	

	/**
	 * Sets the up click handler.
	 *
	 * @param position the position in which the click handler will be setted
	 */
	@Override
	public void setUpClickHandler(Coordinate position) {
        //System.out.printf("Mouse clicked cell " + position.toString() + "\n");
        if (this.selectedBarrier.isEmpty()) {
        	this.controller.movePlayer(position);
        } else {
        	if(this.selectedBarrier.get().equals(0)) {
        		this.controller.placeBarrier(position, Orientation.VERTICAL);
        		//System.out.printf("Barrier placement request: " + position.toString() + " Orientation: " + Orientation.VERTICAL + "\n");
        		
        	} else {
        		controller.placeBarrier(position, Orientation.HORIZONTAL);            		
        		//System.out.printf("Barrier placement request: " + position.toString() + " Orientation: " + Orientation.HORIZONTAL + "\n");
        	}
        	this.selectedBarrier = Optional.empty();
        }
	}
	
	/**
	 * Clears the grid and set player in the given coordinate, it also update barriers number
	 *
	 * @param player1pos the player 1 position
	 * @param player2pos the player 2 position
	 * @param barriersP1 the barrier number of player 1
	 * @param barriersP2 the barrier number of player 2
	 */
	public void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2) {
		this.clearGrid();
		this.view.setPlayerInPane(this.gridMap.get(player1pos), this.player1);
		this.view.setPlayerInPane(this.gridMap.get(player2pos), this.player2);
		this.view.updateBarriersNumber(player1, barriersP1);
		this.view.updateBarriersNumber(player2, barriersP2);
	}
	
	/**
	 * Same as setup grid but with a list of barriers to draw in the grid.
	 *
	 * @param player1pos the player 1 position
	 * @param player2pos the player 2 position
	 * @param barriersP1 the barrier number of player 1
	 * @param barriersP2 the barrier number of player 2
	 * @param barrierList the barrier list
	 */
	public void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2, List<Barrier> barrierList) {
		this.setupGrid(player1pos, player2pos, barriersP1, barriersP2);
		this.drawBarriersOnLoad(barrierList);
	}
	
	/**
	 * Clears the grid.
	 */
	public void clearGrid() {
		this.gridMap.entrySet().forEach(e -> e.getValue().getChildren().remove(0, e.getValue().getChildren().size()));
	}
	
    /**
     * Move the player in the given position.
     *
     * @param position the position
     * @param player the player
     */
    public void move(Coordinate position, String player) {
    	this.view.setPlayerInPane(this.gridMap.get(position), player);
    }
    
    /**
     * Change selected label.
     *
     * @param player the current player 
     */
    @Override
    public void changeSelectedLabel(String player) {
    	this.view.changeSelectedLabel(player);
    }
    
    /**
     * Sets the selected barrier, it is used to get which type of barrier has been clicked
     *
     * @param type the selected barrier type, 
     */
    public void setSelectedBarrier(String type) {
    	if (type.equals("vertical")) {
    		this.selectedBarrier = Optional.of(0);
    	} else if (type.equals("horizontal")) {
    		this.selectedBarrier = Optional.of(1);
    	}
    	this.drawTextLogic("barrier");
    }
    
    /**
     * Draw a barrier/
     *
     * @param barrier the barrier to draw
     */
    public void drawBarrier(Barrier barrier) {
    	BorderPane selected = this.gridMap.get(barrier.getCoordinate());
    	// barrier styling
    	Rectangle verticalBarrier = new Rectangle();
    	verticalBarrier.getStyleClass().add("Barrier");
    	verticalBarrier.setFill(Color.ORANGE);
    	Rectangle horizontalBarrier = new Rectangle();
    	horizontalBarrier.getStyleClass().add("Barrier");
    	horizontalBarrier.setFill(Color.ORANGE);
    	
    	if (barrier.getOrientation().equals(Orientation.HORIZONTAL)) {
    		selected.setBottom(horizontalBarrier);
    		BorderPane.setAlignment(horizontalBarrier, Pos.CENTER);
    		this.horizontalBarrierList.add(horizontalBarrier);
    	} else if (barrier.getOrientation().equals(Orientation.VERTICAL)) {
    		selected.setRight(verticalBarrier);
    		BorderPane.setAlignment(verticalBarrier, Pos.CENTER);
    		this.verticalBarrierList.add(verticalBarrier);
    	}
    	this.setCorrectBarrierSize();
    }
    
    /**
     * Sets the correct barrier size.
     */
    public void setCorrectBarrierSize() {
    	Platform.runLater(new Runnable() {
    		
		    @Override
    		public void run() {
				Double dimensions = gridMap.get(new Coordinate(0,0)).getHeight()/10;
				for (Rectangle b : verticalBarrierList) {
					b.setHeight(dimensions*8);				
					b.setWidth(dimensions);					
				}
				for (Rectangle b : horizontalBarrierList) {
					b.setHeight(dimensions);				
					b.setWidth(dimensions*8);							
				}	
			}
    	});
    }
    
    /**
     * Draw barriers on load.
     *
     * @param barrierList the barrier list to draw
     */
    public void drawBarriersOnLoad(List<Barrier> barrierList) {
    	for (Barrier barrier : barrierList) {
    		this.drawBarrier(barrier);
    	}
    }
    
    /**
     * Update barriers number.
     *
     * @param player the player
     * @param barriersNumber the barriers number
     */
    @Override
    public void updateBarriersNumber(String player, int barriersNumber) {
    	this.view.updateBarriersNumber(player, barriersNumber);
    }
    
    /**
     * Draw power ups.
     *
     * @param powerUpsAsList the power ups as list
     */
    @Override
    public void drawPowerUps(List<PowerUp> powerUpsAsList) {
		for (PowerUp p : powerUpsAsList) {
			switch (p.getType()) {
			case PLUS_ONE_MOVE:
				System.out.println("Drawing powerUp Double Move in " + p.getCoordinate());
				ImageView doubleMoveIcon = new ImageView(new Image(this.getClass()
						.getResourceAsStream(ScenesItem.DOUBLEPUP.get())));
				this.view.drawPowerUp(this.gridMap.get(p.getCoordinate()), doubleMoveIcon);
				break;
			case PLUS_ONE_BARRIER:
				System.out.println("Drawing powerUp Plus One Barrier in " + p.getCoordinate());
				ImageView plusOneBarrierIcon = new ImageView(new Image(this.getClass()
						.getResourceAsStream(ScenesItem.BARRIERPUP.get())));
				this.view.drawPowerUp(this.gridMap.get(p.getCoordinate()), plusOneBarrierIcon);
				break;
			default:
				break;
			}
		}
    }
    
    /**
     * Delete power up.
     *
     * @param p the powerUp to delete
     */
    public void deletePowerUp(PowerUp p) {
		List<Node> toRemove = this.gridMap.get(p.getCoordinate()).getChildren().stream()
				.filter(e -> e.getClass().equals(ImageView.class))
				.collect(Collectors.toList()); 
		this.gridMap.get(p.getCoordinate()).getChildren().removeAll(toRemove);
    }
    
    
    /**
     * Calls the endRound method in view and also calls the controller method for changing round.
     *
     * @param winner the winner
     */
    public void endRound(String winner) {
    	this.view.endRound(winner);
    	this.controller.nextRound();
    }
    
    /**
     * Calls the endGame method in view.
     *
     * @param winner the winner
     */
    public void endGame(String winner) {
    	this.view.endGame(winner);
    }
    
    /**
     * Calls the saveGame method in controller.
     */
    @Override
    public void saveGame() {
    	this.controller.saveGame();
    }

	/**
	 * Draw text in the textAreas by calling the respective method in view. 
	 *
	 * @param textToDisplay the text to display
	 */
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
