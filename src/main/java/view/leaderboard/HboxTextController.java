package view.leaderboard;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import savings.load.LoadLeaderBoard;
import savings.load.LoadLeaderBoardImpl;
import view.scenechanger.SceneChanger;
import view.scenechanger.SceneChangerImpl;

/**
 * The Class HboxTextController.
 * @author Alessandro Becci
 */
public class HboxTextController {
	
	/** The first title */
	@FXML private Label title1;
	
	/** The second title */
	@FXML private Label title2;
	
	/** The box of the Score. */
	@FXML private VBox boxScore;
	
	/** The box of the Names. */
	@FXML private VBox boxNames;
	
	/** The root. */
	@FXML private HBox root;
	
	/** The SceneChanger. */
	private final SceneChanger sceneChange = new SceneChangerImpl();
	
	/** The stage. */
	private final Stage stage = Main.getStage();
	
	/** The loader of LeaderBoard. */
	private final LoadLeaderBoard load = new LoadLeaderBoardImpl();
	
	/** The index page. */
	private int indexPage;
	
	/** A map that associates index of the Page and the list of the Players. */
	private Map<Integer, List<String>> indexPageToPlayer;
	
	/** A map that associates index of the Page and the list of the Scores. */
	private Map<Integer, List<Integer>> indexPageToScore;
	
	/** A map that associates index of the Page and the list of the Player Labels. */
	private final Map<Integer, List<Label>> mapLabelsName = new HashMap<>();
	
	/** A map that associates index of the Page and the list of the Score Labels. */
	private final Map<Integer, List<Label>> mapLabelsScore = new HashMap<>();
	
	/** The Constant NUM_ENTRIES_PAG. */
	public final static int NUM_ENTRIES_PAG = 3;
	
	/** The ChangeListener. */
	private final ChangeListener<Number> changeListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			String styleLabels = "-fx-font-size:" + newValue.doubleValue()/45 + ";" + "-fx-font-family:monospace;" + 
					"-fx-border-color:#D90429;-fx-border-width:" + newValue.doubleValue()/100 + "px; -fx-border-radius: 15.0;"; 
					String styleNamesScore = "-fx-font-size:" + newValue.doubleValue()/22 + ";" + "-fx-text-fill: #FFFFFF;" + 
					 "-fx-border-color: #1A1B28;" + "-fx-border-width:" + newValue.doubleValue()/100 + "px; -fx-border-radius:15.0;" +
							"-fx-font-family: Serif";
					title1.setStyle(styleNamesScore);
					title2.setStyle(styleNamesScore);
					for(int i=0; i < mapLabelsName.get(indexPage).size(); i++) {
						mapLabelsName.get(indexPage).get(i).setStyle(styleLabels);
						mapLabelsScore.get(indexPage).get(i).setStyle(styleLabels);
					}
		}
		
	};
	
	/**
	 * Populate the lists.
	 */
	private void populateLists() {
		indexPageToPlayer = load.getIndexToPlayerMap();
		indexPageToScore = load.getIndexToScoreMap();
	}
	
	/**
	 * Creates the List of players and Scores.
	 *
	 * @return the pair of Lists
	 */
	private Pair<List<Label>, List<Label>> createLabels() {
		List<Label> listLabelsName = new ArrayList<Label>();
		List<Label> listLabelsScore = new ArrayList<Label>();
		if(indexPageToPlayer.isEmpty()) {
			indexPageToPlayer.put(indexPage, Collections.emptyList());
			return new Pair<>(Collections.emptyList(), Collections.emptyList());
		}
		for(int i=0; i < indexPageToPlayer.get(indexPage).size(); i++) {
			Label labelName = new Label(indexPageToPlayer.get(indexPage).get(i));
			Label labelScore = new Label(Integer.toString(indexPageToScore.get(indexPage).get(i)));
			labelName.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			labelScore.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			labelName.setAlignment(Pos.CENTER);
			labelScore.setAlignment(Pos.CENTER);
			listLabelsName.add(labelName);
			listLabelsScore.add(labelScore);
		}
		return new Pair<>(listLabelsName, listLabelsScore);
	}
	
	/**
	 * Sets the childrens of VBox to VGrow ALWAYS
	 */
	private void setVGrowChildrens() {
		for(final Node child: boxNames.getChildren()) {
			VBox.setVgrow(child, Priority.ALWAYS);
		}
		for(final Node child: boxScore.getChildren()) {
			VBox.setVgrow(child, Priority.ALWAYS);
		}
	}
	
	/**
	 * Fills the boxes of names and scores.
	 */
	private void fillBoxes() {
		int numItems = indexPageToPlayer.get(indexPage).size();
		for(int i=0; i < numItems; i++) {
			boxNames.getChildren().add(mapLabelsName.get(indexPage).get(i));
			boxScore.getChildren().add(mapLabelsScore.get(indexPage).get(i));
			if(i != NUM_ENTRIES_PAG-1) {
				boxNames.getChildren().add(new Region());
				boxScore.getChildren().add(new Region());
			}
		}
	}
	
	/**
	 * Creates the Graphical Interface of LeaderBoard.
	 */
	private void createInterface() {
		indexPage = LeaderboardControl.indexPage;
		mapLabelsName.put(indexPage, createLabels().getKey());
		mapLabelsScore.put(indexPage, createLabels().getValue());
		if(mapLabelsName.get(indexPage).isEmpty()) {
			// Dialog setup
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Empty Leaderboard");
			alert.setHeaderText("The Leaderboard is empty");
			alert.setContentText("You wanna get back to menu?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    sceneChange.change("layouts/menu/MainMenu.fxml", "Menu");
			} else {
				System.exit(0);
			}
		}
		fillBoxes();
		setVGrowChildrens();
		setListener();
	}
	
	/**
	 * @FXML initialize.
	 */
	public void initialize() {
		populateLists();
		createInterface();
	}
	
	/**
	 * Sets the listener for resizing.
	 */
	public void setListener() {
		stage.widthProperty().addListener(changeListener);
		stage.setWidth(stage.getWidth()+1);
		stage.setWidth(stage.getWidth()-1);
	}
	
}
