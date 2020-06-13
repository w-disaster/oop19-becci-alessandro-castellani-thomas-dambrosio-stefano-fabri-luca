package controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

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
import javafx.util.Pair;
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
	public static int numPages;
	private Map<Integer, List<String>> indexPageToPlayer;
	private Map<Integer, List<Integer>> indexPageToScore;
	private List<Label> listLabelsName;
	private List<Label> listLabelsScore;
	public final static int NUM_ENTRIES_PAG = 3;
	
	private void populateLists() {
		indexPageToPlayer = load.getIndexToPlayerMap();
		indexPageToScore = load.getIndexToScoreMap();
	}
	
	private void createInterface() {
		indexPage = LeaderboardControl.indexPage;
		stage = LeaderboardControl.stage;
		System.out.println("creating INTERFACE " + indexPage);
		//this method creates the interface for the given index page.
		if(indexPageToPlayer.get(indexPage).size() == NUM_ENTRIES_PAG) {
			//i'll create three spaces for each VBox
			listLabelsName = new ArrayList<>();
			listLabelsScore = new ArrayList<>();
			for(int i=0; i < NUM_ENTRIES_PAG; i++) {
				Label labelName = new Label(indexPageToPlayer.get(indexPage).get(i));
				Label labelScore = new Label(Integer.toString(indexPageToScore.get(indexPage).get(i)));
				labelName.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				labelScore.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				labelName.setAlignment(Pos.CENTER);
				labelScore.setAlignment(Pos.CENTER);
				listLabelsName.add(labelName);
				listLabelsScore.add(labelScore);
			}
			for(int i=0; i < NUM_ENTRIES_PAG; i++) {
				boxNames.getChildren().add(listLabelsName.get(i));
				boxScore.getChildren().add(listLabelsScore.get(i));
				if(i != NUM_ENTRIES_PAG-1) {
					boxNames.getChildren().add(new Region());
					boxScore.getChildren().add(new Region());
				}
			}
			
			for(int i=0; i < (NUM_ENTRIES_PAG*2 - 1); i++) {
				VBox.setVgrow(boxNames.getChildren().get(i), Priority.ALWAYS);
				VBox.setVgrow(boxScore.getChildren().get(i), Priority.ALWAYS);
			}
		}
		else {
			listLabelsName = new ArrayList<>();
			listLabelsName.add(new Label("gangshit"));
			boxNames.getChildren().add(listLabelsName.get(0));
		}
		setListener();
	}
	
	private void calculateNumPages() {
		OptionalInt numPages = indexPageToPlayer.keySet().stream().mapToInt(v -> v).max();
		HboxTextController.numPages = numPages.getAsInt();
	}
	
	public void initialize() {
		load = new LoadLeaderBoard();
		populateLists();
		createInterface();
		calculateNumPages();
	}
	
	public void setListener() {
			//DO IT ONCE
			//if(indexPage==0) {
				stage.widthProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						String styleLabels = "-fx-font-size:" + newValue.doubleValue()/45 + ";"; 
						String styleNamesScore = "-fx-font-size:" + newValue.doubleValue()/25 + ";";
						title1.setStyle(styleNamesScore);
						title2.setStyle(styleNamesScore);
						for(int i=0; i < listLabelsName.size(); i++) {
							listLabelsName.get(i).setStyle(styleLabels);
							//listLabelsScore.get(i).setStyle(styleLabels);
						}
					}
				});
				stage.setWidth(stage.getWidth()+1);
				stage.setWidth(stage.getWidth()-1);
			//}
	}
	
}
