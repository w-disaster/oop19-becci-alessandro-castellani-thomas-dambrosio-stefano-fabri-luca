package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import savings.LoadLeaderBoard;

public class HboxTextController {
	@FXML private Label title1;
	@FXML private Label title2;
	@FXML private VBox boxScore;
	@FXML private VBox boxNames;
	@FXML private HBox root;
	private Stage stage;
	private LoadLeaderBoard load;
	private int indexPage;
	
	
	public void setData(final Stage stage, final int pageIndex) {
		this.stage = stage;
		this.indexPage = pageIndex;
		title1.setText(Integer.toString(indexPage));
	}
	
	public int getNumberPages() {
		return load.getNumPages();
	}
	
	public void initialize() {
		load = new LoadLeaderBoard();
		System.out.println(load.getSortedEntries());
		System.out.println(load.getIndexToNumEntries());
		
		/*Label label1 = new Label("FIRST");
		Region spacer1 = new Region();
		Label label2 = new Label("SECOND");
		Region spacer2 = new Region();
		Label label3 = new Label("THIRD");
		boxNames.getChildren().add(label1);
		boxNames.getChildren().add(spacer1);
		boxNames.getChildren().add(label2);
		boxNames.getChildren().add(spacer2);
		boxNames.getChildren().add(label3);
		label1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		label2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		label3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		VBox.setVgrow(label1, Priority.ALWAYS);
		VBox.setVgrow(spacer1, Priority.ALWAYS);
		VBox.setVgrow(label2, Priority.ALWAYS);
		VBox.setVgrow(spacer2, Priority.ALWAYS);
		VBox.setVgrow(label3, Priority.ALWAYS);
		label1.setAlignment(Pos.CENTER);
		label2.setAlignment(Pos.CENTER);
		label3.setAlignment(Pos.CENTER);
		label1.setStyle("-fx-border-color:red;");
		spacer1.setStyle("-fx-border-color:purple;");
		label2.setStyle("-fx-border-color:green;");
		label3.setStyle("-fx-border-color:black;");*/
		
	}
	
	public void setListener() {
			//DO IT ONCE
			//if(indexPage==0) {
				stage.widthProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						String styleButtons = "-fx-font-size:" + newValue.doubleValue()/30 + ";"; 
						String styleNamesScore = "-fx-font-size:" + newValue.doubleValue()/30 + ";";
						title1.setStyle(styleButtons);
						title2.setStyle(styleNamesScore);
					}
				});
				stage.setWidth(stage.getWidth()+1);
				stage.setWidth(stage.getWidth()-1);
			//}
	}
}
