package guicontrollers;

import controller.GameController;
import controller.PowerUpGameController;
import controller.PowerUpGameControllerImpl;
import controller.StandardGameController;
import controller.StandardGameControllerImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;
import savings.LoadGameImpl;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;


/**
 * The Controller related to the scene.fxml GUI.
 *
 */
public final class UIController{
	
    @FXML private BorderPane rootPane;
    @FXML private GridPane grid;
    
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private TextArea textArea1;
    @FXML private TextArea textArea2;
    
    @FXML private MenuItem saveGame;
    @FXML private MenuItem retToMainMenu;
    @FXML private MenuItem exit;
    
    @FXML private Rectangle player1vertical;
    @FXML private Rectangle player1horizontal;
    @FXML private Rectangle player2vertical;
    @FXML private Rectangle player2horizontal;
    
    @FXML private Label barriersNumber1;
    @FXML private Label barriersNumber2;
    
    private Logic logic;
    
    private Circle bluePlayer;
	private Circle redPlayer;
	
	private GameController controller;
	
	private Optional<String> player1;
	private Optional<String> player2;

	public UIController() {
		this.logic = new LogicImpl(this);
		if (MenuController.powerup_game) {
			this.controller = new PowerUpGameControllerImpl(this);
		} else {
			this.controller = new StandardGameControllerImpl(this);						
		}
	}
	
