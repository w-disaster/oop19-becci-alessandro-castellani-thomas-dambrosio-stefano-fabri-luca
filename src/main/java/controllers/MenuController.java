package controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl.*;
import savings.SaveLeaderBoard;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class MenuController {
	
	@FXML private BorderPane rootPane;
	@FXML private Button newGameButton;
	@FXML private Button loadGameButton;
	@FXML private Button leaderBoardButton;
	@FXML private Button exitGameButton;
	@FXML private Label title;
	
	private SceneChanger sceneChange;
	private Stage stage;
	
	public void initialize() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage = (Stage) rootPane.getScene().getWindow();
				stage.widthProperty().addListener(new ChangeListener<>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						String styleButtons = "-fx-font-size:" + newValue.doubleValue()/40 + ";"; 
						String styleLabel = "-fx-font-size:" + newValue.doubleValue()/11 + ";";
						newGameButton.setStyle(styleButtons);
						loadGameButton.setStyle(styleButtons);
						leaderBoardButton.setStyle(styleButtons);
						exitGameButton.setStyle(styleButtons);
						title.setStyle(styleLabel);
					}
				});
			}
		});
	}
	
	@FXML
    public void exitButtonPressHandler() {
        System.exit(0);
    }

	 @FXML
	 public void newGameButtonPressHandler(ActionEvent event) throws IOException {
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change("layouts/main/scene.fxml", "Game");
	 }
	 
	 @FXML
	 public void loadGameButtonPressHandler() throws NotImplementedException {
		 throw new NotImplementedException("Not Implemented Yet");
	 }
	 
	 @FXML
	 public void leaderboardButtonPressHandler() {
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change("layouts/leaderboard/LeaderBoard.fxml", "Leaderboard");
	 }
	 
}
