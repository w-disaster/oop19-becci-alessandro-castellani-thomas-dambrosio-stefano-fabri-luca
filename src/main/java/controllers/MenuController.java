package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jdk.jshell.spi.ExecutionControl.*;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class MenuController {
	
	
	@FXML
    public void exitButtonPressHandler() {
        System.exit(0);
    }

	 @FXML
	 public void newGameButtonPressHandler(ActionEvent event) throws IOException {
		 SceneChanger sceneChange = new SceneChangerImpl(event);
		 sceneChange.change("layouts/main/scene.fxml", "Quoridor2D - Game");
		 UIController controller = new UIController();
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
