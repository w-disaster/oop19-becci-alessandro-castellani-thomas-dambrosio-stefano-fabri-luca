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
	public static Stage stage;
	public static int indexPage;
	private LoadLeaderBoard load = new LoadLeaderBoard();
	
	@FXML private void backToMenu() {
		sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
	}
	
	private void loadTextBox(int pageIndex) {
		try {
			stage = (Stage) borderPane.getScene().getWindow();
			indexPage = pageIndex;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource("layouts/leaderboard/hBoxText.fxml"));
			hBoxText = loader.load();
		} catch(Exception e) {
			System.out.println("problems loading fxmlFRBHVBYHUFVBGY");
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
		
}
