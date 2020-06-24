package view.game;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.roundenvironment.coordinate.Coordinate;
import view.menu.MenuController;
import view.menu.MenuController.GameStatus;
import view.scenechanger.SceneChanger;
import view.scenechanger.SceneChangerImpl;
import view.scenechanger.ScenesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Controller related to the scene.fxml GUI.
 *
 * @author Stefano D'Ambrosio
 *
 */
public class ViewController{
	
    @FXML private BorderPane rootPane;
    @FXML private GridPane grid;
    
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private TextArea textArea1;
    @FXML private TextArea textArea2;
    
    @FXML private Rectangle player1vertical;
    @FXML private Rectangle player1horizontal;
    @FXML private Rectangle player2vertical;
    @FXML private Rectangle player2horizontal;
    
    @FXML private Label barriersNumber1;
    @FXML private Label barriersNumber2;
    
    @FXML private MenuItem fullscreenMenuItem;
    
    private final ViewLogic logic;
    
    private Circle bluePlayer;
	private Circle redPlayer;
	
	private String player1;
	private String player2;
	
	private final List<Rectangle> verticalBarrierList;
	private final List<Rectangle> horizontalBarrierList;
	private final List<ImageView> powerUpList;

	public ViewController() {
		this.logic = new ViewLogicImpl(this);
		this.verticalBarrierList = new ArrayList<>();
		this.horizontalBarrierList= new ArrayList<>();
		this.powerUpList= new ArrayList<>();
	}
	
	public void initialize() {
    	System.out.println("Initializing...");
    	
    	// Dialog setup
    	final Dialog<Pair<String, String>> dialog = new Dialog<>();
    	dialog.setTitle("Quoridor2D");
    	dialog.setHeaderText("Choose your nicknames");
    	
    	// Dialog styling
    	final DialogPane dialogPane = dialog.getDialogPane();
    	dialogPane.setStyle("-fx-background-color: #2B2D42");

    	// Set the icon 
    	((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add
    		(new Image(this.getClass().getResourceAsStream(ScenesItem.LOGO.get())));
    	
    	// Layout
    	final ButtonType startButton = new ButtonType("Start", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);
    	
    	final GridPane dialogGrid = new GridPane();
    	dialogGrid.setHgap(10);
    	dialogGrid.setVgap(10);
    	dialogGrid.setPadding(new Insets(20, 150, 10, 10));

    	final TextField player1name = new TextField();
    	player1name.setPromptText("Player 1");
    	final TextField player2name = new TextField();
    	player2name.setPromptText("Player 2");
    	
    	final Label p1nickname = new Label("Nickname for player 1:");
    	p1nickname.setTextFill(Color.WHITE);
    	final Label p2nickname = new Label("Nickname for player 2:");
    	p2nickname.setTextFill(Color.WHITE);
    	
    	dialogGrid.add(p1nickname, 0, 0);
    	dialogGrid.add(player1name, 1, 0);
    	dialogGrid.add(p2nickname, 0, 1);
    	dialogGrid.add(player2name, 1, 1);

    	dialog.getDialogPane().setContent(dialogGrid);

    	// Takes the nicknames from the textFields
    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == startButton) {
    	    	return new Pair<String, String>(player1name.getText(),player2name.getText());
    	    }
    	    System.exit(0);
    	    return null;
    	});
    	
    	// Check if user clicked 'load game'
    	if (MenuController.gameStatus.equals(GameStatus.NORMAL)
    			|| MenuController.gameStatus.equals(GameStatus.POWERUP)) {
    		Optional<Pair<String, String>> result = dialog.showAndWait();
    		this.logic.setPlayer(result);
    	}

    	// Grid setup
    	final int numCols = 9;
    	final int numRows = 9;
    	
	    for (int i = 0 ; i < numCols ; i++) {
	        for (int j = 0; j < numRows; j++) {
	            this.addPane(new Coordinate(i,j));
	        }
	    }
	    
	    // View styling
	    bluePlayer = new Circle();
	    redPlayer = new Circle();
	    bluePlayer.getStyleClass().add("BluePlayer");
	    redPlayer.getStyleClass().add("RedPlayer");
	    label1.getStyleClass().add("SelectedLabel");
	    label2.getStyleClass().add("Label");

	    // Prints the tutorial
	    this.logic.drawTextLogic("start");
	    
	    // Starts the game
	    this.logic.startGame();
	    
