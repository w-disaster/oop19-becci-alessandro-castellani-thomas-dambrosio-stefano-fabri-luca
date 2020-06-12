package controllers;


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
import javafx.util.Callback;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class LeaderboardControl {
	
	private SceneChanger sceneChange = new SceneChangerImpl();
	@FXML private BorderPane borderPane;
	@FXML private Pagination pag;
	@FXML private Label title1;
	private HBox hBoxText;
	
	@FXML private void backToMenu() {
		sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
	}
	
	private void loadTextBox() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource("layouts/leaderboard/hBoxText.fxml"));
			hBoxText = loader.load();
			HboxTextController controller = loader.<HboxTextController>getController();
			controller.setData(8);
		} catch(Exception e) {
			System.out.println("problems loading fxml");
		}
		//return new HBox();
	}
	
	private Node createNewPage(int pageIndex) {
		return hBoxText;
	}
	
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				loadTextBox();
				pag.setPageFactory(new Callback<Integer, Node>(){
					@Override
					public Node call(Integer param) {
						return createNewPage(param);
					}
				});
			}
			
		});
		pag.setStyle("-fx-border-color:red;");
	}

	
	
}
