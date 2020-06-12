package controllers;


import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import savings.LoadLeaderBoard;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class LeaderboardControl {
	
	private SceneChanger sceneChange = new SceneChangerImpl();
	@FXML private BorderPane borderPane;
	@FXML private Pagination pag;
	@FXML private Label title1;
	private HBox hBoxText;
	private Stage stage;
	private int indexPage;
	private HboxTextController controllerHBox;
	
	@FXML private void backToMenu() {
		sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
	}
	
	private void setInformationTextBox() {
		
	}
	
	private void loadTextBox() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource("layouts/leaderboard/hBoxText.fxml"));
			hBoxText = loader.load();
			controllerHBox = loader.<HboxTextController>getController();
			stage = (Stage) borderPane.getScene().getWindow();
			//USELESS: i'll pass it with "LoadLeaderboard class"
			
		} catch(Exception e) {
			System.out.println("problems loading fxml");
		}
	}
	
	private Node createNewPage(int pageIndex) {
		this.indexPage = pageIndex;
		controllerHBox.setData(stage, indexPage);
		if(indexPage==0) { controllerHBox.setListener(); }
		return hBoxText;
	}
	
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				setInformationTextBox();
				loadTextBox();
				pag.setMaxPageIndicatorCount(controllerHBox.getNumberPages());
				pag.setPageCount(controllerHBox.getNumberPages());
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

	
	
}
