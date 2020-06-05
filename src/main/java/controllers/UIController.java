package controllers;

import application.Main;

import controller.StandardGameControllerImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.roundenvironment.coordinate.Coordinate;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * The Controller related to the scene.fxml GUI.
 *
 */
public final class UIController{
    
    @FXML private GridPane grid;
    
    @FXML private Label label1;
    @FXML private Label label2;
    
    @FXML private MenuItem retToMainMenu;
    
    @FXML private MenuItem exit;
    
    @FXML private Rectangle player1vertical;
    @FXML private Rectangle player1horizontal;
    @FXML private Rectangle player2vertical;
    @FXML private Rectangle player2horizontal;
    
    private Circle bluePlayer;
	private Circle redPlayer;
	
	private StandardGameControllerImpl controller;
	
	private Optional<String> player1;
	private Optional<String> player2;
	
	private Map<Coordinate, Pane> gridMap;
	
	public UIController() {
		this.controller = new StandardGameControllerImpl(this);
	}
	
	public void initialize() {
    	System.out.println("Initializing...");
    	
    	// Dialog setup
    	Dialog<Pair<String, String>> dialog = new Dialog<>();
    	dialog.setHeaderText("Choose your nicknames");

    	// Set the icon (must be included in the project).
    	//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

    	ButtonType startButton = new ButtonType("Start", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(startButton, ButtonType.CANCEL);
    	
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField player1name = new TextField();
    	player1name.setPromptText("Player 1");
    	TextField player2name = new TextField();
    	player2name.setPromptText("Player 2");
    	
    	grid.add(new Label("Nickname for player 1:"), 0, 0);
    	grid.add(player1name, 1, 0);
    	grid.add(new Label("Nickname for player 2:"), 0, 1);
    	grid.add(player2name, 1, 1);

    	dialog.getDialogPane().setContent(grid);

    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == startButton) {
    	    	return new Pair<String, String>(player1name.getText(),player2name.getText());
    	    }
    	    return null;
    	});

    	Optional<Pair<String, String>> result = dialog.showAndWait();
    	
    	this.player1 = Optional.of(result.get().getKey());
    	this.player2 = Optional.of(result.get().getValue());
	
    	
    	// Grid setup
    	int numCols = 9;
    	int numRows = 9;
    	
    	gridMap = new HashMap<Coordinate, Pane>();
    	
    	bluePlayer = new Circle(25);
    	bluePlayer.getStyleClass().add("BluePlayer");
    	redPlayer = new Circle(25);
    	redPlayer.getStyleClass().add("RedPlayer");
    	label1.setText(player1.get());
    	label2.setText(player2.get());
    	label1.getStyleClass().add("SelectedLabel");
    	label2.getStyleClass().add("Label");
  
	    for (int i = 0 ; i < numCols ; i++) {
	        for (int j = 0; j < numRows; j++) {
	            addPane(i, j);
	            System.out.println("pane added" + i + j);
	        }
	    }
	    
	    //Starts the game
	    this.controller.newStandardGame(this.player1.get(), this.player2.get());
	}
    
    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new StackPane();
        Coordinate position = new Coordinate(colIndex, rowIndex);
        pane.setOnMouseClicked(e -> {
            System.out.printf("Mouse clicked cell " + position.toString() + "\n");
            controller.movePlayer(position);
        });
        pane.getStyleClass().add("GridBorderPane");
        grid.add(pane, position.getX(), position.getY());
        gridMap.put(position, pane);     
    }
    
    public void setupGrid(Coordinate player1pos, Coordinate player2pos) {
    	System.out.println(player1pos);
    	System.out.println(player2pos);
    	gridMap.get(player1pos).getChildren().add(bluePlayer);
    	StackPane.setAlignment(bluePlayer, Pos.CENTER);
    	gridMap.get(player2pos).getChildren().add(redPlayer);
    	StackPane.setAlignment(redPlayer, Pos.CENTER);
    }
    
    public void startGame(String player1, String player2) {
    	System.out.println("Game started");
    	this.player1 = Optional.of(player1);
    	this.player2 = Optional.of(player2);
    }
    
    public void move(Coordinate position, String player) {
    	if(player.equals(this.player1.get())) {
    		gridMap.get(position).getChildren().add(bluePlayer);
    		StackPane.setAlignment(bluePlayer, Pos.CENTER);
    		label2.getStyleClass().clear();
    		label2.getStyleClass().add("SelectedLabel");
    		label1.getStyleClass().clear();
    		label1.getStyleClass().add("Label");
    	} else {
    		gridMap.get(position).getChildren().add(redPlayer);
    		StackPane.setAlignment(redPlayer, Pos.CENTER);
    		label1.getStyleClass().clear();
    		label1.getStyleClass().add("SelectedLabel");   		
    		label2.getStyleClass().clear();
    		label2.getStyleClass().add("Label");
    	}
    }
    
    public void startDraggingBarrier() {
    	System.out.println("dragging");
    }
    
    public void stopDraggingBarrier(ActionEvent event) {
    	System.out.println("stopped");
    }
    
    public void endRound(String winner) {
    	for(Entry<Coordinate, Pane> p : this.gridMap.entrySet()) {
    		if (p.getValue().getChildren().contains(bluePlayer)) {
    			p.getValue().getChildren().remove(bluePlayer);
    		}
    		if (p.getValue().getChildren().contains(redPlayer)) {
    			p.getValue().getChildren().remove(redPlayer);
    		}
    	}
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the round!");
    	alert.setContentText("");
    	
    	
    	
    	Optional<ButtonType> result = alert.showAndWait();

    }
    
    public void endGame(String winner) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("We have a winner!");
    	alert.setHeaderText(winner + " won the game!");
    	alert.setContentText("Do you want to return to the main menu?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    try {
				this.returnToMainMenu();
			} catch (IOException e) {
				System.exit(0);
			}
    	} else {
    	    System.exit(0);
    	}
    }

    /**
     * A method that handles the return to the main menu.
     */ 
    @FXML
    public void returnToMainMenu() throws IOException{
    	SceneChanger sceneChange = new SceneChangerImpl();
    	sceneChange.change("layouts/menu/MainMenu.fxml", "Game");
    }
    
    /**
     * A method that handles the exit of the application.
     */
     @FXML
     public void exitToDesktop(ActionEvent event) {
    	 System.exit(0);
     }
}


