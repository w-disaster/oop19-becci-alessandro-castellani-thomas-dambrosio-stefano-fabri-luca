package controllers;

import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import savings.LoadGame;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;
import viewmenu.ScenesItem;

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
	private LoadGame loading;
	
	private ChangeListener<Number> changeListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			String styleButtons = "-fx-font-size:" + newValue.doubleValue()/40 + ";"; 
			String styleLabel = "-fx-font-size:" + newValue.doubleValue()/13 + ";";
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
	 public void newGameButtonPressHandler(ActionEvent event) {
		 stage = (Stage) rootPane.getScene().getWindow();
		 stage.widthProperty().removeListener(changeListener);
		 to_load = false;
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("Choose your game type !");
	     alert.setHeaderText("Game Type choice");
	     alert.setContentText("Choose your option");

	     ButtonType buttonTypeOne = new ButtonType("Normal");
	     ButtonType buttonTypeTwo = new ButtonType("PowerUp");
	     ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	     alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == buttonTypeOne){
		     System.out.println("aa");
		 } else if (result.get() == buttonTypeTwo) {
		     System.out.println("bb");
		 } else {
		     //if you don't choice, normal Game
		 }
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change(ScenesItem.GAME.get(), ScenesItem.GAMETITLE.get());
	 }
	 
	 @FXML
	 public void loadGameButtonPressHandler() {
		 loading = new LoadGame();
		 if(loading.saveExist()) { to_load = true; } else { to_load = false; }
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change(ScenesItem.GAME.get(), ScenesItem.GAMETITLE.get());
	 }
	 
	 @FXML
	 public void leaderboardButtonPressHandler() {
		 stage = (Stage) rootPane.getScene().getWindow();
		 stage.widthProperty().removeListener(changeListener);
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change(ScenesItem.LEADERBOARD.get(), ScenesItem.LEADERBOARDTITLE.get());
	 }
	 
}
