package controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	
	@FXML private void backToMenu() {
		sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
	}
	
	private Node createNewPage(int pageIndex) {
		HBox hRoot = new HBox();
		Region spacerRoot = new Region();
		Region spacerRoot2 = new Region();
		
		VBox v = new VBox();
		v.setPrefWidth(250);
		
		HBox titles = new HBox();
		Label title1 = new Label("NAME");
		Region spacerTitles = new Region();
		Label title2 = new Label("SCORE");
		
		spacerTitles.setPrefSize(100, 100);
		spacerTitles.setStyle("-fx-border-color: purple;");
		title1.setStyle("-fx-border-color:yellow;");
		title2.setStyle("-fx-border-color:yellow;");
		title1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		title2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		title1.setAlignment(Pos.CENTER);
		title2.setAlignment(Pos.CENTER);
		titles.getChildren().add(title1);
		titles.getChildren().add(spacerTitles);
		titles.getChildren().add(title2);
		HBox.setHgrow(title1, Priority.ALWAYS);
		HBox.setHgrow(spacerTitles, Priority.ALWAYS);
		HBox.setHgrow(title2, Priority.ALWAYS);
		
		v.getChildren().add(titles);
		v.setStyle("-fx-border-color: green;");
		
		hRoot.getChildren().add(spacerRoot);
		hRoot.getChildren().add(v);
		hRoot.getChildren().add(spacerRoot2);
		HBox.setHgrow(spacerRoot, Priority.ALWAYS);
		HBox.setHgrow(v, Priority.ALWAYS);
		HBox.setHgrow(spacerRoot2, Priority.ALWAYS);
		return hRoot;
	}
	
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
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
