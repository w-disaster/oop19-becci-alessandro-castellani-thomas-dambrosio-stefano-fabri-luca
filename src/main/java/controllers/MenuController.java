package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl.*;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;

public class MenuController {

	 private static final double SCALING_RATE = 1.5;

	@FXML
    public void exitButtonPressHandler() {
        System.exit(0);
    }

	 @FXML
	 public void newGameButtonPressHandler(ActionEvent event) throws IOException {
		 SceneBuilder sceneBuild = new SceneBuilderImpl(SCALING_RATE, "layouts/main/scene.fxml");
		 
		 Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 stage.setTitle("Quoridor2D - Game");
	     stage.setScene(sceneBuild.getScene());
	     stage.sizeToScene();
	     stage.show();
	     stage.setMinWidth(stage.getWidth());
	     stage.setMinHeight(stage.getHeight());
	 }
	 
	 @FXML
	 public void loadGameButtonPressHandler() throws NotImplementedException {
		 throw new NotImplementedException("Not Implemented Yet");
	 }
	 
	 @FXML
	 public void leaderboardButtonPressHandler() throws NotImplementedException {
		 throw new NotImplementedException("Not Implemented Yet");
	 }
	 
}
