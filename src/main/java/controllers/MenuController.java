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
import savings.SaveGame;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class MenuController {
	
	@FXML private BorderPane rootPane;
	@FXML private Button newGameButton;
	@FXML private Button loadGameButton;
	@FXML private Button leaderBoardButton;
	@FXML private Button exitGameButton;
	@FXML private Label title;
	
	public static boolean to_load;
	private SceneChanger sceneChange;
	private Stage stage;
	
	private ChangeListener<Number> changeListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			String styleButtons = "-fx-font-size:" + newValue.doubleValue()/40 + ";"; 
			String styleLabel = "-fx-font-size:" + newValue.doubleValue()/11 + ";";
			newGameButton.setStyle(styleButtons);
			loadGameButton.setStyle(styleButtons);
			leaderBoardButton.setStyle(styleButtons);
			exitGameButton.setStyle(styleButtons);
			title.setStyle(styleLabel);
		}
		
	};
	
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				stage = (Stage) rootPane.getScene().getWindow();
				stage.widthProperty().addListener(changeListener);
			}
			
		});
	}
	
	@FXML
    public void exitButtonPressHandler() {
        System.exit(0);
    }

	 @FXML
	 public void newGameButtonPressHandler(ActionEvent event) throws IOException {
		 stage = (Stage) rootPane.getScene().getWindow();
		 stage.widthProperty().removeListener(changeListener);
		 to_load = false;
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change("layouts/main/scene.fxml", "Game");
	 }
	 
	 @FXML
	 public void loadGameButtonPressHandler() {
		 stage = (Stage) rootPane.getScene().getWindow();
		 stage.widthProperty().removeListener(changeListener);
		 to_load = true;
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change("layouts/main/scene.fxml", "Game");
	 }
	 
	 @FXML
	 public void leaderboardButtonPressHandler() {
		 stage = (Stage) rootPane.getScene().getWindow();
		 stage.widthProperty().removeListener(changeListener);
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change("layouts/leaderboard/LeaderBoard.fxml", "Leaderboard");
	 }
	 
}
