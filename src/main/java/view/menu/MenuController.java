package view.menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

import com.google.gson.Gson;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;
import savings.load.LoadGame;
import savings.load.LoadGameFactoryImpl;
import savings.load.LoadUtilities;
import savings.save.PathSavings;
import view.scenechanger.SceneChanger;
import view.scenechanger.SceneChangerImpl;
import view.scenechanger.ScenesItem;
/**
 * This is the @FXML controller of the Menu.
 * @author Alessandro Becci
 *
 */
public class MenuController {
	
	@FXML private BorderPane rootPane;
	@FXML private Button newGameButton;
	@FXML private Button loadGameButton;
	@FXML private Button leaderBoardButton;
	@FXML private Button exitGameButton;
	@FXML private Label title;
	public enum GameStatus{
		NORMAL,
		POWERUP,
		LOADNORMAL,
		LOADPOWERUP;
	}
	public static GameStatus gameStatus;
	private SceneChanger sceneChange;
	private Stage stage = Main.STAGE;
	
	//I need this changeListener for setting labels and buttons size while resizing.
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
	
	private GameStatus getGameType() {
		//if it's not setted, lets do a normal game
		GameStatus type = GameStatus.NORMAL;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(PathSavings.GAMETYPE.getPath())));
			Gson ser = new Gson();
			type = ser.fromJson(reader.readLine(), GameStatus.class);
			reader.close();
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ATTENTION");
			alert.setHeaderText("Saving not present");
			alert.setContentText("There isn't any saved game, starting a normal match instead, Save next time!");

			alert.showAndWait();
		}
		return type;
	}
		
	
	public void initialize() {
		//setting on the stage the changeListener for resizing
		stage.widthProperty().addListener(changeListener);
	}
	
	@FXML
    public void exitButtonPressHandler() {
        System.exit(0);
    }
	
	 @FXML
	 public void newGameButtonPressHandler(ActionEvent event) {
		 //removing the listener before starting a game(exiting from menu)
		 stage.widthProperty().removeListener(changeListener);
		 //static variable to tell UIController to NOT load the game
		 gameStatus = GameStatus.NORMAL;
		//setting up an alert for gameType
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("Choose your game type !");
	     alert.setHeaderText("Game Type choice");
	     alert.setContentText("Choose your option");
	     alert.getDialogPane().setStyle("-fx-background-color: #2B2D42; -fx-fill: #FFFFFF;");
	     // Set the icon 
	     ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
	   		(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));
	     
	     ButtonType buttonTypeOne = new ButtonType("Normal");
	     ButtonType buttonTypeTwo = new ButtonType("PowerUp");
	     ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	     alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == buttonTypeOne){
			 gameStatus = GameStatus.NORMAL;
		 } else if (result.get() == buttonTypeTwo) {
			 gameStatus = GameStatus.POWERUP;
		 } else {
			 gameStatus = GameStatus.NORMAL;
		 }
		 //changing scene with Game
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change(ScenesItem.GAME.get(), ScenesItem.GAMETITLE.get());
	 }
	 
	 @FXML
	 public void loadGameButtonPressHandler() {
		 if(getGameType().equals(GameStatus.NORMAL)) {
			 LoadGame<RoundEnvironment> loading = new LoadGameFactoryImpl().buildNormal();
			 if(loading.saveExist()) { gameStatus = GameStatus.LOADNORMAL; } else { gameStatus = GameStatus.NORMAL; }
		 }
		 else {
			 LoadGame<RoundPUpEnvironment> loading = new LoadGameFactoryImpl().buildPowerup();
			 if(loading.saveExist()) { gameStatus = GameStatus.LOADPOWERUP; } else { gameStatus = GameStatus.NORMAL; }
		 }
		 //changing scene with Game
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change(ScenesItem.GAME.get(), ScenesItem.GAMETITLE.get());
	 }
	 
	 @FXML
	 public void leaderboardButtonPressHandler() {
		//removing the listener before going in a game(exiting from menu)
		 stage.widthProperty().removeListener(changeListener);
		 File leaderBoard = new File(PathSavings.LEADERBOARD.getPath());
		 if(!leaderBoard.exists()) {
			 try{
				 leaderBoard.createNewFile();
			 } catch(Exception e) {
				 System.out.println("cant create file");
			 }
		 }
		 //changing scene with leaderBoard
		 sceneChange = new SceneChangerImpl();
		 sceneChange.change(ScenesItem.LEADERBOARD.get(), ScenesItem.LEADERBOARDTITLE.get());
	 }
	 
}
