package controllers;

import application.Main;
import controller.StandardGameControllerImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.stage.Stage;
import model.roundenvironment.coordinate.Coordinate;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    
	Circle redPlayer;
	Circle bluePlayer;
	
	StandardGameControllerImpl controller;
	
	String player1;
	String player2;
	
	Map<Coordinate, Pane> gridMap;
	int turn = 0;

	public void initialize() {
    	System.out.println("Initializing...");
    	    	
    	this.controller = new StandardGameControllerImpl(this);
    	controller.newStandardGame(player1, player2);

    	int numCols = 9;
    	int numRows = 9;
    	
    	gridMap = new HashMap<Coordinate, Pane>();
    	
    	redPlayer = new Circle(25);
    	redPlayer.getStyleClass().add("RedPlayer");
    	bluePlayer = new Circle(25);
    	bluePlayer.getStyleClass().add("BluePlayer");
    	label1.getStyleClass().add("SelectedLabel");
    	label2.getStyleClass().add("Label");
  

	    for (int i = 0 ; i < numCols ; i++) {
	        for (int j = 0; j < numRows; j++) {
	            addPane(i, j);
	            System.out.println("pane added" + i + j);
	        }
	    }
	}
    
    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new StackPane();
        //pane.setPrefSize(50, 50);
        Coordinate position = new Coordinate(colIndex, rowIndex);
        pane.setOnMouseClicked(e -> {
            System.out.printf("Mouse clicked cell " + position.toString() + "\n");
            controller.movePlayer(position);
        });
        pane.getStyleClass().add("GridBorderPane");
        grid.add(pane, position.getX(), position.getY());
        gridMap.put(position, pane);
        if (colIndex == 4 && rowIndex == 8) {
        	System.out.print(pane.getWidth());
        	pane.getChildren().add(redPlayer);
        	StackPane.setAlignment(redPlayer, Pos.CENTER);
        }
        if (colIndex == 4 && rowIndex == 0) {
        	System.out.print(pane.getWidth());
        	pane.getChildren().add(bluePlayer);
        	StackPane.setAlignment(bluePlayer, Pos.CENTER);
        }
    }
    
    public void move(Coordinate position) {
    	if(turn%2 == 0) {
    		gridMap.get(position).getChildren().add(bluePlayer);
    		StackPane.setAlignment(bluePlayer, Pos.CENTER);
    		label1.getStyleClass().clear();
    		label1.getStyleClass().add("SelectedLabel");   		
    		label2.getStyleClass().clear();
    		label2.getStyleClass().add("Label");

    	} else {
    		gridMap.get(position).getChildren().add(redPlayer);
    		StackPane.setAlignment(redPlayer, Pos.CENTER);
    		label2.getStyleClass().clear();
    		label2.getStyleClass().add("SelectedLabel");
    		label1.getStyleClass().clear();
    		label1.getStyleClass().add("Label");
    	}
    	turn++;
    }

    
    /**
     * A method that handles the return to the main menu.
     */ 
    @FXML
    public void returnToMainMenu(ActionEvent event) throws IOException{
    	SceneChanger sceneChange = new SceneChangerImpl(event);
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


