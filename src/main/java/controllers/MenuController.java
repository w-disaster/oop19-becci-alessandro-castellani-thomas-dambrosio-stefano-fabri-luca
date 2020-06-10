package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jdk.jshell.spi.ExecutionControl.*;
import savings.SaveLeaderBoard;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class MenuController {
	
	private SceneChanger sceneChange;
	
	@FXML
    public void exitButtonPressHandler() {
        System.exit(0);
    }

	 @FXML
	 public void newGameButtonPressHandler(ActionEvent event) throws IOException {
		System.out.println("yy");
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change("layouts/main/scene.fxml", "Game");
	 }
	 
	 @FXML
	 public void loadGameButtonPressHandler() throws NotImplementedException {
		 throw new NotImplementedException("Not Implemented Yet");
	 }
	 
	 @FXML
	 public void leaderboardButtonPressHandler() throws IOException{
		 //ONLY FOR TESTING 
		 SaveLeaderBoard save = new SaveLeaderBoard();
		 save.updateLeaderBoard("gangshit");
	 }
	 
}
