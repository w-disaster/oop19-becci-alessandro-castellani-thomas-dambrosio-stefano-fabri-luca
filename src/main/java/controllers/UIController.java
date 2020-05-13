package controllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * The Controller related to the scene.fxml GUI.
 *
 */
public final class UIController{

    @FXML private Circle redPlayer;
    
    @FXML private Circle bluePlayer;
    
    @FXML private GridPane grid;
    
    @FXML private MenuItem retToMainMenu;
    
    @FXML private MenuItem exit;
    
    public void initialize() {
    	System.out.println("Initializing...");
    	int numCols = 9;
    	int numRows = 9;

	    for (int i = 0 ; i < numCols ; i++) {
	        for (int j = 0; j < numRows; j++) {
	            addPane(i, j);
	            System.out.println("pane added" + i + j);
	        }}
	    }
    

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new BorderPane();
        pane.setOnMouseClicked(e -> {
            System.out.printf("Mouse clicked cell [%d, %d]%n", colIndex, rowIndex);
        });
        pane.getStylesheets().add("layouts/main/applicatoin.css");
        pane.getStyleClass().add("GridBorderPane");
        grid.add(pane, colIndex, rowIndex);
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


