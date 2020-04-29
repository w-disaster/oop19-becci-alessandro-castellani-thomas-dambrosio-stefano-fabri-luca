package controllers;

import com.google.common.collect.ImmutableList;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * The Controller related to the main.fxml GUI.
 *
 */
public final class UIController {
	@FXML 
	private MenuItem RetToMainMenu;
    
    @FXML
    private ImageView redPlayer;
    
    @FXML
    public void handleDragDetection(MouseEvent event) {
    	Dragboard db = redPlayer.startDragAndDrop(TransferMode.MOVE);

        // Put a string on a dragboard as an identifier
        ClipboardContent content = new ClipboardContent();
        content.putString(redPlayer.getId());
        db.setContent(content);

        //Consume the event
        event.consume();
    }

    /**
     * A method that handles the return to the main menu.
     */ 
    @FXML
    public void ReturnToMainMenu(ActionEvent event) throws IOException{

    	SceneChanger sceneChange = new SceneChangerImpl(event);
    	sceneChange.change("layouts/menu/MainMenu.fxml", "Game");
		 

    }
}