	public void initialize() {
    	System.out.println("Initializing...");
    	
    	// Dialog setup
    	Dialog<Pair<String, String>> dialog = new Dialog<>();
    	dialog.setTitle("Quoridor2D");
    	dialog.setHeaderText("Choose your nicknames");
    	
    	// Dialog styling
    	DialogPane dialogPane = dialog.getDialogPane();
    	dialogPane.setStyle("-fx-background-color: #2B2D42; -fx-fill: #FFFFFF;");

    	// Set the icon 
    	((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add
    		(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));
    	
    	// Layout
    	ButtonType startButton = new ButtonType("Start", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);
    	
    	GridPane dialogGrid = new GridPane();
    	dialogGrid.setHgap(10);
    	dialogGrid.setVgap(10);
    	dialogGrid.setPadding(new Insets(20, 150, 10, 10));

    	TextField player1name = new TextField();
    	player1name.setPromptText("Player 1");
    	TextField player2name = new TextField();
    	player2name.setPromptText("Player 2");
    	
    	Label p1nickname = new Label("Nickname for player 1:");
    	p1nickname.setTextFill(Color.WHITE);
    	Label p2nickname = new Label("Nickname for player 2:");
    	p2nickname.setTextFill(Color.WHITE);
    	
    	dialogGrid.add(p1nickname, 0, 0);
    	dialogGrid.add(player1name, 1, 0);
    	dialogGrid.add(p2nickname, 0, 1);
    	dialogGrid.add(player2name, 1, 1);

    	dialog.getDialogPane().setContent(dialogGrid);

    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == startButton) {
    	    	return new Pair<String, String>(player1name.getText(),player2name.getText());
    	    }
    	    System.exit(0);
    	    return null;
    	});
    	
    	// Check if user clicked 'load game'
    	if (MenuController.to_load == false) {
    		Optional<Pair<String, String>> result = dialog.showAndWait();
    		
    		// If you leave it empty it automatically set default nicknames
	    	if (result.get().getKey().equals("")) {
	    		this.player1 = Optional.of("Player 1");
	    	} else {
	    		this.player1 = Optional.of(result.get().getKey());    		
	    	}
	    	
	    	if (result.get().getValue().equals("")) {
	    		this.player2 = Optional.of("Player 2");
	    	} else {
	    		this.player2 = Optional.of(result.get().getValue());    		
	    	}
	    	
	    	// Nicknames can't be the same
	    	if (player1.get().equals(player2.get())) {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("ERROR");
	    		alert.setContentText("You can't use the same name!");
	    		alert.showAndWait();	
	    	}
    	}
    	
    	// Grid setup
    	int numCols = 9;
    	int numRows = 9;
    	
	    for (int i = 0 ; i < numCols ; i++) {
	        for (int j = 0; j < numRows; j++) {
	            this.addPane(i, j);
	        }
	    }
	    
	    bluePlayer = new Circle(25, Color.BLUE);
	    bluePlayer.getStyleClass().add("BluePlayer");
	    redPlayer = new Circle(25, Color.RED);
	    redPlayer.getStyleClass().add("RedPlayer");
	    label1.getStyleClass().add("SelectedLabel");
	    label2.getStyleClass().add("Label");

	    this.logic.drawTextLogic("start");
	    
	    this.logic.setPlayers(player1, player2);
	    
	    
	    //Starts the game
	    if (MenuController.to_load) {
	    	this.controller.loadGame();
	    } else if(MenuController.powerup_game){
	    	//newPowerUpGame
	    	((PowerUpGameController) this.controller).newPowerUpGame(player1.get(), player2.get());	    	
	    }  else {
	    	((StandardGameController) this.controller).newStandardGame(player1.get(), player2.get());	    
	    }
	    label1.setText(player1.get());
	    label2.setText(player2.get());
	}
    
    private void addPane(int colIndex, int rowIndex) {
        Coordinate position = new Coordinate(colIndex, rowIndex);
        BorderPane pane = this.logic.addPaneLogic(position, this.controller);
        pane.getStyleClass().add("GridBorderPane");
        System.out.println(this.grid.getWidth());
        this.grid.add(pane, position.getX(), position.getY());
    }
    
    public void setNicknames(String player1, String player2) {
    	this.player1 = Optional.of(player1);
    	this.player2 = Optional.of(player2);
    }
    
    public void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2) {
    	this.logic.clearGrid();
    	this.logic.getPaneByPosition(player1pos).setCenter(bluePlayer);
    	this.logic.getPaneByPosition(player2pos).setCenter(redPlayer);
    	this.updateBarriersNumber(player1.get(), barriersP1);
    	this.updateBarriersNumber(player2.get(), barriersP2);
    	Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			bluePlayer.setRadius((grid.getWidth())/25);
    			redPlayer.setRadius((grid.getWidth())/25);
    		}
    		
    	});
    }
    
    public void setupGrid(Coordinate player1pos, Coordinate player2pos, int barriersP1, int barriersP2, List<Barrier> barrierList) {
    	this.setupGrid(player1pos, player2pos, barriersP1, barriersP2);
    	this.logic.drawBarriersOnLoad(barrierList);
    }
    
    public void drawPowerUps(List<PowerUp> powerUpsAsList) {
    	this.logic.drawPowerUps(powerUpsAsList);
    }

    public void move(Coordinate position, String player) {
    	System.out.println(this.grid.getWidth());
    	if(player.equals(this.player1.get())) {
    		this.logic.getPaneByPosition(position).getChildren().add(bluePlayer);
    		BorderPane.setAlignment(redPlayer, Pos.CENTER);
    	} else {
    		this.logic.getPaneByPosition(position).getChildren().add(redPlayer);
    		BorderPane.setAlignment(redPlayer, Pos.CENTER);
    	}
    }
    
    public void barrierPlacement(MouseEvent event) {
    	if (event.getSource().equals(player1vertical) || event.getSource().equals(player2vertical)) {
    		this.logic.setSelectedBarrier("vertical");
    	} else {
    		this.logic.setSelectedBarrier("horizontal");
    	}
    }
    
    public void drawBarrier(Barrier barrier) {
    	this.logic.drawBarrierLogic(barrier);
    }
    
    public void updateBarriersNumber(String player, int barriersNumber) {
    	if (player.equals(player1.get())) {
    		this.barriersNumber1.setText(String.valueOf(barriersNumber));
    	} else {
    		this.barriersNumber2.setText(String.valueOf(barriersNumber));
    	}
    }
    
    public void changeSelectedLabel(String player) { 	
    	if (player.equals(player1.get())) {
    		label1.getStyleClass().clear();
    		label1.getStyleClass().add("SelectedLabel");   		
    		label2.getStyleClass().clear();
    		label2.getStyleClass().add("Label");
    	} else {
    		label2.getStyleClass().clear();
    		label2.getStyleClass().add("SelectedLabel");
    		label1.getStyleClass().clear();
    		label1.getStyleClass().add("Label");
    	}
    }
    
    public void drawText(String text) {
    	this.textArea1.setText(text);
    	this.textArea2.setText(text);
    }
    
    public void endRound(String winner) {
       	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the round!");
    	alert.setContentText("");

    	((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
    		(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));
    	
    	alert.showAndWait();
    	
    	this.controller.nextRound();
    }
    
    public void endGame(String winner) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the game!");
    	alert.setContentText("Do you want to return to the main menu?");

    	((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
    	(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
			this.returnToMainMenu();
    	} else {
    	    System.exit(0);
    	}
    }
    
    @FXML
    public void saveGame() {
    	System.out.println("Saving game...");
    	this.controller.saveGame();
    }
    
    @FXML
    public void saveAndReturn() {
    	this.saveGame();
    	this.returnToMainMenu();
    }

    @FXML
    public void saveAndExit() {
    	this.saveGame();
    	this.exitToDesktop();
    }
    
    /**
     * A method that handles the return to the main menu.
     */ 
    @FXML
    public void returnToMainMenu(){
    	SceneChanger sceneChange = new SceneChangerImpl();
    	sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
    }
    
    /**
     * A method that handles the exit of the application.
     */
     @FXML
     public void exitToDesktop() {
    	 System.exit(0);
     }

}