	    //Sets the players name in labels
	    label1.setText(player1);
	    label2.setText(player2);
	}
    
    /**
     * Adds a BorderPane to the grid.
     *
     * @param position the position
     */
    private void addPane(Coordinate position) {
    	final BorderPane pane = this.logic.addPaneLogic(position);
        pane.getStyleClass().add("GridBorderPane");
        this.grid.add(pane, position.getX(), position.getY());
    }
    
    /**
     * Sets the nicknames.
     *
     * @param player1 the player 1 nickname
     * @param player2 the player 2 nickname
     */
    public void setNicknames(String player1, String player2) {
    	this.player1 = player1;
    	this.player2 = player2;
    }
    
    /**
     * Sets the player in center of the BorderPane.
     *
     * @param p the pane
     * @param player the player
     */
    public void setPlayerInPane(BorderPane p, String player) {
    	if (player.equals(player1)) {
    		p.setCenter(bluePlayer);
    	} else {
    		p.setCenter(redPlayer);
    	}
    	this.setCorrectSize();
    }
    
    /**
     * Sets the correct size of all nodes.
     */
    public void setCorrectSize() {
    	
    	// Runs after the initialize
    	Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			final Double baseSize = grid.getWidth();
    					
    			// Players size
    			bluePlayer.setRadius(baseSize/28);
    			redPlayer.setRadius(baseSize/28);

    			// Barrier size
				for (final Rectangle b : verticalBarrierList) {
					b.setHeight(baseSize/11);				
					b.setWidth(baseSize/70);					
				}
				for (final Rectangle b : horizontalBarrierList) {
					b.setHeight(baseSize/70);				
					b.setWidth(baseSize/11);							
				}
				
				// PowerUp size
				for (final ImageView powerUpIcon : powerUpList) {
					powerUpIcon.setFitHeight(baseSize/12);
					powerUpIcon.setFitWidth(baseSize/12);						
				}
			}
    	});
		
	}
    
	/**
	 * Sets the selected barrier in the logic
	 *
	 * @param event the event that handles the click on the barrier
	 */
	public void barrierPlacement(final MouseEvent event) {
    	if (event.getSource().equals(player1vertical) || event.getSource().equals(player2vertical)) {
    		this.logic.setSelectedBarrier("vertical");
    	} else {
    		this.logic.setSelectedBarrier("horizontal");
    	}
    }
    
    /**
     * Update barriers number in the labels.
     *
     * @param player the player
     * @param barriersNumber the barriers number
     */
    public void updateBarriersNumber(final String player, final int barriersNumber) {
    	if (player.equals(player1)) {
    		this.barriersNumber1.setText(String.valueOf(barriersNumber));
    	} else {
    		this.barriersNumber2.setText(String.valueOf(barriersNumber));
    	}
    }
    
    /**
     * Draw power up.
     *
     * @param pane the pane
     * @param powerUpIcon the power up icon
     */
    public void drawPowerUp(final BorderPane pane, final ImageView powerUpIcon) {
    	this.powerUpList.add(powerUpIcon);
    	this.setCorrectSize();
		pane.setCenter(powerUpIcon);
    }
    
    /**
     * Change selected label.
     *
     * @param player the player
     */
    public void changeSelectedLabel(final String player) { 	
    	if (player.equals(player1)) {
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
    
    /**
     * Draw text on the textAreas.
     *
     * @param text the text
     */
    public void drawText(final String text) {
    	this.textArea1.setText(text);
    	this.textArea2.setText(text);
    }
    
    /**
     * Append text on the textAreas.
     *
     * @param text the text
     */
    public void appendText(final String text) {
    	this.textArea1.appendText(text);
    	this.textArea2.appendText(text);    	
    }
    
    /**
     * Shows an alert when changing round
     *
     * @param winner the winner
     */
    public void endRound(final String winner) {
    	final Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the round!");
    	alert.getDialogPane().setStyle("-fx-background-color: #2B2D42");

    	((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
    		(new Image(this.getClass().getResourceAsStream(ScenesItem.LOGO.get())));
    	
    	alert.showAndWait();
    	

    }
    
    /**
     * Shows an alert when a player wins.
     *
     * @param winner the winner
     */
    public void endGame(final String winner) {
    	final Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the game!\nDo you want to return to the main menu?");
    	
    	alert.getDialogPane().setStyle("-fx-background-color: #2B2D42");

    	((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
    	(new Image(this.getClass().getResourceAsStream(ScenesItem.LOGO.get())));

    	final Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
			this.returnToMainMenu();
    	} else {
    	    System.exit(0);
    	}
    }
    
    /**
     * Gets the horizontal barrier list.
     *
     * @return the horizontal barrier list
     */
    public final List<Rectangle> getHorizontalBarrierList() {
    	return this.horizontalBarrierList;
    }
    
    /**
     * Gets the vertical barrier list.
     *
     * @return the vertical barrier list
     */
    public final List<Rectangle> getVerticalBarrierList() {
    	return this.verticalBarrierList;
    }
    
    /**
     * Goes fullscreen or back depending by the stage
     */
    @FXML
    public void goFullscreen() {
    	final Stage s = ((Stage) this.rootPane.getScene().getWindow());
    	if (s.isFullScreen()) {
    		s.setFullScreen(false);
    		this.fullscreenMenuItem.setText("Go Fullscreen");	
    	} else {
    		s.setFullScreen(true);
    		this.fullscreenMenuItem.setText("Go back");	
    	}
    	this.setCorrectSize();
    }
    
    /**
     * Save and go to leaderboard.
     */
    @FXML
    public void saveAndGoToLeaderboard() {
    	this.saveGame();
    	this.showLeaderboard();
    }
    
    /**
     * Show leaderboard.
     */
    @FXML
    public void showLeaderboard() {
    	final SceneChanger sceneChange = new SceneChangerImpl();
    	sceneChange.change(ScenesItem.LEADERBOARD.get(), ScenesItem.LEADERBOARDTITLE.get());
    }
    
    /**
     * Show tutorials.
     */
    @FXML
    public void showTutorials() {
    	this.logic.drawTextLogic("start");
    }
    
    /**
     * Save game.
     */
    @FXML
    public void saveGame() {
    	System.out.println("Saving game...");
    	this.logic.saveGame();
    }
    
    /**
     * Save and return.
     */
    @FXML
    public void saveAndReturn() {
    	this.saveGame();
    	this.returnToMainMenu();
    }

    /**
     * Save and exit.
     */
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
    	final SceneChanger sceneChange = new SceneChangerImpl();
    	sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
    }
    
    /**
     * A method that handles the exit of the application.
     */
     @FXML
     public void exitToDesktop() {
    	 System.exit(0);
     }
}
