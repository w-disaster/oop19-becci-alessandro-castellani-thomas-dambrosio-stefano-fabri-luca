package controllers;


import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class LeaderboardControl {
	
	private SceneChanger sceneChange = new SceneChangerImpl();
	@FXML private BorderPane borderPane;
	
	@FXML private void backToMenu() {
		sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
	}
	
	private Node createNewPage(int pageIndex) {
		HBox h = new HBox();
		Label label = new Label("NAME");
		h.setAlignment(Pos.TOP_LEFT);
		h.setPadding(new Insets(20,0,0,300));
		h.getChildren().add(label);
		return h;
	}
	
	public void initialize() {
		Pagination pag = new Pagination(5);
		AnchorPane anchor = new AnchorPane();
	    AnchorPane.setTopAnchor(pag, 10.0);
	    AnchorPane.setRightAnchor(pag, 10.0);
	    AnchorPane.setBottomAnchor(pag, 10.0);
	    AnchorPane.setLeftAnchor(pag, 10.0);
	    anchor.getChildren().addAll(pag);
	    borderPane.setCenter(anchor);
		pag.setPageFactory((pageIndex) -> {
			    return createNewPage(pageIndex);
		});
		pag.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		//pag.setPrefSize(.getWidth(), vBox.getHeight());
		pag.setStyle("-fx-border-color:red;");
		System.out.println(anchor.getHeight());
	}

	
	
}
