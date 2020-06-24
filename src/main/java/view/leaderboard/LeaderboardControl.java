package view.leaderboard;



import java.io.File;
import java.util.Optional;

import application.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import savings.load.LoadLeaderBoard;
import savings.save.PathSavings;
import view.sceneChanger.SceneChanger;
import view.sceneChanger.SceneChangerImpl;
import view.sceneChanger.ScenesItem;

public class LeaderboardControl {
	
	private SceneChanger sceneChange = new SceneChangerImpl();
	@FXML private BorderPane borderPane;
	@FXML private Pagination pag;
	@FXML private Label leaderBoardLabel;
	@FXML private Button backMenuButton;
	@FXML private Button cleanButton;
	private HBox hBoxText;
	private Stage stage = Main.STAGE;
	public static int indexPage;
	private LoadLeaderBoard load = new LoadLeaderBoard();
	
	private ChangeListener<Number> changeListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			String styleButton = "-fx-font-size:" + newValue.doubleValue()/40 + ";" + "-fx-font-family:monospace;" + 
					"-fx-background-color:#1A1B28;" + "-fx-text-fill: #FFFFFF;" + "-fx-border-color: white;";
				backMenuButton.setStyle(styleButton);
				cleanButton.setStyle(styleButton);
		}
		
	};
	
	private void removeListener() {
		stage.widthProperty().removeListener(changeListener);
	}
	
	@FXML private void cleanLeaderboard() {
		 boolean delete = false;
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("You are deleting the LeaderBoard!");
	     alert.setHeaderText("Leaderboard elimination");
	     alert.setContentText("Are you sure ?");
	     alert.getDialogPane().setStyle("-fx-background-color: #2B2D42; -fx-fill: #FFFFFF;");
	     // Set the icon 
	     ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add
	   		(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));
	     
	     ButtonType buttonTypeOne = new ButtonType("Yes");
	     ButtonType buttonTypeTwo = new ButtonType("No");
	     ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	     alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == buttonTypeOne){
			 delete = true;
		 } else if (result.get() == buttonTypeTwo) {
			 delete = false;
		 } else {
			 delete = false;
		 }
		if(delete) {
			File leaderBoardFile = new File(PathSavings.LEADERBOARD.getPath());
			leaderBoardFile.delete();
			removeListener();
			sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
		}
	}
	
	@FXML private void backToMenu() {
		removeListener();
		sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
	}
	
	private void loadTextBox(int pageIndex) {
		try {
			
			indexPage = pageIndex;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource(ScenesItem.PAGLEADERBOARD.get()));
			hBoxText = loader.load();
		} catch(Exception e) {
			//System.out.println(e.getMessage());
			System.out.println("problems loading fxml");
		}
	}
	
	private Node createNewPage(int pageIndex) {
		loadTextBox(pageIndex);
		return hBoxText;
	}
	
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				borderPane.getScene().getStylesheets().add(ScenesItem.LEADERBOARDSTYLE.get());
				setResizeEvents();
				pag.setMaxPageIndicatorCount(load.getNumPages());
				pag.setPageCount(load.getNumPages());
				
				pag.setCurrentPageIndex(1);
				pag.setPageFactory(new Callback<Integer, Node>(){
					@Override
					public Node call(Integer param) {
						return createNewPage(param);
					}
				});
				
			}

			
		});
	}
	
	private void setResizeEvents() {
		stage.widthProperty().addListener(changeListener);
	}
	
	
		
}
