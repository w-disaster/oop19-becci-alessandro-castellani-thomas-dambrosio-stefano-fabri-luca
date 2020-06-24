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

import java.util.Optional;


/**
 * The Controller related to the scene.fxml GUI.
 *
 * @author Stefano
 *
 */
public final class ViewController{
	
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
    
    private ViewLogic logic;
    
    private Circle bluePlayer;
	private Circle redPlayer;
	
	private String player1;
	private String player2;

	public ViewController() {
		this.logic = new ViewLogicImpl(this);
	}
	
	public void initialize() {
    	System.out.println("Initializing...");
    	
    	// Dialog setup
    	Dialog<Pair<String, String>> dialog = new Dialog<>();
    	dialog.setTitle("Quoridor2D");
    	dialog.setHeaderText("Choose your nicknames");
    	
    	// Dialog styling
    	DialogPane dialogPane = dialog.getDialogPane();
    	dialogPane.setStyle("-fx-background-color: #2B2D42");

    	// Set the icon 
    	((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add
    		(new Image(this.getClass().getResourceAsStream(ScenesItem.LOGO.get())));
    	
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
    	if (MenuController.gameStatus.equals(GameStatus.NORMAL)
    			|| MenuController.gameStatus.equals(GameStatus.POWERUP)) {
    		Optional<Pair<String, String>> result = dialog.showAndWait();
    		this.logic.setPlayer(result);
    	}

    	// Grid setup
    	int numCols = 9;
    	int numRows = 9;
    	
	    for (int i = 0 ; i < numCols ; i++) {
	        for (int j = 0; j < numRows; j++) {
	            this.addPane(new Coordinate(i,j));
	        }
	    }
	    
	    bluePlayer = new Circle();
	    redPlayer = new Circle();
	    bluePlayer.getStyleClass().add("BluePlayer");
	    redPlayer.getStyleClass().add("RedPlayer");
	    label1.getStyleClass().add("SelectedLabel");
	    label2.getStyleClass().add("Label");

	    this.logic.drawTextLogic("start");
	    
	    this.logic.startGame();
	    label1.setText(player1);
	    label2.setText(player2);
	}
    
    private void addPane(Coordinate position) {
        BorderPane pane = this.logic.addPaneLogic(position);
        pane.getStyleClass().add("GridBorderPane");
        this.grid.add(pane, position.getX(), position.getY());
    }
    
    public void setNicknames(String player1, String player2) {
    	this.player1 = player1;
    	this.player2 = player2;
    }
    
    public void setPlayerInPane(BorderPane p, String player) {
    	if (player.equals(player1)) {
    		p.setCenter(bluePlayer);
    	} else {
    		p.setCenter(redPlayer);
    	}
    	this.setCorrectPlayerSize();
    }
    
    private void setCorrectPlayerSize() {
    	Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			bluePlayer.setRadius((grid.getWidth())/28);
    			redPlayer.setRadius((grid.getWidth())/28);
    		}
    		
    	});
		
	}

	public void barrierPlacement(MouseEvent event) {
    	if (event.getSource().equals(player1vertical) || event.getSource().equals(player2vertical)) {
    		this.logic.setSelectedBarrier("vertical");
    	} else {
    		this.logic.setSelectedBarrier("horizontal");
    	}
    }
    
    public void updateBarriersNumber(String player, int barriersNumber) {
    	if (player.equals(player1)) {
    		this.barriersNumber1.setText(String.valueOf(barriersNumber));
    	} else {
    		this.barriersNumber2.setText(String.valueOf(barriersNumber));
    	}
    }
    
    public void drawPowerUp(BorderPane pane, ImageView powerUpIcon) {
    	Platform.runLater(new Runnable() {
    		
    		@Override
    		public void run() {
    			powerUpIcon.setFitHeight(grid.getWidth()/12);
    			powerUpIcon.setFitWidth(grid.getHeight()/12);
    		}
    		
    	});
		pane.setCenter(powerUpIcon);
    }
    
    public void changeSelectedLabel(String player) { 	
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
    
    public void drawText(String text) {
    	this.textArea1.setText(text);
    	this.textArea2.setText(text);
    }
    
    public void endRound(String winner) {
       	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the round!");
    	alert.getDialogPane().setStyle("-fx-background-color: #2B2D42");

    	((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
    		(new Image(this.getClass().getResourceAsStream(ScenesItem.LOGO.get())));
    	
    	alert.showAndWait();
    	

    }
    
    public void endGame(String winner) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the game!\nDo you want to return to the main menu?");
    	
    	alert.getDialogPane().setStyle("-fx-background-color: #2B2D42");

    	((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
    	(new Image(this.getClass().getResourceAsStream(ScenesItem.LOGO.get())));

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
			this.returnToMainMenu();
    	} else {
    	    System.exit(0);
    	}
    }
    
    @FXML
    public void goFullscreen() {
    	Stage s = ((Stage) this.rootPane.getScene().getWindow());
    	if (s.isFullScreen()) {
    		s.setFullScreen(false);
    		this.fullscreenMenuItem.setText("Go Fullscreen");	
    	} else {
    		s.setFullScreen(true);
    		this.fullscreenMenuItem.setText("Go back");	
    	}
    	this.setCorrectPlayerSize();
    	this.logic.setCorrectBarrierSize();
    }
    
    @FXML
    public void saveAndGoToLeaderboard() {
    	this.saveGame();
    	this.showLeaderboard();
    }
    
    @FXML
    public void showLeaderboard() {
    	SceneChanger sceneChange = new SceneChangerImpl();
    	sceneChange.change(ScenesItem.LEADERBOARD.get(), ScenesItem.LEADERBOARDTITLE.get());
    }
    
    @FXML
    public void showTutorials() {
    	this.logic.drawTextLogic("start");
    }
    
    @FXML
    public void saveGame() {
    	System.out.println("Saving game...");
    	this.logic.saveGame();
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
